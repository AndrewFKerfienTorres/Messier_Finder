package CatalogueUtils;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Iterator;

public class Catalogue implements Iterable<CelestialObject>, Serializable{

    private static Catalogue catalogue; //for singleton pattern.
    private HashMap<String, CelestialObject> items;


    private Catalogue(){}

    public static Catalogue getInstance(){
        if (catalogue == null) catalogue = new Catalogue();
        return catalogue;
    }

    public void add(CelestialObject item){
        items.put(item.getMessierIndex(), item);
    }

    public CelestialObject get(String index){
        return items.getOrDefault(index,null);
    }

    public void addCelestialObject(CelestialObject obj){
        items.put(obj.getMessierIndex(), obj);
    }

    @Override
    public Iterator<CelestialObject> iterator() {
        return items.values().iterator();
    }
}
