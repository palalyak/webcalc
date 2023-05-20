@All
@TestNG
@severity=blocker
Feature: Cucumber UI/API tests for web calculator

  @Smoke @qa-ready @Epic-900
  Scenario: post api request
    When user sends an api request with a function
    Then api response contains the correct calculation

  @Regression @qa-ready @Epic-900
  Scenario: User calculates 10-8=2 by pressing calculator buttons
    Given user navigate to WebCalculator site
    Given calculator is opened
    When user clicks the following buttons
      | ONE | ZERO | MINUS | EIGHT |
    And user submit calculation by clicking "EQUALS"
    Then result of calculation should be "2"
    Then history of calculation contains formula "10-8" and result "2"

  @Regression @qa-ready @Epic-900
  Scenario: User calculates 2+3=5 by pressing calculator buttons
    Given user navigate to WebCalculator site
    Given calculator is opened
    When user clicks the following buttons
      | TWO | PLUS | THREE |
    And user submit calculation by clicking "EQUALS"
    Then result of calculation should be "5"
    Then history of calculation contains formula "2+3" and result "5"

  @Regression @qa-ready @Epic-900
  Scenario: User calculates sin(30)=0.5 by pressing calculator buttons
    Given user navigate to WebCalculator site
    Given calculator is opened
    When user clicks the following buttons
      | THREE | ZERO | SIN |
    And user submit calculation by clicking "EQUALS"
    Then result of calculation should be "0.5"
    Then history of calculation contains formula "sin(30)" and result "0.5"

  @Regression @qa-ready @Epic-900
  Scenario: User calculates (10-2)*2 !=20 by pressing calculator buttons
    Given user navigate to WebCalculator site
    Given calculator is opened
    When user clicks the following buttons
      | PARAN_L | ONE | ZERO | MINUS | TWO | PARAN_R | MULT | TWO |
    And user submit calculation by clicking "EQUALS"
    Then result of calculation is not should be "20"
    Then history of calculation contains formula "(10-2)*2" and result "16"
