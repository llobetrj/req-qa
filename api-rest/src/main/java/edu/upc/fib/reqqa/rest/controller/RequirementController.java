package edu.upc.fib.reqqa.rest.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import edu.upc.fib.reqqa.domain.model.Requirement;
import edu.upc.fib.reqqa.domain.model.RequirementAnalysis;
import edu.upc.fib.reqqa.domain.service.RequirementAnalyzerService;
import edu.upc.fib.reqqa.rest.dto.RequirementsRequest;
import edu.upc.fib.reqqa.rest.mapper.RequirementMapperHelper;
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

    private final RequirementMapperHelper requirementMapperHelper;

    public RequirementController(@Autowired RequirementAnalyzerService requirementAnalyzerService, @Autowired RequirementMapperHelper requirementMapperHelper) {
        this.requirementAnalyzerService = requirementAnalyzerService;
        this.requirementMapperHelper = requirementMapperHelper;
    }

    @PostMapping("/analyze")
    @ApiOperation(value = "Analyze requirements",
            notes = "Notes",
            response = String.class)
    public ResponseEntity<String> analyzeRequirements(
            @RequestBody RequirementsRequest requirementsRequest
            ) {

        LOGGER.info("Received request to be analyzed {}",requirementsRequest.getRequirements().toString());

        List<RequirementAnalysis> requirementAnalysisList = requirementAnalyzerService.analyse(requirementMapperHelper.mapToRequirements(requirementsRequest));

        return ResponseEntity.ok(requirementMapperHelper.mapToResponse(requirementAnalysisList));
    }




}
