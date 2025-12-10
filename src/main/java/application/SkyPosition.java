package application;

import java.time.*;
import java.util.Optional;
import java.util.List;
import java.util.ArrayList;

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
        String sign = decimalDegrees < 0 ? "-" : "";
        decimalDegrees = Math.abs(decimalDegrees);

        int degrees = (int) Math.floor(decimalDegrees);
        double fractional = decimalDegrees - degrees;

        double minutesFull = fractional * 60.0;
        int minutes = (int) Math.floor(minutesFull);

        double seconds = (minutesFull - minutes) * 60.0;

        // Round seconds to 3 decimals (your desired format)
        seconds = Math.round(seconds * 1000.0) / 1000.0;

        // Carry seconds -> minutes
        if (seconds >= 60.0) {
            seconds -= 60.0;
            minutes += 1;
        }

        // Carry minutes -> degrees
        if (minutes >= 60.0) {
            minutes -= 60.0;
            degrees += 1;
        }

        return String.format("%s%d° %d′ %.3f″", sign, degrees, minutes, seconds);
    }

    public static String doubleToHMS(double hours) {
        int h = (int) Math.floor(hours);
        double remainingMinutes = (hours - h) * 60.0;
        int m = (int) Math.floor(remainingMinutes);
        double s = (remainingMinutes - m) * 60.0;

        // Round seconds to 1 decimal (matches "%04.1f")
        s = Math.round(s * 10.0) / 10.0;

        // Carry seconds -> minutes
        if (s >= 60.0) {
            s -= 60.0;
            m += 1;
        }

        // Carry minutes -> hours
        if (m >= 60) {
            m -= 60;
            h += 1;
        }

        // Wrap hours at 24 (optional, but matches earlier suggestion)
        if (h >= 24) {
            h -= 24;
        } else if (h < 0) {
            // defensive: keep in 0..23
            h = ((h % 24) + 24) % 24;
        }

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
    public static Optional<List<ZonedDateTime[]>> getYearVisibilityRanges(
            double latitudeDeg,
            double longitudeDeg,
            double raHours,
            double decDeg) {

        List<ZonedDateTime[]> ranges = new ArrayList<>();

        ZonedDateTime now = ZonedDateTime.now(ZoneOffset.UTC);
        ZonedDateTime start = ZonedDateTime.of(now.getYear(), 1, 1, 0, 0, 0, 0, ZoneOffset.UTC);
        ZonedDateTime end = start.plusYears(1).minusSeconds(1);

        ZonedDateTime rangeStart = null;
        ZonedDateTime rangeEnd = null;

        Duration step = Duration.ofMinutes(20); // reuse coarse sampling

        ZonedDateTime day = start;

        while (!day.isAfter(end)) {

            boolean dayVisible = false;

            // Scan the entire day in coarse time steps
            ZonedDateTime t = day;
            ZonedDateTime dayEnd = day.plusDays(1);

            while (!t.isAfter(dayEnd)) {

                double alt = getAltitude(t, latitudeDeg, longitudeDeg, raHours, decDeg);
                boolean dark = isSunBelow18(t, latitudeDeg, longitudeDeg);

                if (alt >= 0 && dark) {
                    dayVisible = true;
                    break;
                }

                t = t.plus(step);
            }

            if (dayVisible) {
                if (rangeStart == null) {
                    // Starting a new range
                    rangeStart = day;
                    rangeEnd = day;
                } else {
                    // Continue existing range
                    rangeEnd = day;
                }
            } else {
                if (rangeStart != null) {
                    // Close current range
                    ranges.add(new ZonedDateTime[]{
                            rangeStart,
                            rangeEnd.plusDays(1).minusSeconds(1)
                    });
                    rangeStart = null;
                    rangeEnd = null;
                }
            }

            day = day.plusDays(1);
        }

        // Close final open range if necessary
        if (rangeStart != null) {
            ranges.add(new ZonedDateTime[]{
                    rangeStart,
                    rangeEnd.plusDays(1).minusSeconds(1)
            });
        }

        // If nothing visible for the whole year
        if (ranges.isEmpty()) {
            return Optional.empty();
        }

        return Optional.of(ranges);
    }
    public static boolean isVisibleDuringMonth(
            double latitudeDeg,
            double longitudeDeg,
            double raHours,
            double decDeg,
            Month month) {

        Optional<List<ZonedDateTime[]>> optRanges =
                getYearVisibilityRanges(latitudeDeg, longitudeDeg, raHours, decDeg);

        // If the object is never visible all year
        if (optRanges.isEmpty()) {
            return false;
        }

        List<ZonedDateTime[]> ranges = optRanges.get();

        // Determine which year the month should be evaluated in
        ZonedDateTime now = ZonedDateTime.now(ZoneOffset.UTC);

        int yearToUse = (now.getMonthValue() > month.getValue())
                ? now.getYear() + 1
                : now.getYear();

        ZonedDateTime monthStart = ZonedDateTime.of(
                yearToUse, month.getValue(), 1,
                0, 0, 0, 0, ZoneOffset.UTC);

        ZonedDateTime monthEnd = monthStart.plusMonths(1).minusSeconds(1);

        // Shift visibility ranges into the year we are testing
        List<ZonedDateTime[]> shiftedRanges = new ArrayList<>();

        for (ZonedDateTime[] r : ranges) {
            ZonedDateTime start = r[0].withYear(yearToUse);
            ZonedDateTime end   = r[1].withYear(yearToUse);

            // Handle ranges that wrap past year boundary (e.g., Nov → Feb)
            if (end.isBefore(start)) {
                end = end.plusYears(1);
            }

            shiftedRanges.add(new ZonedDateTime[]{ start, end });
        }

        // Debug print of ranges being tested
        System.out.println("Visibility ranges for " + month + " " + yearToUse + ":");
        for (ZonedDateTime[] r : shiftedRanges) {
            System.out.println("  Range: " + r[0] + "  →  " + r[1]);
        }

        // CORRECT overlap test
        for (ZonedDateTime[] range : shiftedRanges) {
            ZonedDateTime rangeStart = range[0];
            ZonedDateTime rangeEnd   = range[1];

            boolean overlap =
                    rangeStart.isBefore(monthEnd) &&
                            rangeEnd.isAfter(monthStart);

            if (overlap) {
                return true;
            }
        }

        return false;
    }


}
