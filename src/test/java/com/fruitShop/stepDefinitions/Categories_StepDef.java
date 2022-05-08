package com.fruitShop.stepDefinitions;

import static com.fruitShop.api.CategoriesEndpoints.*;
import static com.fruitShop.utilities.API_Utils.*;
import static org.junit.Assert.*;
import io.cucumber.java.en.*;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import java.util.List;

public class Categories_StepDef {

    Response response = null;
    RequestSpecification requestSpecification;

    @Given("I send {string} request to {string}")
    public void i_send_request_to(String request, String endpoint) {
        if (request.equals("GET")) {
            response = getCategories(endpoint, requestSpecification);
        }
    }

    @Then("http response code should be as {int}")
    public void httpResponseCodeShouldBe(int statusCode) {
        assertEquals(statusCode, response.statusCode());
    }


    @And("http response body path  {string} should contain values:")
    public void httpResponseBodyPathShouldContain(String path, List<String> expectedCategoryNames) {
        assertTrue(responseBodyPathContainsGivenList(response, path, expectedCategoryNames));
    }


    @Given("I add path parameter {string} and value {string}")
    public void iAddPathParameterAndValue(String pathParam, String value) {
        requestSpecification = addPathParameter(pathParam, value);
    }


    @And("http response body path {string} should be {string}")
    public void httpResponseBodyPathShouldBe(String pathParam, String value) {
        assertTrue(responseBodyPathEqualsToGivenString(response, pathParam, value));
    }


}
