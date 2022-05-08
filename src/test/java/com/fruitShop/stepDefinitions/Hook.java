package com.fruitShop.stepDefinitions;

import com.fruitShop.utilities.ConfigurationReader;
import io.cucumber.java.Before;
import io.restassured.RestAssured;

public class Hook {

    @Before
    public void setup(){
        RestAssured.baseURI = ConfigurationReader.getProperty("base_url");
    }

}
