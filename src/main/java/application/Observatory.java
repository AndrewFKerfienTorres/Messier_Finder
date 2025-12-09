package application;

import java.io.Serializable;
import java.util.TimeZone;
public class Observatory implements Serializable {
    private double longitude;
    private double latitude; //initialized to rough estimates of the Rice Creek application.Telescope's location
    private String observatoryName;
    private TimeZone timeZone;
    private Obstruction[] obstructions;
    private Telescope telescope;

    public Observatory(){
        longitude = -76.550;
        latitude = 43.43;
        observatoryName = "Rice Creek Field Station";
        timeZone = TimeZone.getTimeZone("America/New_York");
        obstructions = new Obstruction[]{
                new Obstruction("tree", 0, 30, 0,40),
                new Obstruction("tree",30, 90, 0,20),
                new Obstruction("tree",90, 140, 0,40),
                new Obstruction("tree",140, 240, 0,30),
                new Obstruction("tree",240, 300, 0,60),
                new Obstruction("tree",300, 360, 0,50)
        };
        telescope = new Telescope(90, 45);

    }
    public Observatory(double longitude, double latitude, String observatoryName, TimeZone t, Telescope telescope){
        timeZone = t;
        this.longitude = longitude;
        this.latitude = latitude;
        this.observatoryName = observatoryName;
        this.telescope = telescope;
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

    public void setObstructions(Obstruction[] o){
        this.obstructions = o;
    }

    public void addObstructions(Obstruction o){
        int len = obstructions.length + 1;
        Obstruction[] newObstructions = new Obstruction[len];
        int i = 0;
        for(; i < obstructions.length; ++i){
            newObstructions[i] = obstructions[i];
        }
        newObstructions[i] = o;
        obstructions = newObstructions;
    }
    public Telescope getTelescope() {
        return telescope;
    }
    public void setTelescope(Telescope t) {
        this.telescope = t;
    }
}
