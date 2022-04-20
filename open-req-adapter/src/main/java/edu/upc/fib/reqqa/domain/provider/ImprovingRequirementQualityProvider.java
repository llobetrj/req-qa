package edu.upc.fib.reqqa.domain.provider;

import com.fasterxml.jackson.databind.JsonNode;
import edu.upc.fib.reqqa.config.OpenReqConfiguration;
import edu.upc.fib.reqqa.domain.model.Requirement;
import edu.upc.fib.reqqa.domain.provider.mapper.RequirementMapper;
import edu.upc.fib.reqqa.util.GenericRestController;
import org.springframework.beans.factory.annotation.Autowired;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ImprovingRequirementQualityProvider {

    private final Logger LOGGER = LoggerFactory.getLogger(ImprovingRequirementQualityProvider.class);

    private final OpenReqConfiguration openReqConfiguration;

    private final GenericRestController<JsonNode> serverCommunication;

    private final RequirementMapper requirementMapper;

    @Autowired
    public ImprovingRequirementQualityProvider(OpenReqConfiguration openReqConfiguration, GenericRestController<JsonNode> serverCommunication, RequirementMapper requirementMapper) {
        this.openReqConfiguration = openReqConfiguration;
        this.serverCommunication = serverCommunication;
        this.requirementMapper = requirementMapper;
    }

    public JsonNode getImprovedRequirements(List<Requirement> requirementList) {

        String addReqsUrl = openReqConfiguration.getBaseUrl()+openReqConfiguration.getCheckQuality();
        String jsonString = requirementMapper.toJson(requirementList);

        serverCommunication.createRequest(JsonNode.class,
                addReqsUrl,
                HttpMethod.POST,
                jsonString);
        LOGGER.debug("Sending request to: {} with {}", addReqsUrl, requirementList.toString());
        return serverCommunication.sendRequest();
    }


}
