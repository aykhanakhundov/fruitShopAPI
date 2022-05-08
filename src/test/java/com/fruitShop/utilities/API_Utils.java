package com.fruitShop.utilities;

import static com.fruitShop.utilities.ConfigurationReader.getProperty;
import static io.restassured.RestAssured.*;
import com.fruitShop.api.pojo.Customer;
import com.fruitShop.api.pojo.Item;
import com.github.javafaker.Faker;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class API_Utils {


    static int postedCustomerId;
    static int itemId;

    public static void setBasePath(String basePath) {
        RestAssured.basePath = basePath;
    }


    public static Response sendRequest(String request, RequestSpecification requestSpecification) {
        switch (request) {
            case "GET":
                return given(requestSpecification).when().get();
            case "POST":
                Response response = given(requestSpecification).when().post();
                postedCustomerId = getPostedCustomerId(response, getProperty("customer_id_path"));
                return response;
            case "PUT":
                return given(requestSpecification).when().put(getProperty("id_path_param"));
            case "DELETE":
                return given(requestSpecification).when().delete(getProperty("id_path_param"));
            case "PATCH":
                return given(requestSpecification).when().patch(getProperty("id_path_param"));
            default:
                throw new DefaultException();
        }
    }


    public static Response sendRequestToGivenEndpoint(String request, String endpoint, RequestSpecification requestSpecification) {
        switch (request) {

            case "GET":
                return given(requestSpecification).when().get(endpoint);
            case "POST":
                Response response = given(requestSpecification).when().post(endpoint);
                response.prettyPrint();
                itemId = Integer.parseInt(response.jsonPath().getString("item_url").substring(24));
                return response;
            case "POST ":
                return given(requestSpecification).when().post(endpoint);
            case "DELETE":
                return given(requestSpecification).when().delete(endpoint);
            default:
                throw new DefaultException();
        }
    }


    public static int getPostedCustomerId(Response response, String path) {
        String customer_url = response.jsonPath().getString("customer_url");
        return Integer.parseInt(customer_url.substring(customer_url.lastIndexOf('/') + 1));
    }

    public static int returnPostedCustomerId() {
        return postedCustomerId;
    }


    public static boolean responseBodyPathContainsGivenList(Response response, String path, List<String> givenList) {
        return response.jsonPath().getList(path).containsAll(givenList);
    }


    public static RequestSpecification setHeader(String header, String value) {
        switch (header) {
            case "Accept":
                return given().accept(value);
            case "Content-Type":
                return given().contentType(value);
            default:
                throw new DefaultException();
        }
    }


    public static RequestSpecification setBody(String bodyType, RequestSpecification requestSpecification) {
        switch (bodyType) {
            case "customer pojo":
                return given(requestSpecification).body(setCustomer());
            case "customer map":
                return given(requestSpecification).body(setCustomerMapForPost());
            case "customer patch":
                return given(requestSpecification).body(mapForPatchRequestCustomer());
            case "customer put":
                return given(requestSpecification).body(setCustomerMapForPut());
            case "item pojo":
                return given(requestSpecification).body(setItem());
            default:
                throw new DefaultException();
        }
    }

    public static Customer setCustomer() {
        Faker faker = new Faker();
        Customer customer = new Customer();
        customer.setFirstName(faker.name().firstName());
        customer.setLastName(faker.name().lastName());
        customer.setOrdersUrl(faker.letterify("?????"));
        //System.out.println("customer = " + customer);
        return customer;
    }


    public static Map<String, String> setCustomerMapForPost() {
        Faker faker = new Faker();
        Map<String, String> customerMapForPost = new HashMap<>();
        customerMapForPost.put("firstname", faker.name().firstName());
        customerMapForPost.put("lastname", faker.name().lastName());
        //customerMapForPost.put("orders_url", faker.letterify("??????"));
        return customerMapForPost;
    }


    public static RequestSpecification pathParamForDelete() {
        return given().pathParam("id", returnPostedCustomerId());
    }

    public static boolean responseBodyPathEqualsToGivenString(Response response, String path, String givenStr) {
        return response.jsonPath().getString(path).equals(givenStr);
    }


    public static RequestSpecification pathGivenValueToGivenParam(String pathParam, String value) {
        return given().contentType(ContentType.JSON).pathParam(pathParam, value);
    }

    public static RequestSpecification pathGivenMapAsPathParams(Map<String, String> mapOfParams) {
        return given().contentType(ContentType.JSON).pathParams(mapOfParams);
    }


    public static Map<String, String> mapForPatchRequestCustomer() {
        Map<String, String> customerMapForPatch = new HashMap<>();
        customerMapForPatch.put("firstname", "update for PATCH request");
        return customerMapForPatch;
    }

    public static Map<String, String> setCustomerMapForPut() {
        Map<String, String> customerMapForPost = new HashMap<>();
        customerMapForPost.put("firstname", "PUT Request TEST");
        customerMapForPost.put("lastname", "PUT Request TEST");
        return customerMapForPost;
    }


    public static Item setItem() {
        Item itemPojo = new Item();
        Faker faker = new Faker();
        itemPojo.setQuantity(faker.number().numberBetween(1, 10));
        itemPojo.setPrice(faker.number().randomDouble(2, 1, 10));
        itemPojo.setItemUrl(getProperty("item_url"));
        itemPojo.setProductUrl(getProperty("product_url"));
        return itemPojo;
    }


    public static String getOrderId() {
        return getProperty("item_url").substring(13, 17);
    }


    public static String getItemId() {
        return itemId + "";
    }


}
