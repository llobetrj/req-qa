package edu.upc.fib.reqqa.domain.service;

import edu.upc.fib.reqqa.domain.model.Requirement;
import edu.upc.fib.reqqa.domain.model.RequirementAnalysis;
import edu.upc.fib.reqqa.domain.service.api.IRequirementExternalService;
import edu.upc.fib.reqqa.domain.service.external.FactoryRequirementExternalService;
import edu.upc.fib.reqqa.domain.service.external.RequirementExternalService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class RequirementAnalyzerService {
    private static final Logger LOGGER = LoggerFactory.getLogger(RequirementAnalyzerService.class);

    private final FactoryRequirementExternalService factoryRequirementExternalService;

    @Autowired
    public RequirementAnalyzerService(FactoryRequirementExternalService factoryRequirementExternalService) {
        this.factoryRequirementExternalService = factoryRequirementExternalService;
    }

    public List<RequirementAnalysis> analyse(List<Requirement> requirementList) {
        List<RequirementAnalysis> requirementAnalysisList = new ArrayList<>();
        for (RequirementExternalService reqExternalService : factoryRequirementExternalService.getImplementationMap().values()) {
            LOGGER.info("class: {}", reqExternalService.toString());
            requirementAnalysisList.addAll(((IRequirementExternalService) reqExternalService).getRequirementAnalysis(requirementList));
        }
        return requirementAnalysisList;
    }


}
