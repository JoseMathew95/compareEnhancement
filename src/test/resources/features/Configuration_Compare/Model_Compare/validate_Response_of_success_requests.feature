Feature: Validate response returned from successful Model Compare Request.

  User submits a post request to the Configuration-compare service with x number of ccode's & Llp's.
  The system verifies validates the request body and retrieves the Compare data for all models in the Request.
  The User then verifies that response is Success, Validates the json Schema of the Response and
  then verifies the data returned in the Response.

  Scenario Outline: User compares the Options received from Configuration-Compare against the Options generated from Catalog-Options and Equipment Category.
    Given Mathew requires the compare Information for the '<ccode>' and '<llpCode>'
    And He has a stateCode for the Request
      | stateCode |
      | LA        |
    When  He Makes a post request to configuration-Compare service with the Models and Ccode
    Then  He should receive a successful response
    And   The response has options that matches the Generated Options.
    Examples:
      | ccode            | llpCode |
      | CUC202005RUCT53A | 2DP     |
      | CUD202011WDDH75A | 2TE     |
      | CUJ201908KLJP74A | 2TG     |


  Scenario Outline: User compares the Options received from Configuration-Compare against the Configure service response.
    Given Mathew requires the compare Information for the '<ccode>' and '<llpCode>'
    And He has a stateCode for the Request
      | stateCode |
      | LA        |
    When  He Makes a post request to configuration-Compare service with the Models and Ccode
    Then  He should receive a successful response
    And   The response has options that matches the Configure Options.
    Examples:
      | ccode            | llpCode |
      | CUJ202008KLTE74A | 2TD     |
      | CUT202020DT6H41A | 2TZ     |
      | CUT201913DS1L41C | 2TB     |


  Scenario Outline: User validates that the VCM-Options section is returned and the DisplayGroup values.
    Given Mathew requires the compare Information for the '<ccode>' and '<llpCode>'
    And He has a stateCode for the Request
      | stateCode |
      | LA        |
    When  He Makes a post request to configuration-Compare service with the Models and Ccode
    Then  He should receive a successful response
    And   The sections has DisplayGroup node and a new VCM-Option Section.
    Examples:
      | ccode            | llpCode |
      | CUJ202008KLJP74A | 2TG     |
      | CUJ202008KLJE74A | 2TD     |
      | CUJ201908KLJP74A | 2TG     |


  Scenario Outline: User validates that the right Views data is returned under Equipment-categories
    Given Mathew requires the compare Information for the '<ccode>' and '<llpCode>'
    And He has a stateCode for the Request
      | stateCode |
      | LA        |
    When  He Makes a post request to configuration-Compare service with the Models and Ccode
    Then  He should receive a successful response
    And   The Equipment-categories node has the correct Views data for the Model
    Examples:
      | ccode            | llpCode |
      | CUJ202008KLJP74A | 2TG     |
      | CUJ202008KLJE74A | 2TD     |
      | CUJ201908KLJP74A | 2TG     |