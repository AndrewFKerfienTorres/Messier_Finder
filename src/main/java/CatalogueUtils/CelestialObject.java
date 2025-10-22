package CatalogueUtils;

import java.io.Serializable;

public class CelestialObject implements Serializable {

    private final String messierIndex;
    private final double apparentMagnitude;
    private final double apparentDimensions;
    private final String commonName;
    private final String constellation;
    private final String objectType;
    private final String NGC_IC_Nbr;
    private final double distance;
    // private final jparsec.Angle declination;
    // private final jparsec.Angle rightAscension;


    public CelestialObject(String messierIndex, double apparentDimensions,
                           double apparentMagnitude, String commonName, String constellation,
                           String objectType, String NGC_IC_Nbr, double distance){

        this.messierIndex = messierIndex;
        this.apparentDimensions = apparentDimensions;
        this.apparentMagnitude = apparentMagnitude;
        this.commonName = commonName;
        this. constellation = constellation;
        this.objectType = objectType; this.NGC_IC_Nbr = NGC_IC_Nbr;
        this.distance = distance;
    }

    public double getApparentDimensions() {
        return apparentDimensions;
    }

    public double getApparentMagnitude() {
        return apparentMagnitude;
    }

    public double getDistance() {
        return distance;
    }

    public String getCommonName() {
        return commonName;
    }

    public String getConstellation() {
        return constellation;
    }

    public String getMessierIndex() {
        return messierIndex;
    }

    public String getNGC_IC_Nbr() {
        return NGC_IC_Nbr;
    }

    public String getObjectType() {
        return objectType;
    }
}
