package drukmakoriSzerelok;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class FountainTest {
    private Fountain fountain;
    
    @BeforeEach
    void init() {
        this.fountain = new Fountain();
    }

    @Test
    void GetReferenceID() {
        this.fountain = new Fountain("ref");
        assertEquals("ref", fountain.GetReferenceID());
    }

}