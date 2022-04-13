package edu.upc.fib.reqqa.config;

public class ModuleProperties {
    public ModuleProperties() {
    }

    public ModuleProperties(String version, String moduleName) {
        this.version = version;
        this.moduleName = moduleName;
    }

    private String version;
    private String moduleName;

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getModuleName() {
        return moduleName;
    }

    public void setModuleName(String moduleName) {
        this.moduleName = moduleName;
    }

}
