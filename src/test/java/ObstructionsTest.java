import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ObstructionsTest {


    @Test
    void CanBeInstantiated(){
        Obstruction o = new Obstruction("",0.0,0.0,0.0,0.0);
        assertNotNull(o);

        assertEquals("",o.getDescription());
        assertEquals(0.0,o.getBeginAzimuth());
        assertEquals(0.0,o.getBeginAltitude());
        assertEquals(0.0,o.getEndAltitude());
        assertEquals(0.0,o.getEndAzimuth());
    }









}
