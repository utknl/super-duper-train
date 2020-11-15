Feature: User should be able to delete existing customers by first name

  Scenario: Response status should return 404 for not existing customer
   # This test will fail. Response status code is 204 even though the customer is existing or not
    When non-existing customer info is deleted
    Then delete endpoint status code should be 404

  Scenario: Response status should return 204 for existing customer
    Given existing customer to delete
    When existing customer info is deleted
    Then delete endpoint status code should be 204

  Scenario: Response body should not be empty when deleting customer
   # This test will fail. Response body is empty even though the existing customer is deleted
    Given existing customer to delete
    When existing customer info is deleted
    Then response body should not be empty

  Scenario: Response status should return 405 for method not allowed
    When empty request is deleted
    Then delete endpoint status code should be 405