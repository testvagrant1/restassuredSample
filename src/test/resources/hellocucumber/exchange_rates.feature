Feature: Exchange rate
  We will have exchange rate

  Scenario: When we query exchange rate
    Given Exchange Rate API "https://api.ratesapi.io/api/latest"
    When Make a query of exchange rate
    Then Response ok