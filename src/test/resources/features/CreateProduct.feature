Feature: Product Management

  Scenario: Successfully create a product
    Given a valid product creation request
    When the client sends a POST request to create a product
    Then the response should contain the created product details

  Scenario: Fail to create a product due to invalid request
    Given an invalid product creation request
    When the client sends an invalid POST request to create a product
    Then the response should return a Bad Request status
