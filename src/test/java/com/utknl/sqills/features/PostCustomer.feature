Feature: User should be able to add new customers by first and last name

  Scenario: Response status should return 400 for unexpected request
    Given an invalid request body
    When invalid request is sent to client
    Then post endpoint status code should be 400

  Scenario: Response status should return 201 for adding new customer
    Given valid request body
    When a valid request is sent to client
    Then post endpoint status code should be 201

  Scenario: Existing customer should not be re-added
    Given valid request body
    When a valid request is sent to client
    And same customer is added again
    Then post endpoint status code should be 409

  Scenario: Adding new customer should return customer information as response
    Given valid request body
    When a valid request is sent to client
    Then client response should contain customer information

  Scenario: Only first name should not add new customer
   # or should add? -> This test will fail all the time since adding user with only unique first_name is applicable
    Given request body with only first name
    When a valid request is sent to client
    Then post endpoint status code should be 400

  Scenario: Only last name should not add new customer
   # This test will fail. At first system accepts request without first_name and creates a user as first_name=null.
   # Every new attempt without first_name returns as 409 - customer already exists after that.
    Given request body with only last name
    When a valid request is sent to client
    Then post endpoint status code should be 400

  Scenario Outline: Empty first name should not add new customer
   # This test will fail. At first system accepts request with empty first_name and creates a user as first_name="".
   # Every new attempt with empty first_name returns as '409 - customer already exists' after that.
    Given valid request body with '<first>' and '<last>' name
    When a valid request is sent to client
    Then post endpoint status code should be 400
    Examples:
      | first | last    |
      | ""    | menekse |
      | ""    | sumbul  |
      | ""    | leylak  |
      | ""    | papatya |

  Scenario Outline: Empty last name should not add new customer
   # This test will fail. System accepts request with empty last_name if the first_name is not defined before.
   # And if the first_name is defined, it returns '409 - customer already exists'
    Given valid request body with '<first>' and '<last>' name
    When a valid request is sent to client
    Then post endpoint status code should be 400
    Examples:
      | first   | last |
      | audi    | ""   |
      | toyota  | ""   |
      | mazda   | ""   |
      | renault | ""   |

  Scenario Outline: First and Last name with longer than 32 characters should not be accepted
   # This test will fail. Since there is no character input limitation, it will add the customer
   # at first try and will return '409 - customer already exists' after that
    Given valid request body with '<first>' and '<last>' name
    When a valid request is sent to client
    Then post endpoint status code should be 400
    Examples:
      | first                                                          | last                                                           |
      | adefghijklmnoprqstuvxzasdsdvfdv                                | adefghijklmnoprqstuvxzasdsdvfdv                                |
      | adefghijklmnoprqstuvxzasdsdvfdd                                | adefghijklmnoprqstuvxzasdsdvfdd                                |
      | adefghijklmnoprqstuvxzasdsdvfdvadefghijklmnoprqstuvxzasdsdvfdv | adefghijklmnoprqstuvxzasdsdvfdvadefghijklmnoprqstuvxzasdsdvfdv |
      | adefghijklmnoprqstuvxzasdsdvfdvasdrvsvfbn                      | adefghijklmnoprqstuvxzasdsdvfdvasdrvsvfbn                      |

  Scenario Outline: First and Last name with non-alphabetic characters should not be accepted
   # This test will fail. Since there is no character controller, it will add the customer
   # at first try and will return '409 - customer already exists' after that
    Given valid request body with '<first>' and '<last>' name
    When a valid request is sent to client
    Then post endpoint status code should be 400
    Examples:
      | first   | last  |
      | ./=-    | ./-=  |
      |         |       |
      | 00      | 00    |
      | _ _ _   | _ _ _ |
      | ???!!\/ | $ # # |