@regression
Feature: Customers API tests

  Background:
    Given base path is "/customers/"
    And I set "Accept" http header to "application/json"


  Scenario: Get all the customers
    When I send "GET" request
    Then http response code should be 200
    And http response body path  "customers.firstname" should contain:
      | David  |
      | Anne   |
      | Alice |
      | Michael   |

  Scenario: Create a customer
    And I set "Content-Type" http header to "application/json"
    And I set http request body to "customer pojo"
    When I send "POST" request
    Then http response code should be 201


  Scenario: Delete a customer
    And I add "{id}" as path parameter and posted customer id as value
    When I send "DELETE" request
    Then http response code should be 200
    And I send "GET" request for deleted customer
    Then http response code should be 404


  Scenario: Get a customer by id
    And I add "id" as path param, and "342" as value
    When I send "GET" request to "{id}" endpoint
    Then http response code should be 200
    And http response body path "firstname" should be as "Alice"
    And http response body path "lastname" should be as "Eastman"


  Scenario: Update a customer
    And I set "Content-Type" http header to "application/json"
    And I add "id" as path param, and "345" as value
    And I set http request body to "customer patch"
    When I send "PATCH" request
    Then http response code should be 200
    And http response body path "firstname" should be as "update for PATCH request"


  Scenario: Replace a customer by new data
    And I set "Content-Type" http header to "application/json"
    And I add "id" as path param, and "345" as value
    And I set http request body to "customer put"
    When I send "PUT" request
    Then http response code should be 200
    And http response body path "firstname" should be as "PUT Request TEST"
    And http response body path "lastname" should be as "PUT Request TEST"


  Scenario: Create an order for a customer
    And I add "id" as path param, and "102" as value
    When I send "POST " request to "{id}/orders/" endpoint
    Then http response code should be 201

  Scenario: Get the orders of a customer
    And I add "id" as path param, and "102" as value
    When I send "GET" request to "{id}/orders/" endpoint
    Then http response code should be 200
    And http response body path "orders.order_url[0]" should be as "/shop/orders/7960"














