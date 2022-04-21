package edu.upc.fib.reqqa.rest.controller;

import edu.upc.fib.reqqa.config.TaigaConfiguration;
import edu.upc.fib.reqqa.domain.model.RequirementAnalysis;
import edu.upc.fib.reqqa.domain.service.TaigaEventReceivedService;
import edu.upc.fib.reqqa.domain.service.TaigaSenderService;
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

import java.util.List;

@RestController
public class TaigaController {

    private static final Logger LOGGER = LoggerFactory.getLogger(TaigaController.class);

    private final TaigaConfiguration taigaConfiguration;

    private final TaigaEventReceivedService taigaEventReceivedService;

    private final TaigaSenderService taigaSenderService;

    @Autowired
    public TaigaController(TaigaConfiguration taigaConfiguration, TaigaEventReceivedService taigaEventReceivedService, TaigaSenderService taigaSenderService) {
        this.taigaConfiguration = taigaConfiguration;
        this.taigaEventReceivedService = taigaEventReceivedService;
        this.taigaSenderService = taigaSenderService;
    }

    @PostMapping("/taiga/webhook")
    @ApiOperation(value = "Taiga event",
            notes = "Notes",
            response = String.class)
    public ResponseEntity<String> taigaWebHook(
            @ApiParam(value = "Taige Event request", required = true)
            @RequestBody TaigaEventRequest taigaEventRequest) {
        LOGGER.info("Received Taiga my secret {}", taigaConfiguration.getSecret());
        LOGGER.info("Received Taiga event {} at {} with data {}", taigaEventRequest.getData().getId()
                                , taigaEventRequest.getDate()
                                , taigaEventRequest.getData().toString());

        // destination field taigaEventRequest.getData().getCustom_attributes_values().get("quality")

        List<RequirementAnalysis> requirementAnalysisList = taigaEventReceivedService.process(taigaEventRequest);
        if (requirementAnalysisList != null) {
            taigaSenderService.updateTaigaIssue(taigaEventRequest.getData().getId(), requirementAnalysisList);
        }
        return ResponseEntity.ok("Received");
    }
}
