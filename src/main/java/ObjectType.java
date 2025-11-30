public enum ObjectType {
    NEBULA("Nebula"),
    SUPERNOVA_REMNANT("Supernova Remnant"),
    OPEN_CLUSTER("Open Cluster"),
    GLOBULAR_CLUSTER("Globular Cluster"),
    GALAXY("Galaxy"),
    PLANETARY_NEBULA("Planetary Nebula"),
    STAR("Star"),
    ASTERISM("Asterism"),
    OTHER("Other");

    private final String displayName;

    ObjectType(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }

    @Override
    public String toString() {
        return displayName;
    }

    public static ObjectType fromDisplayName(String name) {
        for (ObjectType type : values()) {
            if (type.displayName.equalsIgnoreCase(name)) {
                return type;
            }
        }
        return OTHER;
    }
}