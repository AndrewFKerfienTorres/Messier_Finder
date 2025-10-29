package CatalogueUtils;

import java.io.*;
import java.util.HashMap;
import java.util.Iterator;

//if catalogue class is modified in any sort of way, make sure to notify via discord in order for
//Andrew to get serialization working correctly (if it is not touched it should work), when doing so please
//refrain from making commits until Andrew has fixed it, at which point pull from origin before commiting.


public class Catalogue implements Iterable<CelestialObject>, Serializable{

    private static Catalogue catalogue; //for singleton pattern.
    private HashMap<String, CelestialObject> items;


    private Catalogue(){
        items = new HashMap<>();
    }

    public static Catalogue getInstance(){
        if (catalogue == null){
            catalogue = new Catalogue();
        }
        return catalogue;
    }

    public static void loadCatalogue(){


        try{
            String resourcedir = System.getProperty("user.dir") + File.separator + "src" + File.separator + "main" +
                    File.separator + "resources" + File.separator + "catalogue.ser";

            ObjectInputStream in = new ObjectInputStream(new FileInputStream(resourcedir));
            catalogue = (Catalogue) in.readObject();
            in.close();
        }catch (IOException | ClassNotFoundException e){
            e.printStackTrace();
        }
    }

    public void add(CelestialObject item){
        items.put(item.getMessierIndex(), item);
    }

    public CelestialObject get(String index){
        return items.getOrDefault(index,null);
    }

    public CelestialObject get(int index){
        return items.getOrDefault("M"+index,null);
    }

    public void addCelestialObject(CelestialObject obj){
        items.put(obj.getMessierIndex(), obj);
    }

    @Override
    public Iterator<CelestialObject> iterator() {
        return items.values().iterator();
    }
}
