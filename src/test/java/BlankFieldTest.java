import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class BlankFieldTest {
    private BlankField field;

    @BeforeEach
    public void init() {
        this.field = new BlankField();
    }

    @Test
    void GetReferenceID() {
        this.field = new BlankField("refID");

        assertEquals("refID", field.GetReferenceID());
    }

    @Test 
    void CanAcceptPlayer() {
        assertEquals(false, field.CanAcceptPlayer());
    }

    
}