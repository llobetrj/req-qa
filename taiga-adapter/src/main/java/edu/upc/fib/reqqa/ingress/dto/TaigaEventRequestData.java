package edu.upc.fib.reqqa.ingress.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class TaigaEventRequestData {
    private String description;

    private Object custom_attributes_values;

    private String id;
}
