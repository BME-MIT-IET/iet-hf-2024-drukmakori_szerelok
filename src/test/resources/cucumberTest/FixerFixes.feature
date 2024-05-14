Feature: test fixing the pipe and pump
  Scenario:
    Given a broken pipe
    Given the fixer currently standing on that pipe
    When the fixer try to repair that
    Then it should be "fixed"

