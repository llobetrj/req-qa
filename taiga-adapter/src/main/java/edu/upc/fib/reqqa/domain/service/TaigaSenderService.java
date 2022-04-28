package edu.upc.fib.reqqa.domain.service;

import edu.upc.fib.reqqa.config.TaigaConfiguration;
import edu.upc.fib.reqqa.domain.model.RequirementAnalysis;
import edu.upc.fib.reqqa.domain.model.RequirementAnalysisDetail;
import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class TaigaSenderService {
    private static final Logger LOGGER = LoggerFactory.getLogger(TaigaSenderService.class);

    public static final String ISSUES_CUSTOM_ATTRIBUTES_VALUES = "/api/v1/issues/custom-attributes-values/";
    public static final String ISSUE_CUSTOM_ATTRIBUTES = "/api/v1/issue-custom-attributes";
    public static final String API_AUTH = "/api/v1/auth";
    public String token = "";

    private final TaigaConfiguration taigaConfiguration;

    @Autowired
    public TaigaSenderService(TaigaConfiguration taigaConfiguration) {
        this.taigaConfiguration = taigaConfiguration;
    }

    public void updateTaigaIssue(String id, List<RequirementAnalysis> requirementAnalysisList) {

        // Get token to connect to Taiga API
        token = getTokenFromTaiga();

        String customFieldId = getCustomFieldId(taigaConfiguration.getCustomFieldName());

        // get custom_field version from the issue by the id retrieved from previous from atributes_values field
        String version = getVersion(id);

        // prepare value to send
        // TODO Use a mapper
        List<String> values = new ArrayList<>();
        requirementAnalysisList.forEach(elem -> {
            // assume all are from the same id
            if (id.equals(elem.getId())) {
                List<RequirementAnalysisDetail> reqDetail = elem.getRequirementAnalysisDetailList();
                reqDetail.forEach(detail -> {
                    values.add(detail.toString());
                });

            }
        });
        String value = StringUtils.collectionToDelimitedString(Collections.singletonList(values),"\n");
        updateCustomField(id, customFieldId, version, value);
    }

    private void updateCustomField(String issueId, String customFieldId, String version, String value) {
        HttpResponse<String> response;
        HttpRequest request;

        String taigaUrl = taigaConfiguration.getBaseUrl()+ ISSUES_CUSTOM_ATTRIBUTES_VALUES + issueId;

        JSONObject jsonRequest = new JSONObject();
        JSONObject jsonAttribute = new JSONObject();
        jsonAttribute.put(customFieldId,value);

        jsonRequest.put("attributes_values",jsonAttribute);
        jsonRequest.put("version", version);
        String jsonString = jsonRequest.toString();

        request = HttpRequest.newBuilder()
                .uri(URI.create(taigaUrl))
                .method(HttpMethod.PATCH.toString(), HttpRequest.BodyPublishers.ofString(jsonString))
                .header("Content-Type", "application/json")
                .header("Authorization",token)
                .build();

       sendRequest(request);
    }

    private String getCustomFieldId(String customName) {
        // get the id of custom field
        // filter by name
        String uri = taigaConfiguration.getBaseUrl()+ ISSUE_CUSTOM_ATTRIBUTES;
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(uri))
                .GET()
                .header("Content-Type", "application/json")
                .header("Authorization", token)
                .build();

        HttpResponse<String> response = sendRequest(request);

        JSONArray res = new JSONArray(response.body());
        String customFieldId = "";
        for (Object elem : res) {
            if (((JSONObject) elem).get("name").equals(customName)) {
                customFieldId = ((JSONObject) elem).get("id").toString();
                break;
            }
        }

        return customFieldId;
    }

    private String getTokenFromTaiga()  {
        JSONObject jsonRequest = new JSONObject();

        jsonRequest.put("username",taigaConfiguration.getUsername());
        jsonRequest.put("password", taigaConfiguration.getPassword());
        jsonRequest.put("type", "normal");

        String jsonString = jsonRequest.toString();
        String uri = taigaConfiguration.getBaseUrl() + API_AUTH;
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(uri))
                .POST(HttpRequest.BodyPublishers.ofString(jsonString))
                .header("Content-Type", "application/json")
                .build();
        HttpResponse<String> response = sendRequest(request);
        JSONObject res = new JSONObject(response.body());
        return "Bearer "+res.get("auth_token").toString();
    }

    private String getVersion(String issueId) {
        String uri = taigaConfiguration.getBaseUrl()+ TaigaSenderService.ISSUES_CUSTOM_ATTRIBUTES_VALUES +issueId;
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(uri))
                .GET()
                .header("Content-Type", "application/json")
                .header("Authorization",token)
                .build();

        HttpResponse<String> response = sendRequest(request);

        JSONObject res = new JSONObject(response.body());
        return res.get("version").toString();
    }

    private HttpResponse<String> sendRequest(HttpRequest request) {
        HttpResponse<String> response = null;
        try {

            response = HttpClient.newHttpClient()
                    .send(request, HttpResponse.BodyHandlers.ofString());

            LOGGER.debug("response: {}, status code: {} " + response.body(), response.statusCode());
        } catch (Exception ex) {
            LOGGER.error("The request: " + request.uri().toString() + " ends with an error: " + ex.getMessage());
        }
        return response;
    }


}
