@regression
Feature: Vendors API tests

  Background:
    Given base path is "/vendors/"
    And I set "Accept" http header to "application/json"


  Scenario: Get all the vendors
    When I send "GET" request
    Then http response code should be 200
    And http response body matches "Get All Vendors" schema
    And http response body path "vendors.name[0]" should be as "Western Tasty Fruits Ltd."
    And http response body path "vendors.name[1]" should be as "Exotic Fruits Company"


  Scenario: Create a vendor
    And I set "Content-Type" http header to "application/json"
    And I set http request body to "vendor pojo"
    When I send " POST " request
    Then http response code should be 201
    And http response body matches "Post Vendor" schema


  Scenario: Delete a vendor
    And I add "id" as path parameter and posted vendor id as value
    When I send "DELETE" request
    Then http response code should be 200
    And I send "GET" request for deleted vendor
    Then http response code should be 404


  Scenario: Get a vendor by id
    And I add "id" as path param, and "811" as value
    When I send "GET" request to "{id}" endpoint
    Then http response code should be 200
    And http response body matches "Get Single Vendor" schema
    And http response body path "name" should be as "Murray, Durgan and Reynolds"


  Scenario: Get the products of a vendor
    And I add "id" as path param, and "32" as value
    When I send "GET" request to "{id}/products/" endpoint
    Then http response code should be 200
    And http response body matches "Get Products of Vendor" schema
    And http response body path "products.name[0]" should be as "Coconut"
    And http response body path "products.name[1]" should be as "Dragon Fruit"




