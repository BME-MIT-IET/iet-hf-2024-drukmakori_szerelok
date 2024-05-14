package cucumberTest;

import drukmakoriSzerelok.Saboteur;
import drukmakoriSzerelok.Game;
import drukmakoriSzerelok.Pipe;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import static org.junit.Assert.assertEquals;

class IsPipeStatusCorrect {
    static String isItSlippery(boolean status) {
        return status ? "" : "not ";
    }

    static String isItSticky(boolean status) { return status ? "" : "not "; }
}

public class SaboteurActionsOnPipe {
    private Pipe pipe = new Pipe();
    private Saboteur saboteur = new Saboteur("saboteur1","");

    private boolean result;

    public SaboteurActionsOnPipe(){
        Game.Get().SetActivePlayer(saboteur);
    }

    @Given("a not broken pipe")
    public void a_not_broken_pipe() {
        pipe.SetIsBroken(false);
        Game game = Game.Get();
    }

    @Given("the saboteur currently standing on that pipe")
    public void the_saboteur_currently_standing_on_that_pipe() {
        saboteur.SetField(pipe);
        pipe.AddPlayer(saboteur);
    }

    @When("the saboteur tries to break that")
    public void the_saboteur_tries_to_break_that() {
        saboteur.PipeSabotage(pipe);
        result = ! pipe.GetIsBroken();
    }

    @Given("a pipe")
    public void a_pipe() {
        Game game = Game.Get();
    }

    @When("the saboteur tries to make that slippery")
    public void the_saboteur_tries_to_make_that_slippery() {
        saboteur.MakeSlippery(pipe);
        result = pipe.GetSlippery() > 0;
    }

    @When("the saboteur tries to make that sticky")
    public void the_saboteur_tries_to_make_that_sticky() {
        saboteur.MakeSticky(pipe);
        result = pipe.GetSticky() > 0;
    }

    @Then("the pipe should be {string}slippery")
    public void the_pipe_should_be_slippery(String answer){
        assertEquals(answer, IsPipeStatusCorrect.isItSlippery(result));
    }

    @Then("the pipe should be {string}sticky")
    public void the_pipe_should_be_sticky(String answer){
        assertEquals(answer, IsPipeStatusCorrect.isItSticky(result));
    }
}
