Feature:
  Scenario: a player moves to another pipe
    Given a default map
    And a fixer currently standing on a tank
    And a neighbour pipe
    When the fixer tries to move to the neighbour pipe
    Then the fixer should be placed on the "neighbour" pipe

  Scenario: two players move to the same pump
    Given a default map
    And two players currently standing on two separate fields
    And a neighbour pump
    When both players try to move to the neighbour pump
    Then "2" players should be placed on the pump
