import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;

class BlankFieldTest {
    private BlankField blankField;
    private static MockedStatic<Game> gameClass;
    private static MockedStatic<GameFrame> gameFrameClass;
    private static MockedStatic<Wait> waitClass;

    @BeforeEach
    public void init() {
        this.blankField = new BlankField();
    }

    @BeforeAll
    static void initOnce() {
        gameClass = Mockito.mockStatic(Game.class);
        gameFrameClass = mockStatic(GameFrame.class);
        waitClass = mockStatic(Wait.class);
    }

    @AfterAll
    static void afterAll() {
        gameClass.close();
        gameFrameClass.close();
        waitClass.close();
    }

    @Test
    void GetReferenceID() {
        this.blankField = new BlankField("refID");

        assertEquals("refID", blankField.GetReferenceID());
    }

    @Test
    void AcceptWater() {
        Field field = mock(Field.class);

        Game game = mock(Game.class);
        gameClass.when(Game::Get).thenReturn(game);

        assertTrue(blankField.AcceptWater(field));

        verify(game).InceraseSaboteurPoints();
    }

    @Test
    void CanAcceptWater() {
        assertEquals(false, blankField.CanAcceptWater());
    }

    @Test 
    void CanAcceptPlayer() {
        assertEquals(false, blankField.CanAcceptPlayer());
    }

    @Test
    void Step() {
        blankField.Step();
    }

    @Test
    void SaboteurOptions() {
        Saboteur saboteur = mock(Saboteur.class);

        Wait wait = mock(Wait.class);
        waitClass.when(Wait::Get)
                .thenReturn(wait);

        blankField.SaboteurOptions(saboteur);

        verify(wait).Wait();
    }

    @Test
    void FixerOptions() {
        Fixer fixer = mock(Fixer.class);

        Wait wait = mock(Wait.class);
        waitClass.when(Wait::Get)
                .thenReturn(wait);

        blankField.FixerOptions(fixer);

        verify(wait).Wait();
    }

}