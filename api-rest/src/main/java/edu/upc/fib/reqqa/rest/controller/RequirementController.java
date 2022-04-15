package edu.upc.fib.reqqa.rest.controller;

import edu.upc.fib.reqqa.rest.dto.Greeting;
import edu.upc.fib.reqqa.rest.dto.RequirementsRequest;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RequirementController {
    private static final Logger LOGGER = LoggerFactory.getLogger(RequirementController.class);

    private static final String template = "Hello, %s!";

    public RequirementController() {
    }

    @GetMapping("/greeting")
    public Greeting greeting(@RequestParam(value = "name", defaultValue = "World") String name) {
        LOGGER.info("Received value {}",name);
        return new Greeting(1, String.format(template, name));
    }



    @PostMapping("/analyze")
    @ApiOperation(value = "Analyze requirements",
            notes = "Notes",
            response = String.class)
    public ResponseEntity<String> analyzeRequirements(
            @RequestBody RequirementsRequest requirementsRequest
            ) {
        LOGGER.info("Received request to be analyzed {}",requirementsRequest.getRequirements().toString());
        return ResponseEntity.ok("Analyzed!");
    }


}
