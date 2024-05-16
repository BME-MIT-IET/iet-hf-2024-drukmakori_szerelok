import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class GameTest {
    private Game game;

    @BeforeEach
    void init() {
        this.game = Game.Get();
    }

    @Test
    void ResetPlayers() {
        game.ResetPlayers();

        // Player player1 = Mockito.mock(Player.class);
        // Player player2 = Mockito.mock(Player.class);
        // ArrayList<Player> players = new ArrayList<>();
        // players.add(player1);
        // players.add(player2);

        // this.game = Mockito.spy(Game.class);

        // when(game.getPlayerCount())
        //     .thenReturn(2);

        // game.getPlayerCount();


        // this.game = Game.Get();
        // game.SetPlayers(players);
    }

}