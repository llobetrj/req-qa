package edu.upc.fib.tfg.rest.controller;

import edu.upc.fib.tfg.config.TaigaConfiguration;
import edu.upc.fib.tfg.ingress.dto.TaigaEventRequest;
import edu.upc.fib.tfg.rest.dto.Greeting;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import javax.validation.Valid;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.Signature;
import java.security.SignatureException;

@RestController
public class RequirementController {
    private static final Logger LOGGER = LoggerFactory.getLogger(RequirementController.class);

    private static final String template = "Hello, %s!";

    private TaigaConfiguration taigaConfiguration;

    public RequirementController(@Autowired TaigaConfiguration taigaConfiguration) {
        this.taigaConfiguration = taigaConfiguration;
    }

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
            @Valid @RequestBody TaigaEventRequest taigaEventRequest) {
        LOGGER.info("Received Taiga my secret {}", taigaConfiguration.getSecret());
        LOGGER.info("Received Taiga event {} at {}", taigaEventRequest.getAction(), taigaEventRequest.getDate());
        return ResponseEntity.ok("Received");
    }
}
