Feature: Exchange rate
  We will have exchange rate between currencies

  Scenario: Query exchange rate and response ok
    Given exchange rate API is "https://api.ratesapi.io/api"
    When make a query of exchange rate
    Then response ok
    
  Scenario: Make queries of exchange with base USD
    Given exchange rate API is "https://api.ratesapi.io/api" and Base "USD"
    When make a query of exchange rate
    Then response have base "USD"

  Scenario: Make queries of exchange for symbols
    Given exchange rate API is "https://api.ratesapi.io/api" and Symbols "JPY,TRY"
    When make a query of exchange rate
    Then response have symbols "JPY,TRY"

  Scenario: Make queries of exchange with incorrect information
    Given exchange rate API is "https://api.ratesapi.io/api" and Symbols "JPY,TY"
    When make a query of exchange rate
    Then response must have invalid

  Scenario: Make queries of exchange in specific date
    Given exchange rate API is "https://api.ratesapi.io/api" and Symbols "JPY,TRY"
    Given date is "2021-01-26"
    When make a query of exchange rate
    Then response have base "EUR" and date "2021-01-26"
