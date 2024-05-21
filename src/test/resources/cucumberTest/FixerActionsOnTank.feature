Feature:
  Scenario: test carrying pipe
    Given a tank
    Given the fixer currently standing on that tank
    When the saboteur tries to carry a pipe
    Then the tank should have "4" pipes
    Then the fixer should ""have an active field

  Scenario: test carrying pump
    Given a tank
    Given the fixer currently standing on that tank
    When the saboteur tries to carry a pump
    Then the tank should have "0" pumps
    Then the fixer should ""have an active field