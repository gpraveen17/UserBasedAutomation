Feature: User Login Functionality

  Background:
  Close All Process

  @login
  Scenario Outline: <TestCase> Valid login
    Given <User> User is on the login page <TestCase>
    When User Enter valid credentials <TestCase>
    Then Get The all LHN Menu <TestCase>
    Then Navigate to Each Page <TestCase>

    Examples:
      | TestCase | User  |
      | TC001    | User1 |
      | TC002    | User2 |
      | TC003    | User3 |
      | TC004    | User4 |
      | TC005    | User5 |