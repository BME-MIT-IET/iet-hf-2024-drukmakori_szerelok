package cucumberTest;

import drukmakoriSzerelok.*;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import static org.junit.Assert.assertEquals;

public class PlayerMoves{
    Field tank;
    Player player;
    Field neighbour;

    @Given("a default map")
    public void a_default_map() {
        Game.Get();
    }
    @Given("a fixer currently standing on a tank")
    public void a_fixer_currently_standing_on_a_tank() {
        player = Game.Get().GetPlayers().get(0);
        Game.Get().SetActivePlayer(player);
        tank = player.GetField();
    }
    @Given("a neighbour pipe")
    public void a_neighbour_pipe() {
        neighbour = Game.Get().GetMap().GetNeighbourFromDirection(tank, "right");
    }
    @When("the fixer tries to move to the neighbour pipe")
    public void the_fixer_tries_to_move_to_the_neighbour_pipe() {
        player.Move(neighbour);
    }
    @Then("the fixer should be placed on the {string} pipe")
    public void the_fixer_should_be_placed_on_the_neighbour_pipe(String answer) {
        var result = player.GetField() == neighbour ? "neighbour" : "tank";
        assertEquals(answer, result);
    }


    Field field1;
    Field field2;
    Player player1;
    Player player2;
    Field pump;

    @Given("two players currently standing on two separate fields")
    public void two_players_currently_standing_on_two_separate_fields() {
        player1 = Game.Get().GetPlayers().get(0);
        player2 = Game.Get().GetPlayers().get(1);
        field1 = player1.GetField();
        field2 = Game.Get().GetMap().GetNeighbourFromDirection(Game.Get().GetMap().GetNeighbourFromDirection(field1, "right"), "right");
        field2.AddPlayer(player2);
        player2.SetField(field2);
    }
    @Given("a neighbour pump")
    public void a_neighbour_pump() {
        pump = Game.Get().GetMap().GetNeighbourFromDirection(field1, "left");
        pump = new Pump();
    }
    @When("both players try to move to the neighbour pump")
    public void both_players_try_to_move_to_the_neighbour_pump() {
        Game.Get().SetActivePlayer(player1);
        player1.Move(pump);
        Game.Get().SetActivePlayer(player2);
        player2.Move(pump);
    }
    @Then("{string} players should be placed on the pump")
    public void both_players_should_be_placed_on_the_pump(String answer) {
        String result = String.valueOf(pump.GetPlayer().size());
        assertEquals(answer, result);
    }
}