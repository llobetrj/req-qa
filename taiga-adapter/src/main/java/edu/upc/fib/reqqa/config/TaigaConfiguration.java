package edu.upc.fib.reqqa.config;

public class TaigaConfiguration {
    private String baseUrl;

    private String secret;

    public String getSecret() {
        return secret;
    }

    public void setSecret(String secret) {
        this.secret = secret;
    }
    public TaigaConfiguration() {
    }

    public TaigaConfiguration(String baseUrl) {
        this.baseUrl = baseUrl;
    }

    public String getBaseUrl() {
        return baseUrl;
    }

    public void setBaseUrl(String baseUrl) {
        this.baseUrl = baseUrl;
    }
}
