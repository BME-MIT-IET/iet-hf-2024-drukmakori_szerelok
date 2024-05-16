import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


class TankTest {
    private Tank tank;

    private static Game gameInstance;
    private static GameFrame gameFrameInstance;
    private static Map map;

    private static final MockedStatic<Game> gameClass = mockStatic(Game.class);
    private static final MockedStatic<GameFrame> gameFrameClass = mockStatic(GameFrame.class);
    private static final MockedStatic<Wait> waitClass = mockStatic(Wait.class);

    @BeforeEach
    public void init() {
        tank = new Tank();

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
        var referenceID = tank.GetReferenceID();
        assertFalse(referenceID.isEmpty());

        assertEquals("test", new Tank("test").GetReferenceID());
    }

    @Test
    void numberOfPumps() {
        var numberOfPumps = tank.getNumberOfPumps();
        tank.setNumberOfPumps(numberOfPumps + 10);
        assertEquals(numberOfPumps + 10, tank.getNumberOfPumps());
    }

    @Test
    void numberOfPipes() {
        var numberOfPipes = tank.getNumberOfPipes();
        tank.setNumberOfPipes(numberOfPipes + 10);
        assertEquals(numberOfPipes + 10, tank.getNumberOfPipes());
    }

    @Test
    void generatePipe() {
        var numberOfPipes = tank.getNumberOfPipes();
        int rounds = 100;
        for (int i = 0; i < rounds; i++)
            tank.GeneratePipe();
        assertTrue(tank.getNumberOfPipes() > numberOfPipes);
        assertTrue(tank.getNumberOfPumps() < numberOfPipes + rounds);
    }

    @Test
    void generatePump() {
        var numberOfPumps = tank.getNumberOfPumps();
        int rounds = 100;
        for (int i = 0; i < rounds; i++)
            tank.GeneratePump();
        assertTrue(tank.getNumberOfPumps() > numberOfPumps);
        assertTrue(tank.getNumberOfPipes() < numberOfPumps + rounds);
    }

    @Test
    void decreaseNumberOfPipes() {
        var numberOfPipes = tank.getNumberOfPipes();
        tank.DecreaseNumberOfPipes();
        assertEquals(numberOfPipes - 1, tank.getNumberOfPipes());
    }

    @Test
    void decreaseNumberOfPumps() {
        var numberOfPumps = tank.getNumberOfPumps();
        tank.DecreaseNumberOfPumps();
        assertEquals(numberOfPumps - 1, tank.getNumberOfPumps());
    }

    @Test
    void givePipe() {
        var fixer = mock(Fixer.class);
        var tankSpy = spy(tank);
        tankSpy.GivePipe(fixer);
        verify(tankSpy, times(1)).DecreaseNumberOfPipes();
    }

    @Test
    void givePump() {
        var tankSpy = spy(tank);
        var fixer = mock(Fixer.class);
        tankSpy.GivePump(fixer);
        verify(tankSpy, times(1)).DecreaseNumberOfPumps();
    }

    @Test
    void step() {
        var tankSpy = spy(tank);
        tankSpy.Step();
        verify(tankSpy, times(1)).GeneratePipe();
        verify(tankSpy, times(1)).GeneratePump();
    }

    @Test
    void acceptWater() {
        tank.AcceptWater(mock(Field.class));
        verify(gameInstance, times(1)).InceraseFixerPoints();
    }

    @Test
    void getStatus() {
        var fixer = mock(Fixer.class);
        var saboteur = mock(Saboteur.class);
        when(fixer.GetStatus()).thenReturn(new ArrayList<>(List.of("", "fixer")));
        when(saboteur.GetStatus()).thenReturn(new ArrayList<>(List.of("", "saboteur")));
        tank.SetPlayer(List.of(fixer, fixer, saboteur));

        var status = tank.GetStatus();

        assertEquals("Tank", status.get(0));
        assertTrue(Integer.parseInt(status.get(1)) >= 0);
        assertTrue(Integer.parseInt(status.get(2)) >= 0);
        assertEquals(2, Integer.parseInt(status.get(3)));
        assertEquals(1, Integer.parseInt(status.get(4)));
        assertEquals(tank.getNumberOfPipes(), Integer.parseInt(status.get(5)));
        assertEquals(tank.getNumberOfPumps(), Integer.parseInt(status.get(6)));
    }

    @Test
    void saboteurOptions_pass() {
        var tankSpy = spy(tank);
        var saboteur = spy(new Saboteur("", ""));
        var field = mock(Field.class);

        when(gameInstance.GetMap()).thenReturn(map);
        when(map.GetNeighbours(any())).thenReturn(List.of(field));
        when(tankSpy.GetPerformAction()).thenReturn("pass");

        var canMoveOptions = new boolean[]{true, false};
        for (var canMove : canMoveOptions) {
            when(field.CanAcceptPlayer()).thenReturn(canMove);
            tankSpy.SaboteurOptions(saboteur);
            assertEquals(canMove, saboteur.GetOptions().contains("move"));
        }

        verify(gameFrameInstance, times(canMoveOptions.length)).EnableActions(any());
        assertEquals(0, saboteur.GetActionPoints());
    }

    @Test
    void saboteurOptions_move() {
        var tankSpy = spy(tank);
        var saboteur = spy(new Saboteur("", ""));
        var endField = mock(Field.class);
        var startField = mock(Field.class);

        saboteur.SetField(startField);
        when(map.GetNeighbours(any())).thenReturn(List.of(endField));
        when(tankSpy.GetPerformAction()).thenReturn("move");
        when(endField.CanAcceptPlayer()).thenReturn(true);
        when(map.GetDirection(any(), any())).thenReturn("direction");
        when(map.GetNeighbourFromDirection(any(), any())).thenReturn(endField);

        tankSpy.SaboteurOptions(saboteur);

        verify(gameFrameInstance, times(2)).EnableActions(any());
        verify(startField, times(1)).RemovePlayer(saboteur);
        verify(endField, times(1)).AcceptPlayer();
    }

    @Test
    void fixerOptions_pass() {
        var tankSpy = spy(tank);
        var fixer = spy(new Fixer("", ""));
        var field = mock(Field.class);
        tankSpy.setNumberOfPumps(1);
        tankSpy.setNumberOfPipes(1);

        when(map.GetNeighbours(any())).thenReturn(List.of(field));
        when(tankSpy.GetPerformAction()).thenReturn("pass");
        when(field.CanAcceptPlayer()).thenReturn(false);
        when(field.GetRemovable()).thenReturn(true);
        when(field.GetReplacable()).thenReturn(true);

        var hasActiveOptions = new boolean[]{true, false};
        for (var hasActive : hasActiveOptions) {
            fixer.SetHasActive(hasActive);

            tankSpy.FixerOptions(fixer);

            var fixerOptions = fixer.GetOptions();
            assertFalse(fixerOptions.contains("move"));
            assertEquals(hasActive, fixerOptions.contains("place active"));
            assertEquals(!hasActive, fixerOptions.contains("remove pipe"));
            assertEquals(!hasActive, fixerOptions.contains("carry pump"));
            assertEquals(!hasActive, fixerOptions.contains("carry pipe"));
        }

        verify(gameFrameInstance, times(hasActiveOptions.length)).EnableActions(any());
        assertEquals(0, fixer.GetActionPoints());

        when(field.GetReplacable()).thenReturn(false);
        fixer.SetHasActive(true);
        tankSpy.FixerOptions(fixer);
        assertFalse(fixer.GetOptions().contains("place active"));

        tankSpy.setNumberOfPumps(0);
        tankSpy.setNumberOfPipes(0);
        fixer.SetHasActive(false);
        tankSpy.FixerOptions(fixer);
        assertFalse(fixer.GetOptions().contains("carry pump"));
        assertFalse(fixer.GetOptions().contains("carry pipe"));
    }

    @Test
    void fixerOptions_move() {
        var tankSpy = spy(tank);
        var fixer = spy(new Fixer("", ""));
        var endField = mock(Field.class);
        var startField = mock(Field.class);

        fixer.SetField(startField);
        when(map.GetNeighbours(any())).thenReturn(List.of(endField));
        when(tankSpy.GetPerformAction()).thenReturn("move");
        when(endField.CanAcceptPlayer()).thenReturn(true);
        when(endField.GetRemovable()).thenReturn(false);
        when(endField.GetReplacable()).thenReturn(false);
        when(map.GetDirection(any(), any())).thenReturn("direction");
        when(map.GetNeighbourFromDirection(any(), any())).thenReturn(endField);

        tankSpy.FixerOptions(fixer);

        verify(gameFrameInstance, times(2)).EnableActions(any());
        verify(startField, times(1)).RemovePlayer(fixer);
        verify(endField, times(1)).AcceptPlayer();
    }

    @Test
    void fixerOptions_removePipe() {
        var tankSpy = spy(tank);
        var fixer = mock(Fixer.class);
        var field = mock(Pipe.class);

        when(map.GetNeighbours(any())).thenReturn(List.of(field));
        when(tankSpy.GetPerformAction()).thenReturn("remove pipe");
        when(field.CanAcceptPlayer()).thenReturn(true);
        when(field.GetRemovable()).thenReturn(true);
        when(field.GetReplacable()).thenReturn(false);
        when(map.GetDirection(any(), any())).thenReturn("direction");
        when(map.GetNeighbourFromDirection(any(), any())).thenReturn(field);

        tankSpy.FixerOptions(fixer);

        verify(gameFrameInstance, times(2)).EnableActions(any());
        verify(fixer, times(1)).RemoveActivePipe(field);
    }

    @Test
    void fixerOptions_placeActive() {
        var tankSpy = spy(tank);
        var fixer = mock(Fixer.class);
        var field = mock(Pipe.class);

        when(map.GetNeighbours(any())).thenReturn(List.of(field));
        when(tankSpy.GetPerformAction()).thenReturn("place active");
        when(field.CanAcceptPlayer()).thenReturn(true);
        when(field.GetRemovable()).thenReturn(false);
        when(field.GetReplacable()).thenReturn(true);
        when(map.GetDirection(any(), any())).thenReturn("direction");
        when(map.GetNeighbourFromDirection(any(), any())).thenReturn(field);

        tankSpy.FixerOptions(fixer);

        verify(gameFrameInstance, times(2)).EnableActions(any());
        verify(fixer, times(1)).Place(field);
    }

    @Test
    void fixerOptions_carryPump() {
        var tankSpy = spy(tank);
        var fixer = spy(new Fixer("", ""));
        var field = mock(Pump.class);
        tankSpy.setNumberOfPumps(1);

        when(map.GetNeighbours(any())).thenReturn(List.of(field));
        when(tankSpy.GetPerformAction()).thenReturn("carry pump");
        when(field.CanAcceptPlayer()).thenReturn(true);
        when(field.GetRemovable()).thenReturn(false);
        when(field.GetReplacable()).thenReturn(false);
        when(map.GetDirection(any(), any())).thenReturn("direction");
        when(map.GetNeighbourFromDirection(any(), any())).thenReturn(field);

        tankSpy.FixerOptions(fixer);

        verify(gameFrameInstance, times(1)).EnableActions(any());
        verify(fixer, times(1)).CarryPump(tankSpy);
    }

    @Test
    void fixerOptions_carryPipe() {
        var tankSpy = spy(tank);
        var fixer = spy(new Fixer("", ""));
        var field = mock(Pipe.class);
        tankSpy.setNumberOfPipes(1);

        when(map.GetNeighbours(any())).thenReturn(List.of(field));
        when(tankSpy.GetPerformAction()).thenReturn("carry pipe");
        when(field.CanAcceptPlayer()).thenReturn(true);
        when(field.GetRemovable()).thenReturn(false);
        when(field.GetReplacable()).thenReturn(false);
        when(map.GetDirection(any(), any())).thenReturn("direction");
        when(map.GetNeighbourFromDirection(any(), any())).thenReturn(field);

        tankSpy.FixerOptions(fixer);

        verify(gameFrameInstance, times(1)).EnableActions(any());
        verify(fixer, times(1)).CarryPipe(tankSpy);
    }
}