package cucumberTest;

import drukmakoriSzerelok.Fixer;
import drukmakoriSzerelok.Game;
import drukmakoriSzerelok.Pipe;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import static org.junit.Assert.assertEquals;


class IsItWorks{
    static String isItWorks(boolean status) {
        return status ? "fixed" : "not fixed";
    }
}

public class FixerFixes {
    private Pipe pipe = new Pipe();
    private Fixer fixer = new Fixer("fixer1","");

    private boolean result;

    @Given("a broken pipe")
    public void a_broken_pipe() {
        pipe.SetIsBroken(true);
        Game game = Game.Get();

    }

    @Given("the fixer currently standing on that pipe")
    public void the_fixer_currently_standing_on_that_pipe() {
        fixer.SetField(pipe);
        pipe.AddPlayer(fixer);
    }


    @When("the fixer try to repair that")
    public void the_fixer_try_to_repair_that(){
        fixer.Fix(pipe);
        result = ! pipe.GetIsBroken();
    }

    @Then("it should be {string}")
    public void it_should_be(String answer){
        assertEquals(answer, IsItWorks.isItWorks(result));
    }

}
