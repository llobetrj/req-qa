package edu.upc.fib.tfg.rest.controller;

import edu.upc.fib.tfg.dto.TaigaEventRequest;
import edu.upc.fib.tfg.rest.dto.Greeting;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
public class RequirementController {
    private static final Logger LOGGER = LoggerFactory.getLogger(RequirementController.class);

    private static final String template = "Hello, %s!";

    @GetMapping("/greeting")
    public Greeting greeting(@RequestParam(value = "name", defaultValue = "World") String name) {
        LOGGER.info("Received value {}",name);
        return new Greeting(1, String.format(template, name));
    }

    @PostMapping("/taiga/webhook")
    @ApiOperation(value = "Taiga event",
            notes = "Notes",
            response = String.class)
    public ResponseEntity<String> taigaWebHook(
            @ApiParam(value = "Taige Event request", required = true)
            @RequestBody TaigaEventRequest taigaEventRequest) {
        LOGGER.info("Received Taiga event {} at {}", taigaEventRequest.getAction(), taigaEventRequest.getDate());
        return ResponseEntity.ok("Received");
    }
}
