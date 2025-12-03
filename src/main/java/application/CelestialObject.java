package application;

import java.io.Serializable;
import java.time.ZonedDateTime;

public class CelestialObject implements Serializable {

    private String messierIndex;
    private String commonName;

    private String constellation;
    private ObjectType objectType;
    private String NGC_IC_Nbr;
    private double apparentMagnitude;
    private String distance;
    private double declination;
    private double rightAscension;
    private String imagePath;
    private ApparentSize apparentDimensions;

    public sealed interface ApparentSize permits Circle, Rectangle {
    }

    public record Circle(double diameter) implements ApparentSize, Serializable {
    }

    public record Rectangle(double width, double height) implements ApparentSize, Serializable {
    }

    public CelestialObject(String messierIndex, String commonName) {

        this.messierIndex = messierIndex;
        this.commonName = commonName;
    }

    public String getMessierIndex() {
        return messierIndex;
    }

    public String getCommonName() {
        return commonName;
    }

    public String toString() {
        return messierIndex + " " + commonName;
    }

    public ApparentSize getApparentDimensions() {
        return apparentDimensions;
    }


    public String getConstellation() {
        return constellation;
    }

    public void setConstellation(String constellation) {
        this.constellation = constellation;
    }

    public ObjectType getObjectType() {
        return objectType;
    }

    public void setObjectType(ObjectType objectType) {
        this.objectType = objectType;
    }

    public String getNGC_IC_Nbr() {
        return NGC_IC_Nbr;
    }

    public void setNGC_IC_Nbr(String NGC_IC_Nbr) {
        this.NGC_IC_Nbr = NGC_IC_Nbr;
    }

    public double getApparentMagnitude() {
        return apparentMagnitude;
    }

    public void setApparentMagnitude(double apparentMagnitude) {
        this.apparentMagnitude = apparentMagnitude;
    }

    public void setApparentDimensions(ApparentSize apparentDimensions) {
        this.apparentDimensions = apparentDimensions;
    }

    public String getDistance() {
        return distance;
    }

    public void setDistance(String distance) {
        this.distance = distance;
    }

    public double getDeclination() {
        return declination;
    }

    public void setDeclination(double declination) {
        this.declination = declination;
    }

    public double getRightAscension() {
        return rightAscension;
    }

    public void setRightAscension(double rightAscension) {
        this.rightAscension = rightAscension;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public String getImagePath() {
        return imagePath;
    }

    public boolean isVisible(Observatory observ, ZonedDateTime timeUTC, Telescope telescope) {
        return isTelescopeLimitedMagnitudeSufficient(telescope)
                && isTelescopeAperatureSuffcient(telescope)
                && !isObjectObstructed(observ, timeUTC)
                && SkyPosition.isSunBelow18(timeUTC, observ.getLatitude(), observ.getLongitude())
                && SkyPosition.getAltitude(timeUTC, observ.getLatitude(), observ.getLongitude(), this.rightAscension, this.declination) > 0.0;
    }

    public boolean isTelescopeLimitedMagnitudeSufficient(Telescope telescope) {
        double limitingMagnitude = 2.0 + 5.0 * Math.log10(telescope.getAperature());
        return this.apparentMagnitude <= limitingMagnitude;
    }


    public boolean isTelescopeAperatureSuffcient(Telescope telescope) {
        double telescopeResolution = 0.322 / telescope.getAperature(); // in degrees

        if (apparentDimensions instanceof Circle circle) {
            double objectSizeDeg = circle.diameter();
            return objectSizeDeg >= telescopeResolution;
        } else if (apparentDimensions instanceof Rectangle rect) {
            // Use the larger side for resolution comparison
            double objectSizeDeg = Math.max(rect.width(), rect.height());
            return objectSizeDeg >= telescopeResolution;
        } else {
            // No size info available
            return false;
        }
    }

    public boolean isObjectObstructed(Observatory observ, ZonedDateTime timeUTC) {
        Obstruction[] obstructions;
        obstructions = observ.getObstructions();
        double latitude = SkyPosition.getAltitude(timeUTC, observ.getLatitude(), observ.getLongitude(), this.rightAscension, this.declination);
        double azimuth = SkyPosition.getAzimuth(timeUTC, observ.getLatitude(), observ.getLongitude(), this.rightAscension, this.declination);
        for (int i = 0; i < obstructions.length; i++) {
            if (obstructions[i].getBeginAltitude() < latitude && obstructions[i].getEndAltitude() > latitude && obstructions[i].getBeginAzimuth() < azimuth && obstructions[i].getEndAzimuth() > azimuth)
                return true;
        }
        return false;
    }
}
