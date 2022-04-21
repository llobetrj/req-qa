package edu.upc.fib.reqqa.ingress.dto;

import lombok.Data;

@Data
public class TaigaEventRequestBy {
    private final String id;
    private final String photo;
    private final String username;
    private final String full_name;
    private final String permalink;
    private final String gravatar_id;


}
