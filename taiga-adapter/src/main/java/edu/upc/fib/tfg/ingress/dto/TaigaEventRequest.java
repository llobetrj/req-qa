package edu.upc.fib.tfg.ingress.dto;

import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

public class TaigaEventRequest {
    public static final String ISO_8601_DATE_FORMAT = "(\\d{4})-(\\d{2})-(\\d{2})T(\\d{2})\\:(\\d{2})\\:(\\d{2})(.\\d{3})?Z$";

    @NotNull(message = "Action required")
    @ApiModelProperty(required=true, value="action type")
    private String action;

    @NotNull(message = "type required")
    private String type;

    private Object by;

    @Pattern(regexp = ISO_8601_DATE_FORMAT)
    private String date;

    private Object data;

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Object getBy() {
        return by;
    }

    public void setBy(Object by) {
        this.by = by;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
