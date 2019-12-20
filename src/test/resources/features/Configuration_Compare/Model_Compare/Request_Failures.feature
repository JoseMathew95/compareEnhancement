Feature:Gives Appropriate error messages for Request Failures

  The Service will provide enough Messages and indication of the Failure of the Request to the User.


  Scenario: Service has no data to return for the requested Models.
    Given Mathew has a list of vehicles to be Compared:
      |code             |llpCode|
      |IUT2018DS6L62B   |2TB    |
      |CUD201811WDEL75A |245    |
    And has a stateCode for the Comparison
      |stateCode|
      |LA       |
    When  The user makes a post request to the Configuration-Service
    Then  Should get a 500 response with the Error Message.

  Scenario: User makes a Put Request against the service.
    Given Mathew has a list of vehicles to be Compared:
      |code             |llpCode|
      |IUT201813DS6L62B |2TB    |
      |CUD201811WDEL75A |2TA    |
    And has a stateCode for the Comparison
      |stateCode|
      |LA       |
    When  The user makes a PUT request to the Configuration-Service
    Then  Should get a 501 response with the Error Message.

  Scenario: User makes a GET Request against the service.
    Given Mathew has a list of vehicles to be Compared:
      |code             |llpCode|
      |IUT201813DS6L62B |2TB    |
      |CUD201811WDEL75A |2TA    |
    And has a stateCode for the Comparison
      |stateCode|
      |LA       |
    When  The user makes a GET request to the Configuration-Service
    Then  Should get a 400 response with the Error Message.

  Scenario: User makes a Patch Request against the service.
    Given Mathew has a list of vehicles to be Compared:
      |code             |llpCode|
      |IUT201813DS6L62B |2TB    |
      |CUD201811WDEL75A |2TA    |
    And has a stateCode for the Comparison
      |stateCode|
      |LA       |
    When  The user makes a PATCH request to the Configuration-Service
    Then  Should get a 501 response with the Error Message.

  Scenario: Missing stateCode in the request body.
    Given Mathew has a list of vehicles to be Compared:
      |code             |llpCode|
      |IUT201813DS6L62B |2TB    |
      |CUD201811WDEL75A |2TA    |
    When  The user makes a post request to the Configuration-Service
    Then  Should get a 400 response with the Error Message.

  Scenario: Missing Ccode in the request body.
    Given Mathew has a list of vehicles to be Compared:
      |code             |llpCode|
      |                 |2TB    |
      |CUD201811WDEL75A |2TA    |
    And has a stateCode for the Comparison
      |stateCode|
      |LA       |
    When  The user makes a post request to the Configuration-Service
    Then  Should get a 400 response with the Error Message.

  Scenario: Missing Llp in the request body.
    Given Mathew has a list of vehicles to be Compared:
      |code             |llpCode|
      |IUT201813DS6L62B |2TB    |
      |CUD201811WDEL75A |       |
    And has a stateCode for the Comparison
      |stateCode|
      |LA       |
    When  The user makes a post request to the Configuration-Service
    Then  Should get a 400 response with the Error Message.