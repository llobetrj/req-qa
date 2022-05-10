package edu.upc.fib.reqqa.rest.controller.mapper;

import edu.upc.fib.reqqa.domain.model.Requirement;
import edu.upc.fib.reqqa.ingress.dto.TaigaEventRequest;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class TaigaEventMapper {

    public List<Requirement> toRequirements(TaigaEventRequest taigaEventRequest) {
        // assuming one request from taiga event
        Requirement requirement = new Requirement(taigaEventRequest.getData().getId(), taigaEventRequest.getData().getDescription());
        List<Requirement> requirementList = new ArrayList<>();
        requirementList.add(requirement);
        return requirementList;
    }
}
