public enum Visibility {
    VISIBLE("Visible"),
    PARTIALLY_VISIBLE("Partially Visible"),
    NOT_VISIBLE("Not Visible");

    private String displayName;

    Visibility(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }

    @Override
    public String toString() {
        return displayName;
    }

    public static Visibility fromDisplayName(String name) {
        for (Visibility v : values()) {
            if (v.displayName.equalsIgnoreCase(name)) {
                return v;
            }
        }
        return NOT_VISIBLE;
    }
}