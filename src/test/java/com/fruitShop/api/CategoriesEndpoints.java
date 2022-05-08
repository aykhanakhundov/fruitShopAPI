package com.fruitShop.api;

import static io.restassured.RestAssured.*;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class CategoriesEndpoints {


    public static Response getCategories(String endpoint, RequestSpecification requestSpecification){
        if(requestSpecification == null) {
            return given().accept(ContentType.JSON)
                    .when().get(endpoint)
                    .then().statusCode(200)
                    .extract().response();
        }else{
            return given(requestSpecification).accept(ContentType.JSON)
                    .when().get(endpoint)
                    .then().statusCode(200)
                    .extract().response();
        }
    }


    public static RequestSpecification addPathParameter(String pathParam, String value){
        return given().pathParam(pathParam, value);
    }
}