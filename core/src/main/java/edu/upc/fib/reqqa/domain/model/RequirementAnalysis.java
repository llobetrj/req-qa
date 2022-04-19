package edu.upc.fib.reqqa.domain.model;

import lombok.Data;

import java.util.List;

@Data
public class RequirementAnalysis {
    private String id;
    List<RequirementAnalysisDetail> requirementAnalysisDetailList;
}
