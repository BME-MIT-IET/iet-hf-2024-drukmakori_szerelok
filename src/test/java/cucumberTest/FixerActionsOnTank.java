package cucumberTest;

import drukmakoriSzerelok.Game;
import drukmakoriSzerelok.Tank;
import drukmakoriSzerelok.Fixer;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import static org.junit.Assert.assertEquals;

public class FixerActionsOnTank {
    private Tank tank = new Tank();
    private Fixer fixer = new Fixer("fixer1","");

    public FixerActionsOnTank(){
        Game.Get().SetActivePlayer(fixer);
    }

    @Given("a tank")
    public void a_tank() {
        Game game = Game.Get();
    }

    @Given("the fixer currently standing on that tank")
    public void the_fixer_currently_standing_on_that_tank() {
        fixer.SetField(tank);
        tank.AddPlayer(fixer);
    }

    @When("the saboteur tries to carry a pipe")
    public void the_saboteur_tries_to_carry_a_pipe() {
        fixer.CarryPipe(tank);
    }

    @When("the saboteur tries to carry a pump")
    public void the_saboteur_tries_to_carry_a_pump() {
        fixer.CarryPump(tank);
    }

    @Then("the tank should have {string} pipes")
    public void the_tank_should_have_pipes(String answer){
        assertEquals(answer, String.valueOf(tank.getNumberOfPipes()));
    }

    @Then("the tank should have {string} pumps")
    public void the_tank_should_have_pumps(String answer){
        assertEquals(answer, String.valueOf(tank.getNumberOfPumps()));
    }

    @Then("the fixer should {string}have an active field")
    public void the_fixer_should_have_an_active_field(String answer){
        var result = fixer.GetHasActive() ? "" : "not ";
        assertEquals(answer, result);
    }
}
