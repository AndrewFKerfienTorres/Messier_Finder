import application.Obstruction;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ObstructionsTest {


    @Test
    void CanBeInstantiated(){
        Obstruction o = new Obstruction("",0.0,0.1,0.0,0.1);
        assertNotNull(o);

        assertEquals("",o.getDescription());
        assertEquals(0.0,o.getBeginAzimuth());
        assertEquals(0.0,o.getBeginAltitude());
        assertEquals(0.1,o.getEndAltitude());
        assertEquals(0.1,o.getEndAzimuth());
    }



    @Test
    void testValidConstruction() {
        Obstruction obs = new Obstruction(
                "Tree",
                10.0, 20.0,
                5.0, 15.0
        );

        assertEquals("Tree", obs.getDescription());
        assertEquals(10.0, obs.getBeginAzimuth());
        assertEquals(20.0, obs.getEndAzimuth());
        assertEquals(5.0, obs.getBeginAltitude());
        assertEquals(15.0, obs.getEndAltitude());
    }

    // -----------------------------
    // ALTITUDE VALIDATION
    // -----------------------------

    @Test
    void testAltitudeBeginGreaterThanEndThrows() {
        assertThrows(IllegalArgumentException.class, () ->
                new Obstruction("Hill", 10, 20, 30, 10)
        );
    }

    @Test
    void testAltitudeNegativeThrows() {
        assertThrows(IllegalArgumentException.class, () ->
                new Obstruction("Hill", 10, 20, -1, 10)
        );
        assertThrows(IllegalArgumentException.class, () ->
                new Obstruction("Hill", 10, 20, 1, -1)
        );
    }

    @Test
    void testAltitudeGreaterThan360Throws() {
        assertThrows(IllegalArgumentException.class, () ->
                new Obstruction("Hill", 10, 20, 361, 10)
        );
        assertThrows(IllegalArgumentException.class, () ->
                new Obstruction("Hill", 10, 20, 10, 361)
        );
    }

    @Test
    void testAltitudeBoundaryZeroAnd360AreAllowed() {
        Obstruction o1 = new Obstruction("ZeroStart", 10, 20, 0, 30);
        assertEquals(0, o1.getBeginAltitude());

        assertThrows(IllegalArgumentException.class, () -> new Obstruction("ZeroEnd", 10, 20, 10, 0));

       assertThrows(IllegalArgumentException.class, ()-> new Obstruction("360Start", 10, 20, 360, 360));
    }



    // -----------------------------
    // AZIMUTH VALIDATION
    // -----------------------------

    @Test
    void testAzimuthBeginGreaterThanEndThrows() {
        assertThrows(IllegalArgumentException.class, () ->
                new Obstruction("Building", 40, 30, 5, 10)
        );
    }

    @Test
    void testAzimuthNegativeThrows() {
        assertThrows(IllegalArgumentException.class, () ->
                new Obstruction("Building", -5, 10, 5, 10)
        );
        assertThrows(IllegalArgumentException.class, () ->
                new Obstruction("Building", 5, -10, 5, 10)
        );
    }

    @Test
    void testAzimuthGreaterThan360Throws() {
        assertThrows(IllegalArgumentException.class, () ->
                new Obstruction("Building", 361, 20, 5, 10)
        );
        assertThrows(IllegalArgumentException.class, () ->
                new Obstruction("Building", 10, 361, 5, 10)
        );
    }

    @Test
    void testAzimuthBoundaryZeroAnd360AreAllowed() {
        Obstruction o1 = new Obstruction("ZeroStart", 0, 20, 5, 10);
        assertEquals(0, o1.getBeginAzimuth());

        assertThrows(IllegalArgumentException.class, () -> new Obstruction("ZeroEnd", 10, 0, 5, 10));

        assertThrows(IllegalArgumentException.class, () -> new Obstruction("FullCircle", 360, 360, 5, 10));
    }








}
