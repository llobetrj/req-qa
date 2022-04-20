package edu.upc.fib.reqqa.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class GenericRestController <T> {

    private Class<T> genericsType;
    private String url;
    private HttpMethod httpMethod;
    private Object objectToSend;

    private final Logger LOGGER = LoggerFactory.getLogger(GenericRestController.class);

    public void createRequest(Class<T> genericsType, String url, HttpMethod httpMethod, Object objectToSend) {
        this.genericsType = genericsType;
        this.url = url;
        this.httpMethod = httpMethod;
        this.objectToSend = objectToSend;
    }

    public T sendRequest()
    {
        return this.sendRequest("nottoken");
    }

    public T sendRequest(String token)
    {

        RestTemplate restTemplate = new RestTemplate();
        restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        httpHeaders.set("Accept", "*/*");
        httpHeaders.set("Authorization", token);

        try {
            HttpEntity<String> h = new HttpEntity<String>(objectToSend.toString(), httpHeaders);
            ResponseEntity<T> response = restTemplate.exchange(url, httpMethod, h, genericsType);

            if (response.getStatusCode() == HttpStatus.OK) {
                LOGGER.debug("response: " + response.getBody());
                LOGGER.debug("statuscode: " + response.getStatusCode());
                return response.getBody();
            } else {
                LOGGER.debug("response: " + response.getBody());
                LOGGER.debug("statuscode: " + response.getStatusCode());
                return response.getBody();
            }
        } catch (Exception ex) {
            LOGGER.error("The request: " + url + " ends with an error: " + ex.getMessage());
        }

        return  null;
    }

}
