Feature:
  Scenario: test fixing a pipe
    Given a broken pipe
    Given the fixer currently standing on that pipe
    When the fixer tries to repair that
    Then it should be "fixed"

  Scenario: test fixing a pump
    Given a broken pump
    Given the fixer currently standing on that pump
    When the fixer tries to repair that
    Then it should be "fixed"