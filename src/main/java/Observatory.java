import java.io.Serializable;
import java.util.TimeZone;
public class Observatory implements Serializable {
    private double longitude;
    private double latitude; //initialized to rough estimates of the Rice Creek Telescope's location
    private String observatoryName;
    private TimeZone timeZone;
    private Obstruction[] obstructions;

    public Observatory(){
        longitude = -76.550;
        latitude = 43.43;
        observatoryName = "Rice Creek Field Station";
        timeZone = TimeZone.getTimeZone("America/New_York");
    }
    public Observatory(double longitude, double latitude, String observatoryName, TimeZone t){
        timeZone = t;
        this.longitude = longitude;
        this.latitude = latitude;
        this.observatoryName = observatoryName;
    }


    public double getLongitude() {
        return longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public String getObservatoryName() {
        return observatoryName;
    }

    public TimeZone getTimeZone() {
        return timeZone;
    }

    public Obstruction[] getObstructions() {
        return obstructions;
    }
}
