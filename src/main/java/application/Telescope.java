package application;

import java.io.Serializable;

public class Telescope implements Serializable {

    private double fieldOfView; //will need to be updated, I was unsure of the value for rice creek
    private double aperature;


    public Telescope(double fieldOfView, double aperature){
        this.fieldOfView = fieldOfView;
        this.aperature = aperature;
    }



    public double getFieldOfView() {
        return fieldOfView;
    }

    public void setFieldOfView(double fieldOfView) {
        this.fieldOfView = fieldOfView;
    }





    public double getAperature() {
        return aperature;
    }

    public void setAperture(double aperture) {
        this.aperature = aperture;
    }
}
