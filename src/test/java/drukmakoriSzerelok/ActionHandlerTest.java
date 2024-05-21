package drukmakoriSzerelok;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.mockito.Mockito.*;

class ActionHandlerTest {
    private ActionHandler actionHandler;
    private static MockedStatic<Game> gameClass;
    private static MockedStatic<GameFrame> gameFrameClass;

    @BeforeEach
    public void init() {
        this.actionHandler = new ActionHandler();
    }

    @BeforeAll
    static void initOnce() {
        gameClass = Mockito.mockStatic(Game.class);
        gameFrameClass = mockStatic(GameFrame.class);
    }

    @AfterAll
    static void afterAll() {
        gameClass.close();
        gameFrameClass.close();
    }

    @Test
    void PlayerTurn_hasPoints() {
        Player player = Mockito.mock(Player.class);

        when(player.GetActionPoints())
                .thenReturn(2)
                .thenReturn(1)
                .thenReturn(0);
        actionHandler.PlayerTurn(player);

        verify(player, times(2)).InteractOptions();
    }

    @Test
    void PlayerTurn_noPoints() {
        Player player = Mockito.mock(Player.class);

        when(player.GetActionPoints())
                .thenReturn(0);
        actionHandler.PlayerTurn(player);

        verify(player, times(0)).InteractOptions();
    }

    @Test
    void HandleTurn() {
        try {
            Game game = mock(Game.class);
            gameClass.when(Game::Get).thenReturn(game);

            Player player = mock(Player.class);
            ArrayList<Player> players = new ArrayList<>();
            players.add(player);

            when(game.GetPlayers())
                    .thenReturn(players);

            when(game.GetActivePlayer())
                    .thenReturn(player);

            GameFrame gameFrame = mock(GameFrame.class);
            gameFrameClass.when(GameFrame::Get)
                    .thenReturn(gameFrame);

            GameField gameField = mock(GameField.class);
            when(gameFrame.GetPlayerLayer())
                    .thenReturn(gameField);

            when(player.GetActionPoints())
                    .thenReturn(3)
                    .thenReturn(2)
                    .thenReturn(1)
                    .thenReturn(0);

            Field field = mock(Field.class);
            when(player.GetField())
                    .thenReturn(field);

            Map map = mock(Map.class);
            when(game.GetMap())
                    .thenReturn(map);

            Field field2 = mock(Field.class);
            ArrayList<ArrayList<Field>> mapFields1 = new ArrayList<>();
            ArrayList<ArrayList<Field>> mapFields2 = new ArrayList<>();
            ArrayList<Field> mapFields2Inner = new ArrayList<>();
            mapFields2Inner.add(field2);
            mapFields2.add(mapFields2Inner);
            when(map.GetFields())
                    .thenReturn(mapFields1)
                    .thenReturn(mapFields2);

            actionHandler.HandleTurn();

            verify(gameField, times(1)).UpdateField(any());
            verify(game, times(1)).SetActivePlayer(player);
            verify(player, times(1)).SetActionPoints(3);
            verify(gameFrame, times(1)).DrawField(field);
            verify(gameFrame, times(1)).DrawPlayer(player);

            verify(player, times(3)).InteractOptions();

            verify(gameFrame, times(1)).DrawField(field2);

            verify(gameFrame, times(1)).DrawPoints();
        } catch(Exception e) {
            throw e;
        }
    }

    @Test
    void WorldTurn() {
        try {
            Game game = mock(Game.class);
            gameClass.when(Game::Get)
                    .thenReturn(game);

            Map map = mock(Map.class);
            when(game.GetMap())
                    .thenReturn(map);

            Field field1 = mock(Field.class);
            Field field2 = mock(Field.class);

            ArrayList<ArrayList<Field>> fieldsMat = new ArrayList<>();
            ArrayList<Field> fieldsRow = new ArrayList<>();
            fieldsRow.add(field1);
            fieldsRow.add(field2);
            fieldsMat.add(fieldsRow);
            when(map.GetFields())
                    .thenReturn(fieldsMat);

            when(field1.GetPOut())
                    .thenReturn(field2);
            when(field2.GetPIn())
                    .thenReturn(field1);

            when(map.GetDirection(field1, field2))
                    .thenReturn("left");
            when(map.GetDirection(field2, field1))
                    .thenReturn("right");

            when(field1.GetBeenStepped())
                    .thenReturn(false).thenReturn(true);
            when(field2.GetBeenStepped())
                    .thenReturn(false).thenReturn(true);

            GameFrame gameFrame = mock(GameFrame.class);
            gameFrameClass.when(GameFrame::Get).thenReturn(gameFrame);

            actionHandler.WorldTurn();

            verify(field1, times(1)).Step();
            verify(field2, times(1)).Step();

            verify(field1, times(1)).SetBeenStepped(true);
            verify(field2, times(1)).SetBeenStepped(true);

            verify(gameFrame, times(1)).DrawField(field1);
            verify(gameFrame, times(1)).DrawField(field2);
        } catch(Exception e) {
            throw e;
        }
    }
}
