package com.fruitShop.utilities;

import static io.restassured.RestAssured.*;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import java.util.List;


public class API_Utils {


    public static void setBasePath(String basePath) {
        RestAssured.basePath = basePath;
    }


    public static Response sendRequest(String request) {
        switch (request) {
            case "GET":
                return when().get();
            case "POST":
                return when().post();
            case "PUT":
                return when().put();
            default:
                throw new DefaultException();
        }
    }

    public static boolean responseBodyPathContainsGivenList(Response response, String path, List<String> givenList){
        return response.jsonPath().getList(path).containsAll(givenList);
    }





}
