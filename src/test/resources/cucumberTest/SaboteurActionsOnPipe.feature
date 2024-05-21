Feature:
  Scenario: test breaking a pipe
    Given a not broken pipe
    Given the saboteur currently standing on that pipe
    When the saboteur tries to break that
    Then it should be "broken"

  Scenario: test making a pipe slippery
    Given a pipe
    Given the saboteur currently standing on that pipe
    When the saboteur tries to make that slippery
    Then the pipe should be ""slippery

  Scenario: test making a pipe sticky
    Given a pipe
    Given the saboteur currently standing on that pipe
    When the saboteur tries to make that sticky
    Then the pipe should be ""sticky