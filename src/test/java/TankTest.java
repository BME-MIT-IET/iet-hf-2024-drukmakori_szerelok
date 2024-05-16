import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.mockStatic;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;


class TankTest {
    private Tank tank;

    @BeforeEach
    public void init() {
        this.tank = new Tank();
    }

    @Test
    void referenceID() {
        var referenceID = tank.GetReferenceID();
        assertFalse(referenceID.isEmpty());
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
        try (MockedStatic<Game> game = mockStatic(Game.class)) {
            var gameSpy = spy(Game.class);
            game.when(Game::Get).thenReturn(gameSpy);
            var fieldMock = mock(Field.class);
            tank.AcceptWater(fieldMock);
            verify(gameSpy, times(1)).InceraseFixerPoints();
        }
    }
}