package cucumberTest;

import drukmakoriSzerelok.Fixer;
import drukmakoriSzerelok.Game;
import drukmakoriSzerelok.Pipe;
import drukmakoriSzerelok.Pump;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import static org.junit.Assert.assertEquals;

class IsItBroken{
    static String isItBroken(boolean status) {
        return status ? "fixed" : "broken";
    }
}

public class FixerFixes {
    private Pipe pipe = new Pipe();
    private Pump pump = new Pump();
    private Fixer fixer = new Fixer("fixer1","");

    private boolean result;

    public FixerFixes(){
        Game.Get().SetActivePlayer(fixer);
    }

    @Given("a broken pipe")
    public void a_broken_pipe() {
        pipe.SetIsBroken(true);
        Game game = Game.Get();
    }

    @Given("a broken pump")
    public void a_broken_pump() {
        pump.SetIsBroken(true);
        Game game = Game.Get();
    }

    @Given("the fixer currently standing on that pipe")
    public void the_fixer_currently_standing_on_that_pipe() {
        fixer.SetField(pipe);
        pipe.AddPlayer(fixer);
    }

    @Given("the fixer currently standing on that pump")
    public void the_fixer_currently_standing_on_that_pump() {
        fixer.SetField(pump);
        pump.AddPlayer(fixer);
    }

    @When("the fixer tries to repair that")
    public void the_fixer_tries_to_repair_that(){
        fixer.Fix(pipe);
        result = ! pipe.GetIsBroken();
    }

    @Then("it should be {string}")
    public void it_should_be(String answer){
        assertEquals(answer, IsItBroken.isItBroken(result));
    }
}
