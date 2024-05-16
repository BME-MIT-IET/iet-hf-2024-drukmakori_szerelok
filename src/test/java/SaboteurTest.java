import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;

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
        verify(saboteurSpy, times(1)).DecreaseActionPoints();
    }

    @Test
    void getStatus() {
        var status = saboteur.GetStatus();
        assertEquals(5, status.size());
        assertEquals(name, status.get(0));
        assertEquals("saboteur", status.get(1));
        assertTrue(Integer.parseInt(status.get(2)) >= 0);
        assertTrue(Integer.parseInt(status.get(3)) >= 0);
        assertEquals(String.valueOf(saboteur.GetActionPoints()), status.get(4));
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
        verify(gameFrame, times(1)).DrawField(field);
        verify(gameFrame, times(1)).DrawPlayer(saboteur);
    }
}