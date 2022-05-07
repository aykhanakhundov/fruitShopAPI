package com.fruitShop.stepDefinitions;

import static com.fruitShop.utilities.API_Utils.*;
import static org.junit.Assert.*;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.restassured.response.Response;

import java.util.List;


public class Customers_StepDef {

    Response response;

    @Given("base path is {string}")
    public void base_path_is(String basePath) {
       setBasePath(basePath);
    }


    @And("I send {string} request")
    public void iSendRequest(String request) {
        response = sendRequest(request);

    }

    @Then("http response code should be {int}")
    public void httpResponseCodeShouldBe(int statusCode) {
        assertEquals(statusCode, response.statusCode());
    }

    @And("http response body path  {string} should contain:")
    public void httpResponseBodyPathShouldContain(String path, List<String> expectedValuesList) {
        assertTrue(responseBodyPathContainsGivenList(response, path, expectedValuesList));
    }
}
