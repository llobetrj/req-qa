package edu.upc.fib.reqqa.rest.controller;

import edu.upc.fib.reqqa.config.TaigaConfiguration;
import edu.upc.fib.reqqa.ingress.dto.TaigaEventRequest;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
public class TaigaController {

    private static final Logger LOGGER = LoggerFactory.getLogger(TaigaController.class);

    private final TaigaConfiguration taigaConfiguration;

    @Autowired
    public TaigaController(TaigaConfiguration taigaConfiguration) {
        this.taigaConfiguration = taigaConfiguration;
    }

    @PostMapping("/taiga/webhook")
    @ApiOperation(value = "Taiga event",
            notes = "Notes",
            response = String.class)
    public ResponseEntity<String> taigaWebHook(
            @ApiParam(value = "Taige Event request", required = true)
            @Valid @RequestBody TaigaEventRequest taigaEventRequest) {
        LOGGER.info("Received Taiga my secret {}", taigaConfiguration.getSecret());
        LOGGER.info("Received Taiga event {} at {}", taigaEventRequest.getAction(), taigaEventRequest.getDate());
        return ResponseEntity.ok("Received");
    }
}
