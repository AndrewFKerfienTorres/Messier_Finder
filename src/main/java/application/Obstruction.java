package application;

import java.io.Serializable;

public class Obstruction implements Serializable {

    String description;
    double beginAltitude;
    double endAltitude;
    double beginAzimuth;
    double endAzimuth;

    public Obstruction (String description, double beginAzimuth, double endAzimuth, double beginAltitude, double endAltitude) {

        if (beginAltitude >= endAltitude || beginAltitude < 0 || endAltitude > 360 ) {
            throw new IllegalArgumentException("Invalid begin altitude or end altitude");
        }
        if (beginAzimuth >= endAzimuth || beginAzimuth < 0 || endAzimuth > 360) {
            throw new IllegalArgumentException("Invalid begin azimuth or end azimuth");
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
