Feature: Book Search Functionality
  As a user
  I want to search for books
  So that I can find books of interest

  Scenario: Search for a book by title
    Given I am on the library homepage
    When I enter "Clean Code" in the search bar
    And I click the search button
    Then I should see book results containing "Clean Code"

  Scenario: Search for a book by author
    Given I am on the library homepage
    When I enter "Robert Martin" in the search bar
    And I click the search button
    Then I should see book results by "Robert Martin"

  Scenario: Search with no results
    Given I am on the library homepage
    When I enter "NonexistentBookTitle123" in the search bar
    And I click the search button
    Then I should see a "No results found" message