Feature: Exchange rate
  We will have exchange rate between currencies

  Scenario: Query exchange rate and response ok
    Given Exchange Rate API "https://api.ratesapi.io/api"
    When Make a query of exchange rate
    Then Response ok
    
  Scenario: Make queries of exchange with base USD
    Given Exchange Rate API "https://api.ratesapi.io/api" and Base "USD"
    When Make a query of exchange rate
    Then Response have base "USD"

  Scenario: Make queries of exchange for symbols
    Given Exchange Rate API "https://api.ratesapi.io/api" and Symbols "JPY,TRY"
    When Make a query of exchange rate
    Then Response have symbols "JPY,TRY"

  Scenario: Make queries of exchange with incorrect information
    Given Exchange Rate API "https://api.ratesapi.io/api" and Symbols "JPY,TY"
    When Make a query of exchange rate
    Then Response must have invalid

  Scenario: Make queries of exchange in specific date
    Given Exchange Rate API "https://api.ratesapi.io/api" and Symbols "JPY,TRY"
    Given Date is "2021-01-26"
    When Make a query of exchange rate
    Then Response have symbols "JPY,TRY"
