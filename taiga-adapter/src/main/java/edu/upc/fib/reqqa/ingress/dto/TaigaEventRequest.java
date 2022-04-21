package edu.upc.fib.reqqa.ingress.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class TaigaEventRequest {
    public static final String ISO_8601_DATE_FORMAT = "(\\d{4})-(\\d{2})-(\\d{2})T(\\d{2})\\:(\\d{2})\\:(\\d{2})(.\\d{3})?Z$";

    @NotNull(message = "Action required")
    @ApiModelProperty(required=true, value="action type")
    private String action;

    @NotNull(message = "type required")
    private String type;

    private TaigaEventRequestBy by;

    @Pattern(regexp = ISO_8601_DATE_FORMAT)
    private String date;


    private TaigaEventRequestData data;
}
