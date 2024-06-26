package drukmakoriSzerelok;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

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
        // The number of generated pipes should be at more than zero and less than the number of rounds
        assertTrue(tank.getNumberOfPipes() > numberOfPipes);
        assertTrue(tank.getNumberOfPumps() < numberOfPipes + rounds);
    }

    @Test
    void generatePump() {
        var numberOfPumps = tank.getNumberOfPumps();
        int rounds = 100;
        for (int i = 0; i < rounds; i++)
            tank.GeneratePump();
        // The number of generated pumps should be at more than zero and less than the number of rounds
        assertTrue(tank.getNumberOfPumps() > numberOfPumps);
        assertTrue(tank.getNumberOfPipes() < numberOfPumps + rounds);
    }

    @Test
    void decreaseNumberOfPipes() {
        var initialNumberOfPipes = tank.getNumberOfPipes();
        tank.DecreaseNumberOfPipes();
        assertEquals(initialNumberOfPipes - 1, tank.getNumberOfPipes());
    }

    @Test
    void decreaseNumberOfPumps() {
        var initialNumberOfPipes = tank.getNumberOfPumps();
        tank.DecreaseNumberOfPumps();
        assertEquals(initialNumberOfPipes - 1, tank.getNumberOfPumps());
    }

    @Test
    void givePipe() {
        var fixer = mock(Fixer.class);
        var tankSpy = spy(tank);
        tankSpy.GivePipe(fixer);
        verify(tankSpy).DecreaseNumberOfPipes();
    }

    @Test
    void givePump() {
        var tankSpy = spy(tank);
        var fixer = mock(Fixer.class);
        tankSpy.GivePump(fixer);
        verify(tankSpy).DecreaseNumberOfPumps();
    }

    @Test
    void step() {
        var tankSpy = spy(tank);
        tankSpy.Step();
        verify(tankSpy).GeneratePipe();
        verify(tankSpy).GeneratePump();
    }

    @Test
    void acceptWater() {
        tank.AcceptWater(mock(Field.class));
        verify(gameInstance).InceraseFixerPoints();
    }

    @Test
    void getStatus() {
        var random = new Random();

        var xIndex = random.nextInt(10);
        var yIndex = random.nextInt(10);
        var fixerCount = random.nextInt(10) + 1;
        var saboteurCount = random.nextInt(10) + 1;
        var numberOfPipes = random.nextInt(10);
        var numberOfPumps = random.nextInt(10);

        var fixer = mock(Fixer.class);
        var saboteur = mock(Saboteur.class);
        when(map.GetXIndex(tank)).thenReturn(xIndex);
        when(map.GetYIndex(tank)).thenReturn(yIndex);
        when(fixer.GetStatus()).thenReturn(new ArrayList<>(List.of("", "fixer")));
        when(saboteur.GetStatus()).thenReturn(new ArrayList<>(List.of("", "saboteur")));
        var players = new ArrayList<Player>();
        for (int i = 0; i < fixerCount; i++) players.add(fixer);
        for (int i = 0; i < saboteurCount; i++) players.add(saboteur);
        tank.SetPlayer(players);
        tank.setNumberOfPipes(numberOfPipes);
        tank.setNumberOfPumps(numberOfPumps);

        var status = tank.GetStatus();

        assertEquals(7, status.size());
        assertEquals("Tank", status.get(0));
        assertEquals(xIndex, Integer.parseInt(status.get(1)));
        assertEquals(yIndex, Integer.parseInt(status.get(2)));
        assertEquals(fixerCount, Integer.parseInt(status.get(3)));
        assertEquals(saboteurCount, Integer.parseInt(status.get(4)));
        assertEquals(numberOfPipes, Integer.parseInt(status.get(5)));
        assertEquals(numberOfPumps, Integer.parseInt(status.get(6)));
    }

    @Test
    void saboteurOptions_pass() {
        var tankSpy = spy(tank);
        var saboteur = new Saboteur("", "");
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

        verify(saboteur).AddOption("move");
        verify(gameFrameInstance, times(2)).EnableActions(any());
        verify(startField).RemovePlayer(saboteur);
        verify(endField).AcceptPlayer();
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

        clearInvocations(fixer);

        // Nowhere to place active
        when(field.GetReplacable()).thenReturn(false);
        fixer.SetHasActive(true);
        tankSpy.FixerOptions(fixer);
        verify(fixer, never()).AddOption("place active");

        // Nothing to carry
        tankSpy.setNumberOfPumps(0);
        tankSpy.setNumberOfPipes(0);
        fixer.SetHasActive(false);
        tankSpy.FixerOptions(fixer);
        verify(fixer, never()).AddOption("carry pump");
        verify(fixer, never()).AddOption("carry pipe");
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

        verify(fixer).AddOption("move");
        verify(gameFrameInstance, times(2)).EnableActions(any());
        verify(startField).RemovePlayer(fixer);
        verify(endField).AcceptPlayer();
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

        verify(fixer).AddOption("remove pipe");
        verify(gameFrameInstance, times(2)).EnableActions(any());
        verify(fixer).RemoveActivePipe(field);
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
        when(fixer.GetHasActive()).thenReturn(true);

        tankSpy.FixerOptions(fixer);

        verify(fixer).AddOption("place active");
        verify(gameFrameInstance, times(2)).EnableActions(any());
        verify(fixer).Place(field);
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

        verify(fixer).AddOption("carry pump");
        verify(gameFrameInstance).EnableActions(any());
        verify(fixer).CarryPump(tankSpy);
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

        verify(fixer).AddOption("carry pipe");
        verify(gameFrameInstance).EnableActions(any());
        verify(fixer).CarryPipe(tankSpy);
    }
}