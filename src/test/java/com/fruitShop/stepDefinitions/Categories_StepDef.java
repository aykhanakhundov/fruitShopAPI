package com.fruitShop.stepDefinitions;

import static com.fruitShop.api.CategoriesEndpoints.*;
import static org.junit.Assert.assertEquals;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import java.util.List;

public class Categories_StepDef {

    Response response = null;
    List<String> actualCategoryNames;
    RequestSpecification requestSpecification;

    @Given("I send {string} request to {string}")
    public void i_send_request_to(String request, String endpoint) {

        switch (request){
            case "GET":
                response = getCategories(endpoint, requestSpecification);
                break;
        }


    }

    @Then("http response code should be {int}")
    public void httpResponseCodeShouldBe(int statusCode) {
        assertEquals(statusCode, response.statusCode());
    }





    @And("http response body path  {string} should contain:")
    public void httpResponseBodyPathShouldContain(String path, List<String> expectedCategoryNames) {
        actualCategoryNames = response.jsonPath().getList(path);
        assertEquals(expectedCategoryNames, actualCategoryNames);
    }


    @Given("I add path parameter {string} and value {string}")
    public void iAddPathParameterAndValue(String pathParam, String value) {
        requestSpecification = addPathParameter(pathParam, value);
    }



    @And("http response body path {string} should be {string}")
    public void httpResponseBodyPathShouldBe(String path, String expectedValue) {


    }


}