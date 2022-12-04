package br.com.service;

import br.com.utils.Keys;
import br.com.utils.PropertiesUtil;
import br.com.utils.RestUtil;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import java.util.HashMap;
import org.json.simple.JSONObject;
import org.junit.Assert;
import org.junit.BeforeClass;

public class Service {

    private JSONObject jsonPayload;
    private String jsonPath = "src/test/resources/payloads/";
    private Response response;
    private RequestSpecification request;

    public Service() {

    }

    //---------------------------- SET URLS ---------------------------- //

    public void setBasePath(String endpoint) {
        RestUtil.setBaseUrl(PropertiesUtil.getProperties("prop.environment"), PropertiesUtil.getProperties(endpoint));
    }

    //---------------------------- HEADERS ---------------------------- //

    public void setDefaultHeaders() {
        HashMap<String, String> headers = new HashMap<>();
        headers.put("Content-type", Keys.APPLICATION_JSON);
        RestUtil.setHeader(headers);
    }

    //---------------------------- REQUESTS ---------------------------- //

    public void getRequest(String endpoint) {
        setBasePath(endpoint);
        setDefaultHeaders();
        response = RestUtil.getRequest();
        System.out.println("---------------------- BACKEND RESPONSE ------------------------");
        response.prettyPrint();
    }

    public void getRequestWithPathParam(String endpoint, String param, String value) {
        setBasePath(endpoint);
        setDefaultHeaders();
        RestUtil.setPathParameter(param, value);
        response = RestUtil.getRequest();
        System.out.println("---------------------- BACKEND RESPONSE ------------------------");
        response.prettyPrint();
    }

    public void postRequest(String endpoint, String json) {
        this.setBasePath(endpoint);
        this.setDefaultHeaders();
        this.setBodyWithJson(json);
        this.response = RestUtil.postRequest();
        System.out.println("---------------------- BACKEND RESPONSE ------------------------");
        response.prettyPrint();
    }

    //---------------------------- BODY ---------------------------- //

    public void setBodyWithJson(String body) {
        jsonPayload = RestUtil.readJson(jsonPath, body);
        RestUtil.setBody(jsonPayload);
    }

    public int getStatusCode() {
        System.out.println("The response status code is: " + response.getStatusCode());
        return response.getStatusCode();
    }

    public String getBody(String key) {
        return response.getBody().jsonPath().getString(key);
    }

    public void checkContractType(Class<?> type, String jsonAttribute) {
        Assert.assertEquals(type, response.jsonPath().get(jsonAttribute).getClass());
    }

    @BeforeClass
    public void tearDown() {
        request = null;
        response = null;
    }
}
