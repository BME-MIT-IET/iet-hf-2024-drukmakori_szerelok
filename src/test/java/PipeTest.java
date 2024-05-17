import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class PipeTest {
    private Pipe pipe;

    private static Game gameInstance;
    private static GameFrame gameFrameInstance;
    private static Map map;

    private static final MockedStatic<Game> gameClass = mockStatic(Game.class);
    private static final MockedStatic<GameFrame> gameFrameClass = mockStatic(GameFrame.class);
    private static final MockedStatic<Wait> waitClass = mockStatic(Wait.class);

    @BeforeEach
    public void init() {
        pipe = new Pipe();

        gameInstance = mock(Game.class);
        gameFrameInstance = mock(GameFrame.class);
        map = mock(Map.class);

        gameClass.when(Game::Get).thenReturn(gameInstance);
        gameFrameClass.when(GameFrame::Get).thenReturn(gameFrameInstance);
        waitClass.when(Wait::Get).thenReturn(mock(Wait.class));

        when(gameInstance.GetMap()).thenReturn(map);
    }

    @AfterAll
    static void afterAll() {
        gameClass.close();
        gameFrameClass.close();
        waitClass.close();
    }

    @Test
    void referenceID() {
        var referenceID = pipe.GetReferenceID();
        assertFalse(referenceID.isEmpty());

        assertEquals("test", new Pipe("test").GetReferenceID());
    }
}