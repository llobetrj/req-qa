package edu.upc.fib.reqqa.rest.mapper;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import edu.upc.fib.reqqa.domain.model.Requirement;
import edu.upc.fib.reqqa.domain.model.RequirementAnalysis;
import edu.upc.fib.reqqa.rest.controller.RequirementController;
import edu.upc.fib.reqqa.rest.dto.RequirementsRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class RequirementMapperHelper {
    private static final Logger LOGGER = LoggerFactory.getLogger(RequirementMapperHelper.class);

    public String mapToResponse(List<RequirementAnalysis> requirementAnalysisList) {
        ObjectMapper mapper = new ObjectMapper();
        String json = "";
        try {
            json = mapper.writeValueAsString(requirementAnalysisList);
        } catch (JsonProcessingException e) {
            LOGGER.error("Error parsing json {}",e.toString());
        }
        return json;
    }

    public List<Requirement> mapToRequirements(RequirementsRequest requirementsRequest) {
        return requirementsRequest.getRequirements().stream()
                .map(requirementDto -> new Requirement(requirementDto.getId(),requirementDto.getText()))
                .collect(Collectors.toList());
    }
}
