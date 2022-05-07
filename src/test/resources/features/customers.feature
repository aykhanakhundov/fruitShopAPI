Feature: Customers API tests

  Background:
    Given base path is "/customers/"

  Scenario: Get all the customers
    When I send "GET" request
    Then http response code should be 200
    And http response body path  "customers.firstname" should contain:
      | Joe     |
      | Michael |
      | David   |
      | Anne    |
      | Alice   |

    Scenario: Create a customer