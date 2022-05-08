@regression
Feature: Orders API tests


  Background:
    Given base path is "/orders/"
    And I set "Accept" http header to "application/json"


  Scenario: Get all the orders
    When I send "GET" request
    Then http response code should be 200
    And http response body matches "Get Orders" schema
    And http response body path "orders.createdAt[0]" should be as "2022-05-08T02:05:18.168Z"
    And http response body path "orders.state[0]" should be as "ordered"
    And http response body path "orders.order_url[0]" should be as "/shop/orders/1432"


    # there is no way to create an order, below 3 scenarios depend on orders and their statuses in jsonBody
#    # will fail if the order status is not "created"
#  Scenario: Purchase (POST) an order
#    And I add "id" as path param, and "1432" as value
#    When I send "POST" request to "{id}/actions/purchase" endpoint
#    Then http response code should be 200
#
#
#
#     # will fail if the order status is not "ordered"
#  Scenario: Cancel (POST) an order
#    And I add "id" as path param, and "7944" as value
#    When I send "POST" request to "{id}/actions/cancel" endpoint
#    Then http response code should be 200
#    And http response body path "state" should be as "canceled"
#
#
#
#    # there is no way to create an order, so delete order request is limited
#  Scenario: Delete an order
#    And I add "id" as path param, and "7902" as value
#    When I send "DELETE" request to "{id}" endpoint
#    Then http response code should be 200
#    And I add "id" as path param, and "7902" as value
#    When I send "GET" request to "{id}" endpoint
#    Then http response code should be 404


  Scenario: Get an order by id
    And I add "id" as path param, and "2242" as value
    When I send "GET" request to "{id}" endpoint
    Then http response code should be 200
    And http response body matches "Get Single Order" schema
    And http response body path "items_url" should be as "/shop/orders/2242/items/"


  Scenario: Get the items of an order
    And I add "id" as path param, and "2249" as value
    When I send "GET" request to "{id}/items/" endpoint
    Then http response code should be 200
    And http response body matches "Get Items of Order" schema
    And http response body path "items.item_url[0]" should be as "/shop/orders/2249/items/8"
    And http response body path "items.product_url[0]" should be as "/shop/products/50"


  Scenario: Add an item to an order
    And I set "Content-Type" http header to "application/json"
    And I add "id" as path param, and "7957" as value
    And I set http request body to "item pojo"
    When I send "POST" request to "{id}/items/" endpoint
    Then http response code should be 201
    And http response body matches "Post Item to Order" schema


  Scenario: Delete an item of an order
    And I add "oid" and "iid" path parameters and values from recent added item
    When I send "DELETE" request to "{oid}/items/{iid}" endpoint
    Then http response code should be 200


  Scenario: Get an item of an order
    And I add "oid" and "iid" path parameters, "2242" and "6" as values
    When I send "GET" request to "{oid}/items/{iid}" endpoint
    Then http response code should be 200
    And http response body matches "Get Item of Order" schema













