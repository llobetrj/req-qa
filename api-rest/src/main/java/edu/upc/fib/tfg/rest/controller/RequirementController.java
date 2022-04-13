package edu.upc.fib.tfg.rest.controller;

import edu.upc.fib.tfg.rest.dto.Greeting;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RequirementController {
    private static final Logger LOGGER = LoggerFactory.getLogger(RequirementController.class);

    private static final String template = "Hello, %s!";

    @GetMapping("/greeting")
    public Greeting greeting(@RequestParam(value = "name", defaultValue = "World") String name) {
        LOGGER.info("Received value {}",name);
        return new Greeting(1, String.format(template, name));
    }
}
