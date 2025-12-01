import application.Telescope;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class TelescopeTest {


    @Test
    void canGetFieldOfView(){
        Telescope t = new Telescope(0.1, 0.1);
        assertEquals(0.1, t.getFieldOfView());
    }

    @Test
    void canGetAperture(){
        Telescope t = new Telescope(0.1, 16.0);
        assertEquals(16.0, t.getAperature());
    }





}
