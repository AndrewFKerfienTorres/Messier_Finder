import org.junit.jupiter.api.Test;

import java.util.TimeZone;

import static org.junit.jupiter.api.Assertions.*;

public class ObservatoryTest {

    @Test
    void canConstructWithoutParams(){
        Observatory o = new Observatory();
        assertNotNull(o);
    }

    @Test
    void canConstructWithParams(){
        double latitude, longitude;
        String name;
        TimeZone timeZone;

        latitude = 0.0; longitude = 0.0;
        name = "placeholder";
        timeZone = TimeZone.getTimeZone("America/New_York");

        Observatory o = new Observatory(latitude,longitude,name,timeZone);

        assertNotNull(o);
    }

    @Test
    void canGetParams(){
        Observatory o = new Observatory();
        assertNotNull(o);

        double lat = o.getLatitude(), lon = o.getLongitude();
        String name = o.getObservatoryName();
        TimeZone tz = o.getTimeZone();

        assertEquals(43.43,lat);
        assertEquals(-76.550,lon);
        assertEquals("Rice Creek Field Station",name);
        assertEquals(TimeZone.getTimeZone("America/New_York"),tz);
    }

    @Test
    void canSetObstructions(){
        Obstruction[] oa1 = new Obstruction[2];

        Obstruction o1 = new Obstruction("a",0.0,0.0,0.0,0.0);
        Obstruction o2 = new Obstruction("b",0.0,0.0,0.0,0.0);
        Obstruction o3 = new Obstruction("c",0.0,0.0,0.0,0.0);

        oa1[0] = o1; oa1[1] = o2;

        Observatory obs = new Observatory();
        assertNotNull(obs);

        obs.setObstructions(oa1);
        assertNotNull(obs.getObstructions());
        assertEquals(oa1,obs.getObstructions());

        obs.addObstructions(o3);
        assertNotEquals(oa1, obs.getObstructions());
        assertEquals("c", obs.getObstructions()[2].description);
    }








}
