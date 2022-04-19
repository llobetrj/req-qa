package edu.upc.fib.reqqa.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties
public class ApplicationConfiguration {


    @Bean
    @ConfigurationProperties(prefix = "req-qa")
    public ModuleProperties getModuleProperties() {
        return new ModuleProperties();
    }

    @Bean
    @ConfigurationProperties(prefix = "taiga")
    public TaigaConfiguration getTaigaProperties() {return new TaigaConfiguration();}

    @Bean
    @ConfigurationProperties(prefix = "open-req")
    public OpenReqConfiguration getOpenReqProperties() {return new OpenReqConfiguration();}

}
