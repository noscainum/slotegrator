Feature: Admin dashboard UI tests

  Scenario: User successfully logged in
    Given The browser is opened
    When Login page opening
    And Admin page authorization
    Then User successfully logged in
    And Browser closing

  Scenario: Players table opened
    Given The browser is opened
    When Login page opening
    And Admin page authorization
    And Players table loading
    Then Players table loaded
    And Browser closing

  Scenario: The table is correctly sorted by the selected column
    Given The browser is opened
    When Login page opening
    And Admin page authorization
    And Players table loading
    And Table sorting
    Then Table is sorted correctly
    And Browser closing