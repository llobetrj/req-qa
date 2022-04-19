package edu.upc.fib.reqqa.domain.provider;

import com.fasterxml.jackson.databind.JsonNode;
import edu.upc.fib.reqqa.config.OpenReqConfiguration;
import edu.upc.fib.reqqa.domain.model.Requirement;
import edu.upc.fib.reqqa.util.GenericRestController;
import org.springframework.beans.factory.annotation.Autowired;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ImprovingRequirementQualityProvider {
    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    private final OpenReqConfiguration openReqConfiguration;

    private final GenericRestController<JsonNode> serverCommunication;

    public ImprovingRequirementQualityProvider(@Autowired OpenReqConfiguration openReqConfiguration, @Autowired GenericRestController<JsonNode> serverCommunication) {
        this.openReqConfiguration = openReqConfiguration;
        this.serverCommunication = serverCommunication;
    }

    public JsonNode getImprovedRequirements(List<Requirement> requirementList) {

        String addReqsUrl = openReqConfiguration.getBaseUrl()+openReqConfiguration.getCheckQuality();

        serverCommunication.createRequest(JsonNode.class,
                addReqsUrl,
                HttpMethod.POST,
                requirementList);
        LOGGER.debug("Sending request to: {} with {}", addReqsUrl, requirementList.toString());
        return serverCommunication.sendRequest();
    }
}
