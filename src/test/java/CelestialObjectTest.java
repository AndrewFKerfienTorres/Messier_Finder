import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

public class CelestialObjectTest {



    @Test
    void canDeserialize() throws IOException, ClassNotFoundException {

        Catalogue.loadCatalogue();
        Catalogue c = Catalogue.getInstance();

        assertNotNull(c);

        CelestialObject m1 = c.get("M1");
        CelestialObject m100 = c.get("M100");

        assertNotNull(m1);
        assertNotNull(m100);

        //m1

        assertEquals(420, m1.getApparentDimensions()); //apparent dimensions
        assertEquals(8.4, m1.getApparentMagnitude()); //apparent magnitude
        assertEquals("Super-nova remnant", m1.getObjectType()); //type
        assertEquals(4.9, m1.getDistance()); //distance
        assertEquals("Taurus", m1.getConstellation()); //constellation

        //m100

        assertEquals(7.4,m100.getApparentDimensions()); //apparent dimensions
        assertEquals(9.3,m100.getApparentMagnitude()); //apparent magnitude
        assertEquals("Spiral galaxy",m100.getObjectType()); //type
        assertEquals(550000,m100.getDistance()); //distance
        assertEquals("Coma Berenices",m100.getConstellation()); //constellation

    }

    @Test
    void TestWhetherOrNotTheMethodToDetermineIfLimitedMagnitudeIsSufficient() throws IOException, ClassNotFoundException{
        Telescope t = new Telescope();
        Catalogue.loadCatalogue();
        Catalogue c = Catalogue.getInstance();
        CelestialObject o = c.get("M1");

        boolean result = o.isTelescopeLimitedMagnitudeSufficient(t);

        assertTrue(result); //TODO: for Steve to make sure that this result is true, SEE BELOW!!!
    }


    @Test
    void TestWhetherOrNotTheMethodToDetermineIfLimitedApertureIsSufficient() throws IOException, ClassNotFoundException{
        Telescope t = new Telescope();
        Catalogue.loadCatalogue();
        Catalogue c = Catalogue.getInstance();
        CelestialObject o = c.get("M1");

        boolean result = o.isTelescopeAperatureSuffcient(t);

        assertFalse(result); //TODO: I don't think that the values for the telescope are exaclty correct:
                             //TODO: isTelescopeAperatureSuffcient::angularResolutionInDegrees = infinity
                             //TODO: Further evidenced by the methods not being detected as hit by the test
                             //TODO: Shall need to come up in meeting.
    }








}
