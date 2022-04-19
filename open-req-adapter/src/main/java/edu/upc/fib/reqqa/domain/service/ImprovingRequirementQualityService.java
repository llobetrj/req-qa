package edu.upc.fib.reqqa.domain.service;

import com.fasterxml.jackson.databind.JsonNode;
import edu.upc.fib.reqqa.domain.model.Requirement;
import edu.upc.fib.reqqa.domain.model.RequirementAnalysis;
import edu.upc.fib.reqqa.domain.provider.ImprovingRequirementQualityProvider;
import edu.upc.fib.reqqa.domain.service.api.IRequirementExternalService;
import edu.upc.fib.reqqa.domain.service.external.RequirementExternalService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

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
        return new ArrayList<>();
    }
}
