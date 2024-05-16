import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

class FixerTest {
    private Fixer fixer;

    @BeforeEach
    void init() {
        fixer = new Fixer("fixer", "picture");
    }

    @Test
    void Fix() {
        Active active = Mockito.mock(Active.class);

        fixer.Fix(active);

        verify(active, times(1)).Fix();
    }

    @Test
    void RemoveActivePipe() {
        Pipe pipe = Mockito.mock(Pipe.class);

        fixer.RemoveActivePipe(pipe);

        verify(pipe, times(1)).Remove(fixer);
    }

    @Test
    void CarryPump() {
        Tank tank = Mockito.mock(Tank.class);

        fixer.CarryPump(tank);

        verify(tank, times(1)).GivePump(fixer);
    }

    @Test
    void CarryPipe() {
        Tank tank = Mockito.mock(Tank.class);

        fixer.CarryPipe(tank);

        verify(tank, times(1)).GivePipe(fixer);
    }

    @Test
    void ActiveGetSet() {
        Active active = Mockito.mock(Active.class);

        fixer.SetActive(active);

        assertEquals(active, fixer.GetActive());
    }

    @Test
    void HasActiveGetSet() {
        boolean hasActive = true;

        fixer.SetHasActive(hasActive);

        assertEquals(hasActive, fixer.GetHasActive());
    }

}