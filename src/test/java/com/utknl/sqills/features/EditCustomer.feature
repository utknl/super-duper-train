Feature: User should be able to edit customers by first name

  Scenario: Response status should return 404 for editing non-existing customer
    When non-existing customer info is edited
    Then edit endpoint status code should be 404

  Scenario: Response status code should be 204 for editing existing customer
    Given existing customer info to edit
    When existing customer info is edited
    Then edit endpoint status code should be 204

  Scenario: Response body should not be empty when editing customer
    # This test will fail. Response body is empty even though the existing customer is edited
    Given existing customer info to edit
    When existing customer info is edited
    Then edit response body should not be empty

  Scenario: Editing an existing customer information should be successful
    Given existing customer info to edit
    And previous last name
    When existing customer info is edited
    Then customer last name should be changed