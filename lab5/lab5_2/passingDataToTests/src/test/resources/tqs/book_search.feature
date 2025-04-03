Feature: Book Search
  To allow a customer to find books quickly, the library must offer multiple ways to search for a book.

  Scenario: Search books by author
    Given the following books are available in the library
      | Title               | Author          | Published Date           |
      | One Good Book       | Anonymous       | 2013-03-14T10:00:00      |
      | Some Other Book     | Tim Tomson      | 2014-08-23T10:00:00      |
      | How to Cook a Dino  | Fred Flintstone | 2012-01-01T10:00:00      |
    When the customer searches for books by author "Tim Tomson"
    Then 1 book should be found
    And the book should have the title "Some Other Book"

  Scenario: Search books by date from and to
    Given the following books are available in the library
      | Title               | Author          | Published Date           |
      | One Good Book       | Anonymous       | 2013-03-14T10:00:00      |
      | Some Other Book     | Tim Tomson      | 2014-08-23T10:00:00      |
      | How to Cook a Dino  | Fred Flintstone | 2012-01-01T10:00:00      |
    When the customer searches for books published between 2013-01-01T00:00:00 and 2014-12-31T23:59:59
    Then 2 books should be found

  Scenario: No results when searching for non-existent author
    Given the following books are available in the library
      | Title               | Author          | Published Date           |
      | One Good Book       | Anonymous       | 2013-03-14T10:00:00      |
      | Some Other Book     | Tim Tomson      | 2014-08-23T10:00:00      |
      | How to Cook a Dino  | Fred Flintstone | 2012-01-01T10:00:00      |
    When the customer searches for books by author "Mark Twain"
    Then 0 books should be found

  Scenario: Multiple books by the same author
    Given the following books are available in the library
      | Title               | Author          | Published Date           |
      | One Good Book       | Anonymous       | 2013-03-14T10:00:00      |
      | Anonymous Sequel    | Anonymous       | 2015-06-14T10:00:00      |
      | Some Other Book     | Tim Tomson      | 2014-08-23T10:00:00      |
      | How to Cook a Dino  | Fred Flintstone | 2012-01-01T10:00:00      |
    When the customer searches for books by author "Anonymous"
    Then 2 books should be found