import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class TelescopeTest {


    @Test
    void canGetFieldOfView(){
        Telescope t = new Telescope();
        assertEquals(0.0, t.getFieldOfView());
    }
    @Test
    void canGetMirrorDiameter(){
        Telescope t = new Telescope();
        assertEquals(16.0, t.getMirrorDiameter());
    }
    @Test
    void canGetAperture(){
        Telescope t = new Telescope();
        assertEquals(0.0, t.getAperature());
    }





}
