package edu.upc.fib.tfg.config;

import edu.upc.fib.tfg.config.taiga.TaigaConfiguration;
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
}
