package edu.upc.fib.reqqa.rest.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import edu.upc.fib.reqqa.domain.model.Requirement;
import edu.upc.fib.reqqa.domain.model.RequirementAnalysis;
import edu.upc.fib.reqqa.domain.service.RequirementAnalyzerService;
import edu.upc.fib.reqqa.rest.dto.RequirementsRequest;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
public class RequirementController {
    private static final Logger LOGGER = LoggerFactory.getLogger(RequirementController.class);

    private final RequirementAnalyzerService requirementAnalyzerService;

    public RequirementController(@Autowired RequirementAnalyzerService requirementAnalyzerService) {
        this.requirementAnalyzerService = requirementAnalyzerService;
    }

    @PostMapping("/analyze")
    @ApiOperation(value = "Analyze requirements",
            notes = "Notes",
            response = String.class)
    public ResponseEntity<String> analyzeRequirements(
            @RequestBody RequirementsRequest requirementsRequest
            ) {
        LOGGER.info("Received request to be analyzed {}",requirementsRequest.getRequirements().toString());
        List<Requirement> requirementList = requirementsRequest.getRequirements().stream()
                                            .map(requirementDto -> {
                                                Requirement req = new Requirement(requirementDto.getId(),requirementDto.getText());
                                                return req;
                                            })
                                            .collect(Collectors.toList());
        List<RequirementAnalysis> requirementAnalysisList = requirementAnalyzerService.analyse(requirementList);
        ObjectMapper mapper = new ObjectMapper();
        String json = "";
        try {
            json = mapper.writeValueAsString(requirementAnalysisList);
        } catch (JsonProcessingException e) {
            LOGGER.error("Error parsing json {}",e.toString());
        }
        return ResponseEntity.ok(json);
    }


}
