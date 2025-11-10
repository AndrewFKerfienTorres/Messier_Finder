package CatalogueUtils;

import java.io.Serializable;
import java.time.ZonedDateTime;
import Observatory;
import SkyPosition;
import Obstruction;

public class CelestialObject implements Serializable {

    private final String messierIndex;
    private final double apparentMagnitude;
    private final double apparentDimensions;
    private final String commonName;
    private final String constellation;
    private final String objectType;
    private final String NGC_IC_Nbr;
    private final double distance;
    private double declination;
    private double rightAscension;


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

    public boolean isVisible(Observatory observ, ZonedDateTime timeUTC, Telescope telescope){
        return !isObjectObstructed(obser)&&isTelescopeLimitedMagnitudeSufficient(telescope)&&isTelescopeAperatureSuffcient(telescope);
    }
    public boolean isTelescopeLimitedMagnitudeSufficient(Telescope telescope){
        double limitingMagnitude = 2.0 + 5.0 * Math.log10(telescope.getAperature());
        return this.apparentMagnitude >= limitingMagnitude;
    }

    public boolean isTelescopeAperatureSuffcient(Telescope telescope){
        double angularResolutionInDegrees = 0.322 / telescope.getAperature();
        return this.apparentMagnitude >= angularResolutionInDegrees;

    }



    public boolean isObjectObstructed(Observatory observ){
      Obstruction[] obstructions;
      obstructions = observ.getObstructions();
      double latitude = SkyPosition.getLatitude(timeUTC, observ.getLatitude(), observ.getLongitude(), this.rightAscension, this.declination);
      double azimuth = SkyPosition.getAzimuth(timeUTC, observ.getLatitude(), observ.getLongitude(), this.rightAscension, this.declination);
      for(int i = 0; i < obstructions.length; i++){
          if(obstructions[i].getBeginLatitude() < latitude&&obstructions[i].getEndLatitude() > latitude&&obstructions[i].getBeginAzimuth() < azimuth&&obstructions[i].getEndAzimuth())
              return true;
      }
      return false;
    }
}
