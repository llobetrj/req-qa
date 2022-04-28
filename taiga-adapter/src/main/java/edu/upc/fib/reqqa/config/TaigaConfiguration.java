package edu.upc.fib.reqqa.config;

import lombok.Data;

@Data
public class TaigaConfiguration {
    private String baseUrl;
    private String secret;
    private String customFieldName;
    private String username;
    private String password;
    private String granType;


}
