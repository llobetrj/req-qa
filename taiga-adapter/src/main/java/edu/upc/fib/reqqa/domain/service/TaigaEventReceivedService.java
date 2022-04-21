package edu.upc.fib.reqqa.domain.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import edu.upc.fib.reqqa.domain.model.Requirement;
import edu.upc.fib.reqqa.domain.model.RequirementAnalysis;
import edu.upc.fib.reqqa.ingress.dto.TaigaEventRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class TaigaEventReceivedService {

    private static final Logger LOGGER = LoggerFactory.getLogger(TaigaEventReceivedService.class);

    private final RequirementAnalyzerService requirementAnalyzerService;

    public static final String ISSUE = "issue";

    @Autowired
    public TaigaEventReceivedService(RequirementAnalyzerService requirementAnalyzerService) {
        this.requirementAnalyzerService = requirementAnalyzerService;
    }

    public List<RequirementAnalysis> process(TaigaEventRequest taigaEventRequest) {
        if (!taigaEventRequest.getType().equals(ISSUE)) {
            LOGGER.info("Taiga event with id {} is not a issue, discarded.", taigaEventRequest.getBy().getId());
            return null;
        }
        // do staff
        LOGGER.info("Processing Taiga event with id {}...", taigaEventRequest.getData().getId());

        // assuming one request from taiga event
        Requirement requirement = new Requirement(taigaEventRequest.getData().getId(), taigaEventRequest.getData().getDescription());
        List<Requirement> requirementList = new ArrayList<>();
        requirementList.add(requirement);

        List<RequirementAnalysis> requirementAnalysisList = requirementAnalyzerService.analyse(requirementList);
        LOGGER.info("Analyed result from reqqa core {}", requirementAnalysisList.toString());
        return requirementAnalysisList;
    }

}
