import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;

import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class SaboteurTest {
    private Saboteur saboteur;
    private final String name = "Test Saboteur";

    @BeforeEach
    public void init() {
        this.saboteur = new Saboteur(name, "");
    }

    @Test
    void makeSlippery() {
        var saboteurSpy = spy(saboteur);
        var pipe = spy(Pipe.class);
        assertFalse(pipe.GetSlippery() > 0);
        saboteurSpy.MakeSlippery(pipe);
        assertTrue(pipe.GetSlippery() > 0);
        verify(saboteurSpy).DecreaseActionPoints();
    }

    @Test
    void getStatus() {
        var random = new Random();

        var xIndex = random.nextInt(10);
        var yIndex = random.nextInt(10);
        var actionPoints = random.nextInt(10);

        var field = mock(Field.class);
        var map = mock(Map.class);
        var gameInstance = mock(Game.class);
        saboteur.SetField(field);
        saboteur.SetActionPoints(actionPoints);
        when(map.GetXIndex(field)).thenReturn(xIndex);
        when(map.GetYIndex(field)).thenReturn(yIndex);
        when(gameInstance.GetMap()).thenReturn(map);

        try (MockedStatic<Game> gameClass = mockStatic(Game.class)) {
            gameClass.when(GameFrame::Get).thenReturn(gameInstance);

            var status = saboteur.GetStatus();

            assertEquals(5, status.size());
            assertEquals(name, status.get(0));
            assertEquals("saboteur", status.get(1));
            assertEquals(xIndex, Integer.parseInt(status.get(2)));
            assertEquals(yIndex, Integer.parseInt(status.get(3)));
            assertEquals(actionPoints, Integer.parseInt(status.get(4)));
        }
    }

    @Test
    void interactOptions() {
        var field = mock(Field.class);
        var gameFrame = mock(GameFrame.class);
        saboteur.SetField(field);
        try (MockedStatic<GameFrame> gameFrameStatic = mockStatic(GameFrame.class)) {
            gameFrameStatic.when(GameFrame::Get).thenReturn(gameFrame);
            saboteur.InteractOptions();
        }
        verify(gameFrame).DrawField(field);
        verify(gameFrame).DrawPlayer(saboteur);
    }
}