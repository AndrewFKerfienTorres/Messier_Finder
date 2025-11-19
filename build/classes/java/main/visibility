public enum visibility {
    VISIBLE("Visible"),
    PARTIALLY_VISIBLE("Partially Visible"),
    NOT_VISIBLE("Not Visible");

    private final String displayName;

    visibility(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }

    @Override
    public String toString() {
        return displayName;
    }

    public static visibility fromDisplayName(String name) {
        for (visibility v : values()) {
            if (v.displayName.equalsIgnoreCase(name)) {
                return v;
            }
        }
        return NOT_VISIBLE;
    }
}
