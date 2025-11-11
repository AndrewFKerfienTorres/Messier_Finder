import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

public class CatalogueTest {

    @Test
    void CatalogueCanBeInstantiatedWithSingletonPattern(){
        Catalogue c = Catalogue.getInstance();
        Catalogue c2 = Catalogue.getInstance();
        assertNotNull(c);
        assertNotNull(c2);
        assertEquals(c, c2);
    }

    @Test
    void CatalogueCanBeLoaded() throws IOException, ClassNotFoundException {

        Catalogue.loadCatalogue();

        Catalogue c = Catalogue.getInstance();
        assertNotNull(c.get(1));
    }

    @Test
    void CanIterate() throws IOException, ClassNotFoundException {

        Catalogue.loadCatalogue();

        Catalogue c = Catalogue.getInstance();
        for(CelestialObject co : c){
            assertNotNull(co);
        }
    }

    @Test
    void exceptionHandling(){

        String path = System.getProperty("user.dir") + File.separator + "src" + File.separator + "resources" + File.separator
                + "catalogue.ser";

        File f = new File(path);

        boolean FileExists = f.exists();
        if (FileExists){
            f.renameTo(new File(f.getParent(), "catalogue_T.ser"));
        }

        assertDoesNotThrow(() -> Catalogue.loadCatalogue(), "Catalogue handles exception");

        if (FileExists){
            new File(f.getParent(),"catalogue_t.ser").renameTo(new File(f.getParent(), "catalogue.ser"));
        }

    }

    @Test
    void canAddItemsToCatalogue(){
        Catalogue c = Catalogue.getInstance();
        c.add(new CelestialObject("M1",420,8.4,"Crab Nebula ","Taurus ","Super­nova remnant ","NGC 1952 ",4.9));

        assertNotNull(c.get("M1"));
    }



}
