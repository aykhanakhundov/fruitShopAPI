package com.fruitShop.stepDefinitions;

import static com.fruitShop.utilities.API_Utils.*;
import static org.junit.Assert.*;
import io.cucumber.java.en.*;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class Default_StepDef {

    Response response;
    RequestSpecification requestSpecification;

    @Given("base path is {string}")
    public void base_path_is(String basePath) {
        setBasePath(basePath);
    }


    @And("I send {string} request")
    public void iSendRequest(String request) {
        response = sendRequest(request, requestSpecification);
    }

    @When("I send {string} request to {string} endpoint")
    public void iSendRequestToEndpoint(String request, String endpoint) {
        response = sendRequestToGivenEndpoint(request, endpoint, requestSpecification);
    }


    @Then("http response code should be {int}")
    public void httpResponseCodeShouldBe(int statusCode) {
        response.prettyPrint();
        assertEquals(statusCode, response.statusCode());
    }

    @And("http response body path  {string} should contain:")
    public void httpResponseBodyPathShouldContain(String path, List<String> expectedValuesList) {
        response.prettyPrint();
        assertTrue(responseBodyPathContainsGivenList(response, path, expectedValuesList));
    }

    @And("I set {string} http header to {string}")
    public void iSetHttpHeaderTo(String header, String value) {
        requestSpecification = setHeader(header, value);
    }

    @And("I set http request body to {string}")
    public void iSetHttpRequestBodyTo(String body) {
        requestSpecification = setBody(body, this.requestSpecification);
    }

    @And("I add {string} as path parameter and posted customer id as value")
    public void iAddAsPathParameterAndPostedCustomerIdAsValue(String pathParam) {
        requestSpecification = pathParamForDelete(pathParam);
    }

    @And("I send {string} request for deleted customer")
    public void iSendRequestForDeletedCustomer(String request) {
        response = RestAssured.given().pathParam("id", returnPostedCustomerId()).when().get("{id}");
    }

    @And("I send {string} request for deleted product")
    public void iSendRequestForDeletedProduct(String request) {
        response = RestAssured.given().pathParam("id", returnPostedProductId()).when().get("{id}");
    }

    @And("I send {string} request for deleted vendor")
    public void iSendRequestForDeletedVendor(String request) {
        response = RestAssured.given().pathParam("id", returnPostedVendorId()).when().get("{id}");
    }


    @And("I add {string} as path param, and {string} as value")
    public void iAddAsPathParamAndAsValue(String pathParam, String value) {
        requestSpecification = pathGivenValueToGivenParam(pathParam, value);
    }

    @And("http response body path {string} should be as {string}")
    public void httpResponseBodyPathShouldBeAs(String pathParam, String value) {
        assertTrue(responseBodyPathEqualsToGivenString(response, pathParam, value));
    }


    @And("I add {string} and {string} path parameters and values from recent added item")
    public void iAddAndPathParametersAndValuesFromRecentAddedItem(String pathParam1, String pathParam2) {
        Map<String, String> mapOfParams = new HashMap<>();
        mapOfParams.put(pathParam1, getOrderId());
        mapOfParams.put(pathParam2, getItemId());
        requestSpecification = pathGivenMapAsPathParams(mapOfParams);
    }

    @And("I add {string} and {string} path parameters, {string} and {string} as values")
    public void iAddAndPathParametersAndAsValues(String pathParam1, String pathParam2, String value1, String value2) {
        Map<String, String> mapOfParams = new HashMap<>();
        mapOfParams.put(pathParam1, value1);
        mapOfParams.put(pathParam2, value2);
        requestSpecification = pathGivenMapAsPathParams(mapOfParams);
    }

    @And("I add {string} as path parameter and posted product id as value")
    public void iAddAsPathParameterAndPostedProductIdAsValue(String pathParam) {
        requestSpecification = pathParamForDeleteProduct(pathParam);
    }


    @And("I add {string} as path parameter and posted vendor id as value")
    public void iAddAsPathParameterAndPostedVendorIdAsValue(String pathParam) {
        requestSpecification = pathParamForDeleteVendor(pathParam);
    }


}






