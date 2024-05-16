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

    @BeforeEach
    public void init() {
        this.blankField = new BlankField();
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
    void CanAcceptPlayer() {
        assertEquals(false, blankField.CanAcceptPlayer());
    }

    
}