package edu.upc.fib.reqqa.rest.dto;

import lombok.Data;

import java.util.List;

@Data
public class RequirementsRequest {

    private List<RequirementDto> requirements;

}
