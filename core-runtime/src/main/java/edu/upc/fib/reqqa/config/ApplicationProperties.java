package edu.upc.fib.reqqa.config;

public class ApplicationProperties {
    public ApplicationProperties() {
    }

    public ApplicationProperties(String version, String name) {
        this.version = version;
        this.name = name;
    }

    private String version;
    private String name;

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
