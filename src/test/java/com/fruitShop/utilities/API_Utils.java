package com.fruitShop.utilities;

import static com.fruitShop.utilities.ConfigurationReader.getProperty;
import static io.restassured.RestAssured.*;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import com.fruitShop.api.pojo.Customer;
import com.fruitShop.api.pojo.Item;
import com.fruitShop.api.pojo.Product;
import com.fruitShop.api.pojo.Vendor;
import com.github.javafaker.Faker;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class API_Utils {

    static Faker faker;
    static int postedCustomerId;
    static int postedProductId;
    static int postedVendorId;
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
            case "POST ":
                Response response1 = given(requestSpecification).when().post();
                postedProductId = getPostedProductId(response1, getProperty("product_id_path"));
                return response1;
            case " POST ":
                Response response2 = given(requestSpecification).when().post();
                postedVendorId = getPostedVendorId(response2, getProperty("vendor_id_path"));
                return response2;
            case "PUT":
                return given(requestSpecification).when().put(getProperty("id_path_param"));
            case "DELETE":
                return given(requestSpecification).when().delete(getProperty("id_path_param"));
            case "PATCH":
                return given(requestSpecification).when().patch(getProperty("id_path_param"));
            default:
                throw new DefaultException("NO SUCH REQUEST!");
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
            case "PATCH":
                return given(requestSpecification).when().patch(endpoint);
            case "PUT":
                return given(requestSpecification).when().put(endpoint);
            default:
                throw new DefaultException("NO SUCH REQUEST!");
        }
    }


    public static int getPostedCustomerId(Response response, String path) {
        String customer_url = response.jsonPath().getString("customer_url");
        return Integer.parseInt(customer_url.substring(customer_url.lastIndexOf('/') + 1));
    }


    public static int getPostedProductId(Response response, String path) {
        String product_url = response.jsonPath().getString("product_url");
        return Integer.parseInt(product_url.substring(product_url.lastIndexOf('/') + 1));
    }


    public static int getPostedVendorId(Response response, String path) {
        String product_url = response.jsonPath().getString("vendor_url");
        return Integer.parseInt(product_url.substring(product_url.lastIndexOf('/') + 1));
    }


    public static int returnPostedCustomerId() {
        return postedCustomerId;
    }


    public static int returnPostedProductId() {
        return postedProductId;
    }


    public static int returnPostedVendorId() {
        return postedVendorId;
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
                throw new DefaultException("NO SUCH HEADER!");
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
            case "product pojo":
                return given(requestSpecification).body(setProduct());
            case "product patch":
                return given(requestSpecification).body(mapForPatchRequestProduct());
            case "vendor pojo":
                return given(requestSpecification).body(setVendor());
            default:
                throw new DefaultException("NO SUCH BODY!");
        }
    }


    public static Customer setCustomer() {
        faker = new Faker();
        Customer customer = new Customer();
        customer.setFirstName(faker.name().firstName());
        customer.setLastName(faker.name().lastName());
        customer.setOrdersUrl(faker.letterify("?????"));
        //System.out.println("customer = " + customer);
        return customer;
    }


    public static Map<String, String> setCustomerMapForPost() {
        faker = new Faker();
        Map<String, String> customerMapForPost = new HashMap<>();
        customerMapForPost.put("firstname", faker.name().firstName());
        customerMapForPost.put("lastname", faker.name().lastName());
        //customerMapForPost.put("orders_url", faker.letterify("??????"));
        return customerMapForPost;
    }


    public static RequestSpecification pathParamForDelete(String pathParam) {
        return given().pathParam(pathParam, returnPostedCustomerId());
    }


    public static RequestSpecification pathParamForDeleteProduct(String pathParam) {
        return given().pathParam(pathParam, returnPostedProductId());
    }


    public static RequestSpecification pathParamForDeleteVendor(String pathParam) {
        return given().pathParam(pathParam, returnPostedVendorId());
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


    public static Map<String, String> mapForPatchRequestProduct() {
        Map<String, String> productMapForPatch = new HashMap<>();
        productMapForPatch.put("name", "update for PATCH request");
        return productMapForPatch;
    }


    public static Map<String, String> setCustomerMapForPut() {
        Map<String, String> customerMapForPost = new HashMap<>();
        customerMapForPost.put("firstname", "PUT Request TEST");
        customerMapForPost.put("lastname", "PUT Request TEST");
        return customerMapForPost;
    }


    public static Item setItem() {
        Item itemPojo = new Item();
        faker = new Faker();
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


    public static Product setProduct() {
        Product product = new Product();
        faker = new Faker();
        product.setName(faker.food().fruit());
        product.setPrice(faker.number().randomDouble(2, 1, 100));
        product.setCategoryUrl(getProperty("category_url"));
        product.setVendorUrl(getProperty("vendor_url"));
        return product;
    }


    public static Vendor setVendor() {
        Vendor vendor = new Vendor();
        faker = new Faker();
        vendor.setName(faker.company().name());
        return vendor;
    }


    public static void matchesGivenJsonSchema(Response response, String schemaName) {
        switch (schemaName) {
            case "Get All Vendors":
                response.then().assertThat().body(matchesJsonSchemaInClasspath("schemas/getAllVendorsSchema.json"));
                break;
            case "Get Single Vendor":
                response.then().assertThat().body(matchesJsonSchemaInClasspath("schemas/getSingleVendorSchema.json"));
                break;
            case "Get Products of Vendor":
                response.then().assertThat().body(matchesJsonSchemaInClasspath("schemas/getProductsOfVendorSchema.json"));
                break;
            case "Get All Products":
                response.then().assertThat().body(matchesJsonSchemaInClasspath("schemas/getAllProductsSchema.json"));
                break;
            case "Get Single Product":
                response.then().assertThat().body(matchesJsonSchemaInClasspath("schemas/getSingleProductSchema.json"));
                break;
            case "Patch Product":
                response.then().assertThat().body(matchesJsonSchemaInClasspath("schemas/patchProductSchema.json"));
                break;
            case "Post Product":
                response.then().assertThat().body(matchesJsonSchemaInClasspath("schemas/postProductSchema.json"));
                break;
            case "Put Product":
                response.then().assertThat().body(matchesJsonSchemaInClasspath("schemas/putProductSchema.json"));
                break;
            case "Post Vendor":
                response.then().assertThat().body(matchesJsonSchemaInClasspath("schemas/postVendorSchema.json"));
                break;
            case "Get Orders":
                response.then().assertThat().body(matchesJsonSchemaInClasspath("schemas/getAllOrdersSchema.json"));
                break;
            case "Get Single Order":
                response.then().assertThat().body(matchesJsonSchemaInClasspath("schemas/getSingleOrderSchema.json"));
                break;
            case "Get Items of Order":
                response.then().assertThat().body(matchesJsonSchemaInClasspath("schemas/getItemsOfOrderSchema.json"));
                break;
            case "Post Item to Order":
                response.then().assertThat().body(matchesJsonSchemaInClasspath("schemas/postItemToOrderSchema.json"));
                break;
            case "Get Item of Order":
                response.then().assertThat().body(matchesJsonSchemaInClasspath("schemas/getAnItemOfOrderSchema.json"));
                break;
            case "Get All Customers":
                response.then().assertThat().body(matchesJsonSchemaInClasspath("schemas/getAllCustomersSchema.json"));
                break;
            case "Post Customer":
                response.then().assertThat().body(matchesJsonSchemaInClasspath("schemas/postCustomerSchema.json"));
                break;
            case "Get Single Customer":
                response.then().assertThat().body(matchesJsonSchemaInClasspath("schemas/getSingleCustomerSchema.json"));
                break;
            default:
                throw new DefaultException("NO SUCH SCHEMA!");
        }
    }


}
