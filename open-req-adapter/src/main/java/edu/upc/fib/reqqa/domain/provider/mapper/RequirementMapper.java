package edu.upc.fib.reqqa.domain.provider.mapper;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import edu.upc.fib.reqqa.domain.model.Requirement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class RequirementMapper {
    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    public String toJson(List<Requirement> requirementList) {
        ObjectMapper mapper = new ObjectMapper();
        //Converting the Object to JSONString
        String jsonString = "";
        try {
            jsonString = mapper.writeValueAsString(requirementList);
        } catch (JsonProcessingException e) {
            LOGGER.error("Error {}", e.toString());
        }
        jsonString = "{\"requirements\":" + jsonString + "}";
        return jsonString;
    }
}
