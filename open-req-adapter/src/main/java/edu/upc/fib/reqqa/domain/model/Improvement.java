package edu.upc.fib.reqqa.domain.model;

import lombok.Data;

@Data
public class Improvement {
    private String description;
    private Integer index_end;
    private Integer index_start;
    private String language_construct;
}
