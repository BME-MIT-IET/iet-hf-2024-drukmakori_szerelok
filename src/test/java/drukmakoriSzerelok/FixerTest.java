package drukmakoriSzerelok;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.Mockito;

import java.util.ArrayList;

class FixerTest {
    private Fixer fixer;
    private static MockedStatic<Game> gameClass;
    private static MockedStatic<GameFrame> gameFrameClass;
    private static MockedStatic<Wait> waitClass;

    @BeforeEach
    void init() {
        fixer = new Fixer("fixer", "picture");
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
    void Fix() {
        Active active = mock(Active.class);

        fixer.Fix(active);

        verify(active, times(1)).Fix();
    }

    @Test
    void RemoveActivePipe() {
        Pipe pipe = mock(Pipe.class);

        fixer.RemoveActivePipe(pipe);

        verify(pipe, times(1)).Remove(fixer);
    }

    @Test
    void CarryPump() {
        Tank tank = mock(Tank.class);

        fixer.CarryPump(tank);

        verify(tank, times(1)).GivePump(fixer);
    }

    @Test
    void CarryPipe() {
        Tank tank = mock(Tank.class);

        fixer.CarryPipe(tank);

        verify(tank, times(1)).GivePipe(fixer);
    }

    @Test
    void ActiveGetSet() {
        Active active = mock(Active.class);

        fixer.SetActive(active);

        assertEquals(active, fixer.GetActive());
    }

    @Test
    void HasActiveGetSet() {
        boolean hasActive = true;

        fixer.SetHasActive(hasActive);

        assertEquals(hasActive, fixer.GetHasActive());
    }

    @Test
    void Place() {
        Field field1 = mock(Field.class);
        Field field2 = mock(Field.class);
        fixer.SetField(field1);

        when(field1.GetPIn())
                .thenReturn(field2);

        Active active = mock(Active.class);
        fixer.SetActive(active);

        Game game = mock(Game.class);
        gameClass.when(Game::Get)
                        .thenReturn(game);

        Map map = mock(Map.class);
        when(game.GetMap())
                .thenReturn(map);

        when(map.GetDirection(field2, field1))
                .thenReturn("right");

        when(map.GetNeighbourFromDirection(field2, "right"))
                .thenReturn(field1);

        fixer.Place(field2);

        verify(active).SetPOut(field1);
        verify(field1).SetPIn(active);
    }

    @Test
    void InteractOptions() {
        Field field = mock(Field.class);
        fixer.SetField(field);

        Game game = mock(Game.class);
        gameClass.when(Game::Get).thenReturn(game);

        Map map = mock(Map.class);
        when(game.GetMap()).thenReturn(map);

        Field field2 = mock(Field.class);
        ArrayList<Field> fields = new ArrayList<>();
        fields.add(field2);
        when(map.GetNeighbours(field))
                .thenReturn(fields);

        GameFrame gameFrame = mock(GameFrame.class);
        gameFrameClass.when(GameFrame::Get).thenReturn(gameFrame);

        when(map.GetNeighbours(field2))
                .thenReturn(new ArrayList<>());

        fixer.InteractOptions();

        verify(field).FixerOptions(fixer);

        verify(gameFrame).DrawField(field2);
    }

}