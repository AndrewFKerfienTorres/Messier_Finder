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

        // Julian/Gregorian switch dates
        ZonedDateTime lastJulian = ZonedDateTime.of(1582, 10, 4, 0, 0, 0, 0, ZoneOffset.UTC);
        ZonedDateTime firstGregorian = ZonedDateTime.of(1582, 10, 15, 0, 0, 0, 0, ZoneOffset.UTC);

        int A, B;

        if (zdt.isBefore(lastJulian) || zdt.isEqual(lastJulian)) {
            // Julian calendar
            B = 0;
        } else if (zdt.isAfter(lastJulian) && zdt.isBefore(firstGregorian)) {
            // Invalid dates (October 5–14, 1582)
            throw new IllegalArgumentException("Date does not exist due to Gregorian reform: " + zdt);
        } else {
            // Gregorian calendar
            A = year / 100;
            B = 2 - A + (A / 4);
        }

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
    public static String doubleToDMS(double decimalDegrees) {
        // Capture the sign so we can format correctly at the end
        String sign = decimalDegrees < 0 ? "-" : "";
        decimalDegrees = Math.abs(decimalDegrees);

        int degrees = (int) decimalDegrees;
        double fractional = decimalDegrees - degrees;

        double minutesFull = fractional * 60.0;
        int minutes = (int) minutesFull;

        double seconds = (minutesFull - minutes) * 60.0;

        // Optional: round seconds to a reasonable precision
        seconds = Math.round(seconds * 1000.0) / 1000.0;  // 3 decimal places

        // Handle rounding that pushes seconds to 60
        if (seconds >= 60.0) {
            seconds -= 60.0;
            minutes += 1;
        }

        // Handle rounding that pushes minutes to 60
        if (minutes >= 60) {
            minutes -= 60;
            degrees += 1;
        }

        return String.format("%s%d° %d′ %.3f″", sign, degrees, minutes, seconds);
    }
    public static String floatToHMS(double hours) {
        int h = (int) hours; // integer hours
        double remainingMinutes = (hours - h) * 60;
        int m = (int) remainingMinutes; // integer minutes
        double s = (remainingMinutes - m) * 60; // seconds, can have decimal

        return String.format("%02dh %02dm %04.1fs", h, m, s);
    }
}
