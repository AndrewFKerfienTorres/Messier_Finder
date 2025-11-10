public class Obstruction {

    String description;
    double beginAltitude;
    double endAltitude;
    double beginAzimuth;
    double endAzimuth;

    public Obstruction(String description, double beginAzimuth, double endAzimuth, double beginAltitude, double endAltitude) {

        if (beginAltitude > endAltitude || beginAltitude < 0 || endAltitude < 0) {
            throw new IllegalArgumentException("Beginning altitude must be lower than ending altitude, and neither can be zero.");
        }
        if (beginAzimuth > endAzimuth || beginAzimuth < 0 || endAzimuth < 0) {
            throw new IllegalArgumentException("Beginning azimuth bust be lower than ending azimuth, and neither can be zero.");
        }
        this.description = description;
        this.beginAzimuth = beginAzimuth;
        this.endAzimuth = endAzimuth;
        this.beginAltitude = beginAltitude;
        this.endAltitude = endAltitude;


    }

    public String getDescription() {
        return description;
    }

    public double getBeginAltitude() {
        return beginAltitude;
    }

    public double getEndAltitude() {
        return endAltitude;
    }

    public double getBeginAzimuth() {
        return beginAzimuth;
    }

    public double getEndAzimuth() {
        return endAzimuth;
    }
}
