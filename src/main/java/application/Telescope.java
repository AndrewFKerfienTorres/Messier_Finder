package application;

import java.io.Serializable;

public class Telescope implements Serializable {

    private double fieldOfView; //will need to be updated, I was unsure of the value for rice creek
    private double mirrorDiameter; //initialized for rice creek
    private double aperature;

    public Telescope(){
        fieldOfView = 0.0;
        mirrorDiameter = 16.0;
        aperature = 0.0;
    }

    public double getFieldOfView() {
        return fieldOfView;
    }

    public double getMirrorDiameter() {
        return mirrorDiameter;
    }

    public double getAperature() {
        return aperature;
    }
}
