import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class PumpTest {
    private Pump pump;

    private static Game gameInstance;
    private static GameFrame gameFrameInstance;
    private static Map map;

    private static final MockedStatic<Game> gameClass = mockStatic(Game.class);
    private static final MockedStatic<GameFrame> gameFrameClass = mockStatic(GameFrame.class);
    private static final MockedStatic<Wait> waitClass = mockStatic(Wait.class);

    @BeforeEach
    public void init() {
        pump = new Pump();

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
        var referenceID = pump.GetReferenceID();
        assertFalse(referenceID.isEmpty());

        assertEquals("test", new Pump("test").GetReferenceID());
    }

    @Test
    void storedWater() {
        var initialStoredWater = pump.GetStoredWater();
        pump.SetStoredWater(initialStoredWater + 2);
        assertEquals(initialStoredWater + 2, pump.GetStoredWater());
        pump.IncreaseStoredWater();
        assertEquals(initialStoredWater + 3, pump.GetStoredWater());
        pump.DecreaseStoredWater();
        assertEquals(initialStoredWater + 2, pump.GetStoredWater());
    }

    @Test
    void acceptWater() {
        var pumpSpy = spy(pump);
        var field = mock(Field.class);

        // Field not pIn
        pumpSpy.SetPIn(null);
        assertFalse(pumpSpy.AcceptWater(field));
        verify(pumpSpy, never()).IncreaseStoredWater();

        // Field is pIn, but storedWater is full
        pumpSpy.SetPIn(field);
        pumpSpy.SetStoredWater(5);
        assertFalse(pumpSpy.AcceptWater(field));
        verify(pumpSpy, never()).IncreaseStoredWater();

        // Field is pIn, storedWater is not full, beenStepped is false
        pumpSpy.SetStoredWater(0);
        pumpSpy.SetBeenStepped(false);
        assertTrue(pumpSpy.AcceptWater(field));
        verify(pumpSpy).IncreaseStoredWater();

        clearInvocations(pumpSpy);

        // Field is pIn, storedWater is not full, beenStepped is true
        pumpSpy.SetStoredWater(0);
        pumpSpy.SetBeenStepped(true);
        assertTrue(pumpSpy.AcceptWater(field));
        verify(pumpSpy).IncreaseStoredWater();
    }

    @Test
    void malfunction() {
        for (var i = 0; i < 250; i++)
            pump.Malfunction();
        assertTrue(pump.GetIsBroken());
    }

    @Test
    void step() {
        var pumpSpy = spy(pump);
        pumpSpy.SetStoredWater(1);
        pumpSpy.Step(); // Set hasWaterBeforeStep to true
        pumpSpy.Step(); // ForwardWater() is called
        verify(pumpSpy).ForwardWater();
        verify(pumpSpy, times(2)).Malfunction();

        pumpSpy = spy(pump);
        pumpSpy.SetStoredWater(0);
        pumpSpy.Step(); // Nothing happens
        verify(pumpSpy, never()).ForwardWater();
        verify(pumpSpy).Malfunction();
    }

    @Test
    void setDirection() {
        // TODO: Remove the SetDirection method because it does nothing, just like the super class's method
        //  The method's documentation is also incorrect, and it's missing the @Override annotation
        var field = mock(Field.class);
        assertDoesNotThrow(() -> pump.SetDirection(false, field));
        assertDoesNotThrow(() -> pump.SetDirection(true, field));
    }

    @Test
    void getStatus() {
        var random = new Random();

        var xIndex = random.nextInt(10);
        var yIndex = random.nextInt(10);
        var fixerCount = random.nextInt(10) + 1;
        var saboteurCount = random.nextInt(10) + 1;
        var isBroken = random.nextBoolean();
        var storedWater = random.nextInt(10);
        var pInDirection = "pIn direction";
        var pOutDirection = "pOut direction";

        var fixer = mock(Fixer.class);
        var saboteur = mock(Saboteur.class);
        var pIn = mock(Field.class);
        var pOut = mock(Field.class);
        when(map.GetXIndex(pump)).thenReturn(xIndex);
        when(map.GetYIndex(pump)).thenReturn(yIndex);
        when(map.GetDirection(pump, pIn)).thenReturn(pInDirection);
        when(map.GetDirection(pump, pOut)).thenReturn(pOutDirection);
        when(fixer.GetStatus()).thenReturn(new ArrayList<>(List.of("", "fixer")));
        when(saboteur.GetStatus()).thenReturn(new ArrayList<>(List.of("", "saboteur")));
        var players = new ArrayList<Player>();
        for (int i = 0; i < fixerCount; i++) players.add(fixer);
        for (int i = 0; i < saboteurCount; i++) players.add(saboteur);
        pump.SetPlayer(players);
        pump.SetPIn(pIn);
        pump.SetPOut(pOut);
        pump.SetIsBroken(isBroken);
        pump.SetStoredWater(storedWater);

        var status = pump.GetStatus();

        assertEquals(9, status.size());
        assertEquals("Pump", status.get(0));
        assertEquals(xIndex, Integer.parseInt(status.get(1)));
        assertEquals(yIndex, Integer.parseInt(status.get(2)));
        assertEquals(fixerCount, Integer.parseInt(status.get(3)));
        assertEquals(saboteurCount, Integer.parseInt(status.get(4)));
        assertEquals(isBroken, Boolean.parseBoolean(status.get(5)));
        assertEquals(storedWater, Integer.parseInt(status.get(6)));
        assertEquals(pInDirection, status.get(7));
        assertEquals(pOutDirection, status.get(8));
    }

    @Test
    void forwardWater() {
        // No water
        var pumpSpy = spy(pump);
        pumpSpy.SetStoredWater(0);
        pumpSpy.ForwardWater();
        verify(pumpSpy, never()).DecreaseStoredWater();

        // Broken
        pumpSpy.SetStoredWater(10);
        pumpSpy.SetIsBroken(true);
        pumpSpy.ForwardWater();
        verify(pumpSpy, never()).DecreaseStoredWater();

        // No out
        pumpSpy.SetIsBroken(false);
        pumpSpy.SetPOut(null);
        pumpSpy.ForwardWater();
        verify(pumpSpy, never()).DecreaseStoredWater();

        // Out cannot accept water
        var field = mock(Field.class);
        when(field.CanAcceptWater()).thenReturn(false);
        pumpSpy.SetPOut(field);
        pumpSpy.ForwardWater();
        verify(pumpSpy, never()).DecreaseStoredWater();

        // Out has water
        when(field.CanAcceptWater()).thenReturn(true);
        when(field.AcceptWater(pumpSpy)).thenReturn(false);
        pumpSpy.ForwardWater();
        verify(pumpSpy, never()).DecreaseStoredWater();

        // Out accepts water
        when(field.AcceptWater(pumpSpy)).thenReturn(true);
        pumpSpy.ForwardWater();
        verify(pumpSpy).DecreaseStoredWater();

        clearInvocations(pumpSpy);

        // The pump becomes empty
        pumpSpy.SetStoredWater(1);
        pumpSpy.ForwardWater();
        verify(pumpSpy).DecreaseStoredWater();
    }

    @Test
    void saboteurOptions_pass() {
        var pumpSpy = spy(pump);
        var saboteur = new Saboteur("", "");
        var field = mock(Field.class);

        when(gameInstance.GetMap()).thenReturn(map);
        when(map.GetNeighbours(any())).thenReturn(List.of(field));
        when(pumpSpy.GetPerformAction()).thenReturn("pass");

        var canMoveOptions = new boolean[]{true, false};
        for (var canMove : canMoveOptions) {
            when(field.CanAcceptPlayer()).thenReturn(canMove);
            pumpSpy.SaboteurOptions(saboteur);
            assertEquals(canMove, saboteur.GetOptions().contains("move"));
        }

        var isBrokenOptions = new boolean[]{true, false};
        for (var isBroken : isBrokenOptions) {
            when(pumpSpy.GetIsBroken()).thenReturn(isBroken);
            pumpSpy.SaboteurOptions(saboteur);
            assertEquals(!isBroken, saboteur.GetOptions().contains("set pump"));
        }

        verify(gameFrameInstance, times(canMoveOptions.length + isBrokenOptions.length)).EnableActions(any());
        assertEquals(0, saboteur.GetActionPoints());
    }

    @Test
    void saboteurOptions_move() {
        var pumpSpy = spy(pump);
        var saboteur = spy(new Saboteur("", ""));
        var endField = mock(Field.class);
        var startField = mock(Field.class);

        saboteur.SetField(startField);
        when(map.GetNeighbours(any())).thenReturn(List.of(endField));
        when(pumpSpy.GetPerformAction()).thenReturn("move");
        when(endField.CanAcceptPlayer()).thenReturn(true);
        when(map.GetDirection(any(), any())).thenReturn("direction");
        when(map.GetNeighbourFromDirection(any(), any())).thenReturn(endField);
        when(pumpSpy.GetIsBroken()).thenReturn(true);

        pumpSpy.SaboteurOptions(saboteur);

        verify(gameFrameInstance, times(2)).EnableActions(any());
        verify(startField).RemovePlayer(saboteur);
        verify(endField).AcceptPlayer();
    }

    @Test
    void saboteurOptions_setPump() {
        var pumpSpy = spy(pump);
        var saboteur = spy(new Saboteur("", ""));
        var field = mock(Field.class);
        var neighbour = mock(Field.class);

        when(map.GetNeighbours(any())).thenReturn(List.of(field, neighbour));
        when(pumpSpy.GetPerformAction()).thenReturn("set pump");
        when(field.CanAcceptPlayer()).thenReturn(true);
        when(map.GetDirection(any(), any())).thenReturn("direction");
        when(map.GetNeighbourFromDirection(any(), any())).thenReturn(field);
        when(pumpSpy.GetIsBroken()).thenReturn(false);

        pumpSpy.SaboteurOptions(saboteur);

        verify(gameFrameInstance, times(3)).EnableActions(any());
        assertEquals(field, pumpSpy.GetPIn());
        assertEquals(field, pumpSpy.GetPOut());
    }

    @Test
    void fixerOptions_move() {
        var pumpSpy = spy(pump);
        var fixer = spy(new Fixer("", ""));
        var endField = mock(Field.class);
        var startField = mock(Field.class);

        fixer.SetField(startField);
        when(map.GetNeighbours(any())).thenReturn(List.of(endField));
        when(pumpSpy.GetPerformAction()).thenReturn("move");
        when(endField.CanAcceptPlayer()).thenReturn(true);
        when(map.GetDirection(any(), any())).thenReturn("direction");
        when(map.GetNeighbourFromDirection(any(), any())).thenReturn(endField);

        pumpSpy.FixerOptions(fixer);

        verify(fixer).AddOption("move");
        verify(gameFrameInstance, times(2)).EnableActions(any());
        verify(startField).RemovePlayer(fixer);
        verify(endField).AcceptPlayer();
    }

    @Test
    void fixerOptions_pass() {
        var pumpSpy = spy(pump);
        var fixer = spy(new Fixer("", ""));
        var field = mock(Field.class);

        when(map.GetNeighbours(any())).thenReturn(List.of(field));
        when(pumpSpy.GetPerformAction()).thenReturn("pass");
        when(field.CanAcceptPlayer()).thenReturn(false);
        when(field.GetRemovable()).thenReturn(true);
        when(field.GetReplacable()).thenReturn(true);
        when(pumpSpy.GetIsBroken()).thenReturn(false);

        var hasActiveOptions = new boolean[]{true, false};
        for (var hasActive : hasActiveOptions) {
            fixer.SetHasActive(hasActive);

            pumpSpy.FixerOptions(fixer);

            var fixerOptions = fixer.GetOptions();
            assertFalse(fixerOptions.contains("move"));
            assertEquals(hasActive, fixerOptions.contains("place active"));
            assertEquals(!hasActive, fixerOptions.contains("remove pipe"));
        }

        verify(gameFrameInstance, times(hasActiveOptions.length)).EnableActions(any());
        assertEquals(0, fixer.GetActionPoints());

        // Nowhere to place active
        when(field.GetReplacable()).thenReturn(false);
        fixer.SetHasActive(true);
        pumpSpy.FixerOptions(fixer);
        assertFalse(fixer.GetOptions().contains("place active"));

        // Nothing to remove
        when(field.GetRemovable()).thenReturn(false);
        pumpSpy.FixerOptions(fixer);
        assertFalse(fixer.GetOptions().contains("remove pipe"));
    }

    @Test
    void fixerOptions_removePipe() {
        var pumpSpy = spy(pump);
        var fixer = mock(Fixer.class);
        var field = mock(Pipe.class);

        when(map.GetNeighbours(any())).thenReturn(List.of(field));
        when(pumpSpy.GetPerformAction()).thenReturn("remove pipe");
        when(field.CanAcceptPlayer()).thenReturn(true);
        when(field.GetRemovable()).thenReturn(true);
        when(field.GetReplacable()).thenReturn(false);
        when(map.GetDirection(any(), any())).thenReturn("direction");
        when(map.GetNeighbourFromDirection(any(), any())).thenReturn(field);

        pumpSpy.FixerOptions(fixer);

        verify(fixer).AddOption("remove pipe");
        verify(gameFrameInstance, times(2)).EnableActions(any());
        verify(fixer).RemoveActivePipe(field);
    }

    @Test
    void fixerOptions_placeActive() {
        var pumpSpy = spy(pump);
        var fixer = mock(Fixer.class);
        var field = mock(Pipe.class);

        when(map.GetNeighbours(any())).thenReturn(List.of(field));
        when(pumpSpy.GetPerformAction()).thenReturn("place active");
        when(field.CanAcceptPlayer()).thenReturn(true);
        when(field.GetRemovable()).thenReturn(false);
        when(field.GetReplacable()).thenReturn(true);
        when(map.GetDirection(any(), any())).thenReturn("direction");
        when(map.GetNeighbourFromDirection(any(), any())).thenReturn(field);
        when(fixer.GetHasActive()).thenReturn(true);

        pumpSpy.FixerOptions(fixer);

        verify(fixer).AddOption("place active");
        verify(gameFrameInstance, times(2)).EnableActions(any());
        verify(fixer).Place(field);
    }

    @Test
    void fixerOptions_fix() {
        var pumpSpy = spy(pump);
        var fixer = mock(Fixer.class);
        var field = mock(Pipe.class);

        when(map.GetNeighbours(any())).thenReturn(List.of(field));
        when(pumpSpy.GetPerformAction()).thenReturn("fix");
        when(field.CanAcceptPlayer()).thenReturn(false);
        when(field.GetRemovable()).thenReturn(false);
        when(field.GetReplacable()).thenReturn(false);
        when(map.GetDirection(any(), any())).thenReturn("direction");
        when(map.GetNeighbourFromDirection(any(), any())).thenReturn(field);
        when(pumpSpy.GetIsBroken()).thenReturn(true);

        pumpSpy.FixerOptions(fixer);

        verify(fixer).AddOption("fix");
        verify(fixer, never()).AddOption("set pump");
        verify(gameFrameInstance).EnableActions(any());
        verify(fixer).Fix(pumpSpy);
    }

    @Test
    void fixerOptions_setPump() {
        var pumpSpy = spy(pump);
        var fixer = spy(new Fixer("", ""));
        var field = mock(Field.class);
        var neighbour = mock(Field.class);

        when(map.GetNeighbours(any())).thenReturn(List.of(field, neighbour));
        when(pumpSpy.GetPerformAction()).thenReturn("set pump");
        when(field.CanAcceptPlayer()).thenReturn(true);
        when(map.GetDirection(any(), any())).thenReturn("direction");
        when(map.GetNeighbourFromDirection(any(), any())).thenReturn(field);
        when(pumpSpy.GetIsBroken()).thenReturn(false);

        pumpSpy.FixerOptions(fixer);

        verify(fixer).AddOption("set pump");
        verify(fixer, never()).AddOption("fix");
        verify(gameFrameInstance, times(3)).EnableActions(any());
        assertEquals(field, pumpSpy.GetPIn());
        assertEquals(field, pumpSpy.GetPOut());
    }
}