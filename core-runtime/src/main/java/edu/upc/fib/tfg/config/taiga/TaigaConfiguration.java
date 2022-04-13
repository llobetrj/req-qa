package edu.upc.fib.tfg.config.taiga;

public class TaigaConfiguration {
    private String taigaBaseUrl;

    public TaigaConfiguration() {
    }

    public TaigaConfiguration(String taigaBaseUrl) {
        this.taigaBaseUrl = taigaBaseUrl;
    }

    public String getTaigaBaseUrl() {
        return taigaBaseUrl;
    }

    public void setTaigaBaseUrl(String taigaBaseUrl) {
        this.taigaBaseUrl = taigaBaseUrl;
    }
}
