package application;

import java.util.ArrayList;
import java.util.List;

public class MessierObject {

    private List<MessierObject> objects;

    public MessierObject() {
        objects = new ArrayList<>();
        loadCatalog();
    }

    private void loadCatalog() {
        // blank for now
    }

    public List<MessierObject> getAllObjects() {
        return objects;
    }

    public void addObject(MessierObject obj) {
        objects.add(obj);
    }
}

