package application;

import java.time.*;
import java.util.Optional;

public class SkyPosition {

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
        // Julian centuries since J2000.0
        double T = (jd - 2451545.0) / 36525.0;

        // GMST in degrees
        double theta = 280.46061837
                + 360.98564736629 * (jd - 2451545.0)
                + 0.000387933 * T * T
                - T * T * T / 38710000.0;

        // Normalize to 0..360 degrees
        theta = theta % 360.0;
        if (theta < 0) theta += 360.0;

        // Convert degrees to hours (360 deg = 24 h)
        return theta / 15.0;
    }



    public static double getAltitude(ZonedDateTime timeUTC, double latitudeDeg, double longitudeDeg, double raHours, double decDeg) {

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


        return altDeg;
    }
    public static double getAzimuth(ZonedDateTime timeUTC, double latitudeDeg, double longitudeDeg, double raHours, double decDeg) {

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
    public static String doubleToHMS(double hours) {
        int h = (int) hours; // integer hours
        double remainingMinutes = (hours - h) * 60;
        int m = (int) remainingMinutes; // integer minutes
        double s = (remainingMinutes - m) * 60; // seconds, can have decimal

        return String.format("%02dh %02dm %04.1fs", h, m, s);
    }
    private static double[] sunRaDec(ZonedDateTime timeUTC) {
        double jd = toJulianDate(timeUTC);
        double T = (jd - 2451545.0) / 36525.0;

        // Mean longitude of Sun (deg)
        double L0 = (280.46646 + T*(36000.76983 + 0.0003032*T)) % 360;
        if (L0 < 0) L0 += 360;

        // Mean anomaly (deg)
        double M = 357.52911 + T*(35999.05029 - 0.0001537*T);

        // Sun's equation of center (deg)
        double C = Math.sin(Math.toRadians(M))*(1.914602 - T*(0.004817 + 0.000014*T))
                + Math.sin(Math.toRadians(2*M))*(0.019993 - 0.000101*T)
                + Math.sin(Math.toRadians(3*M))*0.000289;

        double trueLong = L0 + C; // Sun's true longitude (deg)

        // Obliquity of ecliptic
        double epsilon = 23 + (26 + ((21.448 - T*(46.815 + T*(0.00059 - T*0.001813))))/60.0)/60.0;

        // Sun's RA and Dec
        double lambdaRad = Math.toRadians(trueLong);
        double epsilonRad = Math.toRadians(epsilon);

        double sinDec = Math.sin(epsilonRad) * Math.sin(lambdaRad);
        double dec = Math.toDegrees(Math.asin(sinDec));

        double y = Math.cos(epsilonRad) * Math.sin(lambdaRad);
        double x = Math.cos(lambdaRad);
        double ra = Math.toDegrees(Math.atan2(y, x)) / 15.0; // hours

        if (ra < 0) ra += 24.0;

        return new double[]{ra, dec};
    }

    /**
     * Returns true if the Sun is below -18° altitude (astronomical night)
     */
    public static boolean isSunBelow18(ZonedDateTime timeUTC, double latitudeDeg, double longitudeDeg) {
        double[] sun = sunRaDec(timeUTC);
        double raHours = sun[0];
        double decDeg = sun[1];

        double altitude = getAltitude(timeUTC, latitudeDeg, longitudeDeg, raHours, decDeg);

        return altitude < -18.0;
    }


    public static Optional<ZonedDateTime[]> visibilityWindowForMonth(
            double latitudeDeg,
            double longitudeDeg,
            double raHours,
            double decDeg,
            Month month) {

        ZonedDateTime now = ZonedDateTime.now(ZoneOffset.UTC);

        // Determine year: if requested month already passed, use next year
        int yearToUse = (now.getMonthValue() > month.getValue())
                ? now.getYear() + 1
                : now.getYear();

        ZonedDateTime start = ZonedDateTime.of(
                yearToUse, month.getValue(), 1, 0, 0, 0, 0, ZoneOffset.UTC);

        ZonedDateTime end = start.plusMonths(1).minusSeconds(1);

        Duration coarseStep = Duration.ofMinutes(20);
        Duration fineStep = Duration.ofMinutes(1);
        double minAlt = 0.0;

        boolean foundWindow = false;
        ZonedDateTime coarseHit = null;

        // --- COARSE SEARCH ---
        ZonedDateTime t = start;
        while (!t.isAfter(end)) {
            double alt = getAltitude(t, latitudeDeg, longitudeDeg, raHours, decDeg);
            boolean dark = isSunBelow18(t, latitudeDeg, longitudeDeg);

            if (alt >= minAlt && dark) {
                coarseHit = t;
                foundWindow = true;
                break;
            }

            t = t.plus(coarseStep);
        }

        if (!foundWindow) {
            return Optional.empty();
        }

        // --- FINE SEARCH BACKWARD ---
        ZonedDateTime windowStart = coarseHit;
        while (true) {
            ZonedDateTime prev = windowStart.minus(fineStep);
            if (prev.isBefore(start)) break;

            double alt = getAltitude(prev, latitudeDeg, longitudeDeg, raHours, decDeg);
            boolean dark = isSunBelow18(prev, latitudeDeg, longitudeDeg);

            if (alt < minAlt || !dark) break;

            windowStart = prev;
        }

        // --- FINE SEARCH FORWARD ---
        ZonedDateTime windowEnd = coarseHit;
        while (true) {
            ZonedDateTime next = windowEnd.plus(fineStep);
            if (next.isAfter(end)) break;

            double alt = getAltitude(next, latitudeDeg, longitudeDeg, raHours, decDeg);
            boolean dark = isSunBelow18(next, latitudeDeg, longitudeDeg);

            if (alt < minAlt || !dark) break;

            windowEnd = next;
        }

        return Optional.of(new ZonedDateTime[]{ windowStart, windowEnd });
    }

}
