package edu.upc.fib.reqqa.domain.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import edu.upc.fib.reqqa.domain.model.Requirement;
import edu.upc.fib.reqqa.domain.model.RequirementAnalysis;
import edu.upc.fib.reqqa.domain.model.RequirementAnalysisDetail;
import edu.upc.fib.reqqa.domain.provider.ImprovingRequirementQualityProvider;
import edu.upc.fib.reqqa.domain.service.api.IRequirementExternalService;
import edu.upc.fib.reqqa.domain.service.external.RequirementExternalService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class ImprovingRequirementQualityService extends RequirementExternalService implements IRequirementExternalService {

    private final Logger LOGGER = LoggerFactory.getLogger(ImprovingRequirementQualityService.class);

    private final ImprovingRequirementQualityProvider improvingRequirementQualityProvider;

    public ImprovingRequirementQualityService(@Autowired ImprovingRequirementQualityProvider improvingRequirementQualityProvider) {
        this.improvingRequirementQualityProvider = improvingRequirementQualityProvider;
    }

    @Override
    public List<RequirementAnalysis> getRequirementAnalysis(List<Requirement> requirementList) {
        JsonNode requirementAnalysisListJson = improvingRequirementQualityProvider.getImprovedRequirements(requirementList);
        LOGGER.info("Received from openReq {}",requirementAnalysisListJson);
        List<RequirementAnalysis> returnAnalysisList = new ArrayList<>();

        // map to RequirementAnalysis
        ObjectMapper mapper = new ObjectMapper();
        for (Iterator<Map.Entry<String, JsonNode>> iter = requirementAnalysisListJson.fields(); iter.hasNext(); ) {

            Map.Entry<String, JsonNode> n = iter.next();

            LOGGER.debug("output requirement analysis: " + n.getKey());

            Optional<Requirement> requirement = requirementList.stream().filter(t -> String.valueOf(t.getId()).equals(n.getKey())).findFirst();
            if (requirement.isPresent()) {

                RequirementAnalysis it = new RequirementAnalysis();
                it.setId(requirement.get().getId());
                returnAnalysisList.add(it);

                List<RequirementAnalysisDetail> requirementAnalysisDetails = new ArrayList<>();
                it.setRequirementAnalysisDetailList(requirementAnalysisDetails);

                if (n.getValue().isArray()) {
                    for (int valIndex = 0; valIndex < n.getValue().size(); valIndex++) {
                        JsonNode j = n.getValue().get(valIndex);
                        LOGGER.debug("Retrieve detailed value from openReq check-quality: " + j.toString());
                        try {
                            RequirementAnalysisDetail i = mapper.readValue(j.toString(), RequirementAnalysisDetail.class);

                            Optional<RequirementAnalysisDetail> oImp = requirementAnalysisDetails.stream().filter(im -> im.getIndex_end() >= i.getIndex_start()).findFirst();
                            if (oImp.isEmpty()) {

                                requirementAnalysisDetails.add(i);

                            }
                        } catch (Exception e) {
                            LOGGER.error("Error parsing json {}",e.toString());
                        }
                    }
                }
            }
        }

        return returnAnalysisList;
    }
}
