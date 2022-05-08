@regression
Feature: Categories API tests


  Scenario: Get all the product categories
    Given I send "GET" request to "/categories/"
    Then http response code should be as 200
    And http response body path  "categories.name" should contain values:
      | Fruits |
      | Dried  |
      | Fresh  |
      | Exotic |
      | Nuts   |


  Scenario Outline: Get a category by id
    Given I add path parameter "id" and value "<category name>"
    And I send "GET" request to "/categories/{id}"
    Then http response code should be as 200
    And http response body path "name" should be "<category name>"
    Examples:
      | category name |
      | Fruits        |
      | Dried         |
      | Fresh         |
      | Exotic        |
      | Nuts          |