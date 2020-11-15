Feature: User should be able to get customer information by first name

  Scenario: Response status should return 404 for not existing customer
   # This test will fail. Response status code is 200 even though the customer is not existing
    When non-existing customer info is queried
    Then get endpoint status code should be 404

  Scenario: Response status should return 200 for existing customer
    Given existing customer
    When existing customer info is queried
    Then get endpoint status code should be 200

  Scenario: Response status should return 405 for method not allowed
    When empty request is queried
    Then get endpoint status code should be 405

  Scenario: Response body should contain customer information for existing customer
    Given existing customer
    When existing customer info is queried
    Then response body should contain the queried customer information