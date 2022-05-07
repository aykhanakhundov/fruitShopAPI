package com.fruitShop.stepDefinitions;

import static com.fruitShop.utilities.API_Utils.*;
import static org.junit.Assert.*;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import java.util.List;


public class Customers_StepDef {

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
    public void iAddAsPathParameterAndPostedCustomerIdAsValue(String endPoint) {
        requestSpecification = pathParamForDelete();
    }

    @And("I send {string} request for deleted customer")
    public void iSendRequestForDeletedCustomer(String request) {
        response = RestAssured.given().pathParam("id",returnPostedCustomerId()).when().get("{id}");
    }


    @And("I add {string} as path param, and {string} as value")
    public void iAddAsPathParamAndAsValue(String pathParam, String value) {
        requestSpecification = pathGivenValueToGivenParam(pathParam, value);
    }

    @And("http response body path {string} should be as {string}")
    public void httpResponseBodyPathShouldBeAs(String pathParam, String value) {
        assertTrue(responseBodyPathEqualsToGivenString(response, pathParam, value));
    }


}






