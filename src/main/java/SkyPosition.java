import java.time.*;
//import java.time.temporal.ChronoField;
//import java.util.GregorianCalendar;

public class SkyPosition {

    // INPUTS -------------------------
    //double latitudeDeg = 43.439978;         // observer latitude (°)
    //double longitudeDeg = -76.550036;// observer longitude (°) (west negative)

    //double raHours = 6.771694444;      // RA (hours)  Sirius
    //double decDeg = -16.74805556;       // Dec (degrees) Sirius

    //double raHours = 5.601833333;      // RA (hours) Crab Nebula
    //double decDeg = 22.03275;       // Dec (degrees) Crab Nebula

    //ZonedDateTime timeUTC = ZonedDateTime.of(
    //       2025, 11, 03, 8, 47, 01, 0, ZoneOffset.UTC);

    // Convert calendar date/time to Julian Date
    public static double toJulianDate(ZonedDateTime zdt) {
        int year = zdt.getYear();
        int month = zdt.getMonthValue();
        double day = zdt.getDayOfMonth()
                + (zdt.getHour() + (zdt.getMinute() + zdt.getSecond() / 60.0) / 60.0) / 24.0;

        if (month <= 2) {
            year -= 1;
            month += 12;
        }

        int A = year / 100;
        int B = 2 - A + (A / 4);

        return Math.floor(365.25 * (year + 4716))
                + Math.floor(30.6001 * (month + 1))
                + day + B - 1524.5;
    }

    // Compute Greenwich Mean Sidereal Time (hours)
    public static double gmstFromJulianDate(double jd) {
        double T = (jd - 2451545.0) / 36525.0;
        double gmst = 6.697374558 + 2400.051336*T + 0.000025862*T*T;
        double frac = (jd + 0.5) % 1.0;
        gmst += frac * 24.0 * 1.002737909;
        gmst = gmst % 24.0;
        if (gmst < 0) gmst += 24.0;
        return gmst;
    }

    static double getAltitude(ZonedDateTime timeUTC, double latitudeDeg, double longitudeDeg, double raHours, double decDeg) {

        double jd = toJulianDate(timeUTC);
        double gmst = gmstFromJulianDate(jd);
        double lst = (gmst + longitudeDeg / 15.0) % 24.0;
        if (lst < 0) lst += 24.0;

        double haHours = lst - raHours;
        double haDeg = haHours * 15.0;
        double dec = Math.toRadians(decDeg);
        double ha = Math.toRadians(haDeg);
        double lat = Math.toRadians(latitudeDeg);

        double sinAlt = Math.sin(dec) * Math.sin(lat) + Math.cos(dec) * Math.cos(lat) * Math.cos(ha);
        double alt = Math.asin(sinAlt);

        double altDeg = Math.toDegrees(alt);

        // apply atmospheric refraction (Saemundsson formula)
        if (altDeg > -1 && altDeg < 90) {
            double R = 1.02 / Math.tan(Math.toRadians(altDeg + 10.3 / (altDeg + 5.11))) / 60.0;
            altDeg += R;
        }

        return altDeg;
    }
    static double getAzimuth(ZonedDateTime timeUTC, double latitudeDeg, double longitudeDeg, double raHours, double decDeg) {

        double jd = toJulianDate(timeUTC);
        double gmst = gmstFromJulianDate(jd);
        double lst = (gmst + longitudeDeg / 15.0) % 24.0;
        if (lst < 0) lst += 24.0;

        double haHours = lst - raHours;
        double haDeg = haHours * 15.0;
        double dec = Math.toRadians(decDeg);
        double ha = Math.toRadians(haDeg);
        double lat = Math.toRadians(latitudeDeg);

        double y = -Math.sin(ha);
        double x = Math.cos(ha) * Math.sin(lat) - Math.tan(dec) * Math.cos(lat);
        double az = Math.atan2(x, y);
        az = Math.toDegrees(az);
        az += 90.0;
        if (az < 0) az += 360.0;

        return az;
    }
}
