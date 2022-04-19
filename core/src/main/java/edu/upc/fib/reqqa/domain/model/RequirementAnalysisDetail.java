package edu.upc.fib.reqqa.domain.model;

import lombok.Data;

@Data
public class RequirementAnalysisDetail {
    private String description;
    private Integer index_end;
    private Integer index_start;
    private String category;
    private String text;
    private String language_construct;
    private String title;

}
