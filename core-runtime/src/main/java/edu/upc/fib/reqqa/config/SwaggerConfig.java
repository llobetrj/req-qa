package edu.upc.fib.reqqa.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.Collections;

@Configuration
@EnableSwagger2
public class SwaggerConfig {

    @Bean
    Docket getDocket(){
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.any())
                .paths(PathSelectors.any())
                .build()
                .apiInfo(getApiInfo());
    }

    private ApiInfo getApiInfo() {
        return new ApiInfo(
                "REST API OF REQUIREMENT QA PROJECT",
                "Requirement quality attribute application based on exists requirement services " +
                         "invoking via REST API, allowing requeriment engineers to analyze the quality of " +
                         "requirements.",
                "0.0.1",
                "Terms of service",
                new Contact("fib","http://fib.upc.edu","fib.upc.edu@.com"),
                "License of API", "API license URL", Collections.emptyList());
    }

}

