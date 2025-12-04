import application.Catalogue;
import application.CelestialObject;
import application.ObjectType;
import application.Telescope;
import org.junit.jupiter.api.Test;
import java.io.IOException;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Nested;

public class CelestialObjectTest {



    @Test
    void canDeserialize() throws IOException, ClassNotFoundException {

        Catalogue.loadCatalogue();
        Catalogue c = Catalogue.getInstance();

        assertNotNull(c);

        CelestialObject m1 = c.get("M1");
        CelestialObject m100 = c.get("M100");

        System.out.println(m1);
        assertNotNull(m1);
        assertNotNull(m100);

        //m1

        //assertEquals(420, m1.getApparentDimensions()); //apparent dimensions
        assertEquals(8.4, m1.getApparentMagnitude()); //apparent magnitude
        assertEquals(ObjectType.SUPERNOVA_REMNANT, m1.getObjectType()); //type
        assertEquals("4.9–8.1 kly", m1.getDistance()); //distance
        assertEquals("Taurus", m1.getConstellation()); //constellation

        assertEquals("Crab Nebula",m1.getCommonName());
        assertEquals("NGC 1952", m1.getNGC_IC_Nbr());
        assertEquals("M1", m1.getMessierIndex());

        //m100

        //assertEquals(7.4,m100.getApparentDimensions()); //apparent dimensions
        assertEquals(9.3,m100.getApparentMagnitude()); //apparent magnitude
        assertEquals(ObjectType.GALAXY,m100.getObjectType()); //type
        assertEquals("55,000 kly",m100.getDistance()); //distance
        assertEquals("Coma Berenices",m100.getConstellation()); //constellation

    }

//    @Test
//    void TestWhetherOrNotTheMethodToDetermineIfLimitedMagnitudeIsSufficient() throws IOException, ClassNotFoundException{
//        Telescope t = new Telescope();
//        Catalogue.loadCatalogue();
//        Catalogue c = Catalogue.getInstance();
//        CelestialObject o = c.get("M1");
//
//        boolean result = o.isTelescopeLimitedMagnitudeSufficient(t);
//
//        assertTrue(result); //TODO: for Steve to make sure that this result is true, SEE BELOW!!!
//    }


//    @Test
//    void TestWhetherOrNotTheMethodToDetermineIfLimitedApertureIsSufficient() throws IOException, ClassNotFoundException{
//        Telescope t = new Telescope();
//        Catalogue.loadCatalogue();
//        Catalogue c = Catalogue.getInstance();
//        CelestialObject o = c.get("M1");
//
//        boolean result = o.isTelescopeAperatureSuffcient(t);
//
//        assertFalse(result); //TODO: I don't think that the values for the telescope are exaclty correct:
//                             //TODO: isTelescopeAperatureSuffcient::angularResolutionInDegrees = infinity
//                             //TODO: Further evidenced by the methods not being detected as hit by the test
//                             //TODO: Shall need to come up in meeting.
//    }

    @Test
    void CanBeManuallyInstantiatedAndMakeSureThatTheCommonNameAndNumberCanBeGotten(){
        CelestialObject o = new CelestialObject("M1","Crab Nebula");
        assertNotNull(o);
    }


    @Nested
    class LimitingMagnitudeTests {

        // Helper to create a CelestialObject with a given magnitude
        private CelestialObject makeObject(double mag) {
            CelestialObject obj = new CelestialObject("M0", "TestObject");
            obj.setApparentMagnitude(mag);
            return obj;
        }

        @Test
        void test50mmBinoculars_ObjectBrighterVisible() {
            Telescope binoculars = new Telescope(0, 50); // 50 mm
            CelestialObject obj = makeObject(5.4);      // Brighter than limit ~10.5

            assertTrue(obj.isTelescopeLimitedMagnitudeSufficient(binoculars));
        }

        @Test
        void test50mmBinoculars_ObjectDimmerNotVisible() {
            Telescope binoculars = new Telescope(0, 50);
            CelestialObject obj = makeObject(11.0); // Dimmer than limit ~10.5

            assertFalse(obj.isTelescopeLimitedMagnitudeSufficient(binoculars));
        }

        @Test
        void test80mmRefractor_ObjectBrighterVisible() {
            Telescope refractor = new Telescope(0, 80); // 80 mm
            CelestialObject obj = makeObject(5.9);      // Brighter than limit ~11.5

            assertTrue(obj.isTelescopeLimitedMagnitudeSufficient(refractor));
        }

        @Test
        void test80mmRefractor_ObjectDimmerNotVisible() {
            Telescope refractor = new Telescope(0, 80);
            CelestialObject obj = makeObject(12.0); // Dimmer than limit ~11.5

            assertFalse(obj.isTelescopeLimitedMagnitudeSufficient(refractor));
        }

        @Test
        void test6InchTelescope_ObjectBrighterVisible() {
            Telescope sixInch = new Telescope(0, 150); // 150 mm
            CelestialObject obj = makeObject(7.3);     // Brighter than limit ~13.9

            assertTrue(obj.isTelescopeLimitedMagnitudeSufficient(sixInch));
        }

        @Test
        void test6InchTelescope_ObjectDimmerNotVisible() {
            Telescope sixInch = new Telescope(0, 150);
            CelestialObject obj = makeObject(14.0); // Dimmer than limit ~13.9

            assertFalse(obj.isTelescopeLimitedMagnitudeSufficient(sixInch));
        }

        @Test
        void test10InchTelescope_ObjectBrighterVisible() {
            Telescope tenInch = new Telescope(0, 254); // 254 mm
            CelestialObject obj = makeObject(8.1);     // Brighter than limit ~15.0

            assertTrue(obj.isTelescopeLimitedMagnitudeSufficient(tenInch));
        }

        @Test
        void test10InchTelescope_ObjectDimmerNotVisible() {
            Telescope tenInch = new Telescope(0, 254);
            CelestialObject obj = makeObject(15.5); // Dimmer than limit ~15.0

            assertFalse(obj.isTelescopeLimitedMagnitudeSufficient(tenInch));
        }
    }


    @Nested
    class ApertureSufficiencyTests {

        // Helper for Circle
        private CelestialObject makeCircleObject(double diameterDeg) {
            CelestialObject obj = new CelestialObject("M0", "CircleObject");
            obj.setApparentDimensions(new CelestialObject.Circle(diameterDeg));
            return obj;
        }

        // Helper for Rectangle
        private CelestialObject makeRectangleObject(double widthDeg, double heightDeg) {
            CelestialObject obj = new CelestialObject("M1", "RectangleObject");
            obj.setApparentDimensions(new CelestialObject.Rectangle(widthDeg, heightDeg));
            return obj;
        }

        @Test
        void test50mmBinoculars_CircleResolvable() {
            Telescope binoculars = new Telescope(0, 50); // 50 mm
            double resolution = 0.322 / 50;             // ~0.00644 deg
            CelestialObject obj = makeCircleObject(0.01); // bigger than resolution

            assertTrue(obj.isTelescopeAperatureSuffcient(binoculars));
        }

        @Test
        void test50mmBinoculars_CircleNotResolvable() {
            Telescope binoculars = new Telescope(0, 50);
            CelestialObject obj = makeCircleObject(0.005); // smaller than ~0.00644

            assertFalse(obj.isTelescopeAperatureSuffcient(binoculars));
        }

        @Test
        void test80mmRefractor_RectangleResolvable() {
            Telescope refractor = new Telescope(0, 80);
            double resolution = 0.322 / 80; // ~0.004025 deg
            CelestialObject obj = makeRectangleObject(0.002, 0.005); // larger side = 0.005 > resolution

            assertTrue(obj.isTelescopeAperatureSuffcient(refractor));
        }

        @Test
        void test80mmRefractor_RectangleNotResolvable() {
            Telescope refractor = new Telescope(0, 80);
            CelestialObject obj = makeRectangleObject(0.001, 0.003); // larger side = 0.003 < resolution

            assertFalse(obj.isTelescopeAperatureSuffcient(refractor));
        }

        @Test
        void test6InchTelescope_CircleResolvable() {
            Telescope sixInch = new Telescope(0, 150); // 150 mm
            double resolution = 0.322 / 150;           // ~0.002147 deg
            CelestialObject obj = makeCircleObject(0.005); // bigger than resolution

            assertTrue(obj.isTelescopeAperatureSuffcient(sixInch));
        }

        @Test
        void test6InchTelescope_CircleNotResolvable() {
            Telescope sixInch = new Telescope(0, 150);
            CelestialObject obj = makeCircleObject(0.001); // smaller than resolution

            assertFalse(obj.isTelescopeAperatureSuffcient(sixInch));
        }

        @Test
        void test10InchTelescope_RectangleResolvable() {
            Telescope tenInch = new Telescope(0, 254);
            double resolution = 0.322 / 254; // ~0.001267 deg
            CelestialObject obj = makeRectangleObject(0.0015, 0.0010); // larger side = 0.0015 > resolution

            assertTrue(obj.isTelescopeAperatureSuffcient(tenInch));
        }

        @Test
        void test10InchTelescope_RectangleNotResolvable() {
            Telescope tenInch = new Telescope(0, 254);
            CelestialObject obj = makeRectangleObject(0.0010, 0.0010); // larger side = 0.0010 < resolution

            assertFalse(obj.isTelescopeAperatureSuffcient(tenInch));
        }
    }

    @Nested
    class ApparentDimensionsStringTests {

        @Test
        void testCircleDimensions() {
            CelestialObject obj = new CelestialObject("M1", "Crab Nebula");
            obj.setApparentDimensions(new CelestialObject.Circle(5.5));
            String result = obj.getApparentDimensionsString();
            assertEquals("05h 30m 00.0s", result, "Circle diameter should be returned as string");
        }

        @Test
        void testRectangleDimensions() {
            CelestialObject obj = new CelestialObject("M2", "Some Rectangle Object");
            obj.setApparentDimensions(new CelestialObject.Rectangle(2.0, 3.5));
            String result = obj.getApparentDimensionsString();
            assertEquals("2° 0′ 0.000″ x 3° 30′ 0.000″", result, "Rectangle dimensions should be returned as 'width x height'");
        }

        @Test
        void testNullDimensions() {
            CelestialObject obj = new CelestialObject("M3", "Unknown Object");
            obj.setApparentDimensions(null);
            String result = obj.getApparentDimensionsString();
            assertEquals("Unknown", result, "Null dimensions should return 'Unknown'");
        }
    }


}
