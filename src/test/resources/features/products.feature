@regression
Feature: Products API tests


  Background:
    Given base path is "/products/"
    And I set "Accept" http header to "application/json"


  Scenario: Get all the products
    When I send "GET" request
    Then http response code should be 200
    And http response body path "products.name[2]" should be as "Pineapples"
    And http response body path "products.name[4]" should be as "Cranberries"


  Scenario: Create a product
    And I set "Content-Type" http header to "application/json"
    And I set http request body to "product pojo"
    When I send "POST " request
    Then http response code should be 201


  Scenario: Delete a product
    And I add "id" as path parameter and posted product id as value
    When I send "DELETE" request
    Then http response code should be 200
    And I send "GET" request for deleted product
    Then http response code should be 404


  Scenario: Get a product by id
    And I add "id" as path param, and "90" as value
    When I send "GET" request to "{id}" endpoint
    Then http response code should be 200
    And http response body path "name" should be as "Raspberries"
    And http response body path "vendor_url" should be as "/shop/vendors/672"


  Scenario: Update (PATCH) one or more properties of a product
    And I set "Content-Type" http header to "application/json"
    And I add "id" as path param, and "10" as value
    And I set http request body to "product patch"
    When I send "PATCH" request to "{id}" endpoint
    Then http response code should be 200
    And http response body path "name" should be as "update for PATCH request"



    Scenario: Replace (PUT) a product by new data
      And I set "Content-Type" http header to "application/json"
      And I add "id" as path param, and "18" as value
      And I set http request body to "product pojo"
      When I send "PUT" request to "{id}" endpoint
      Then http response code should be 200








