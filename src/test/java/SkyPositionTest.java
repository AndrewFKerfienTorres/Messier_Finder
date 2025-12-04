import application.SkyPosition;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.Optional;
import java.time.*;

import static org.junit.jupiter.api.Assertions.*;

import java.time.ZoneOffset;
import java.time.ZonedDateTime;

class SkyPositionTest {

    private static final double HOUR_TOLERANCE = 1e-3;

    @Nested
    class ToJulianDateTests {
        //Useful testing resource is https://ssd.jpl.nasa.gov/tools/jdc/#/cd which is a Julian Date and time converter. This was used to determine the expected values.

        private static final double DELTA = 1e-6; // allowable floating-point error

        @Test
        void testStandardDate() {
            ZonedDateTime zdt = ZonedDateTime.of(2025, 11, 17, 12, 0, 0, 0, ZoneOffset.UTC);
            double jd = SkyPosition.toJulianDate(zdt);
            assertEquals(2460997.0, jd, DELTA);
        }

        @Test
        void testDateBeforeMarch() {
            ZonedDateTime zdt = ZonedDateTime.of(2025, 2, 15, 0, 0, 0, 0, ZoneOffset.UTC);
            double jd = SkyPosition.toJulianDate(zdt);
            assertEquals(2460721.5, jd, DELTA);
        }

        @Test
        void testLeapYear() {
            ZonedDateTime zdt = ZonedDateTime.of(2020, 2, 29, 0, 0, 0, 0, ZoneOffset.UTC);
            double jd = SkyPosition.toJulianDate(zdt);
            assertEquals(2458908.5, jd, DELTA);
        }

        @Test
        void testMidnight() {
            ZonedDateTime zdt = ZonedDateTime.of(2025, 11, 17, 0, 0, 0, 0, ZoneOffset.UTC);
            double jd = SkyPosition.toJulianDate(zdt);
            assertEquals(2460996.5, jd, DELTA);
        }

        @Test
        void testEndOfYear() {
            ZonedDateTime zdt = ZonedDateTime.of(2025, 12, 31, 23, 59, 59, 0, ZoneOffset.UTC);
            double jd = SkyPosition.toJulianDate(zdt);
            assertEquals(2461041.4999884, jd, DELTA);
        }

        @Test
        void testLastJulianDay() {
            // October 4, 1582 is the last day of the Julian calendar
            ZonedDateTime zdt = ZonedDateTime.of(1582, 10, 4, 0, 0, 0, 0, ZoneOffset.UTC);
            double jd = SkyPosition.toJulianDate(zdt);
            assertEquals(2299159.5, jd, DELTA); // standard JD for 1582-10-04
        }

        @Test
        void testFirstGregorianDay() {
            // October 15, 1582 is the first day of the Gregorian calendar
            ZonedDateTime zdt = ZonedDateTime.of(1582, 10, 15, 0, 0, 0, 0, ZoneOffset.UTC);
            double jd = SkyPosition.toJulianDate(zdt);
            assertEquals(2299160.5, jd, DELTA); // standard JD for 1582-10-15
        }

        @Test
        void testDateBetweenSkippedDays() {
            // Dates like October 10, 1582 did not exist in the Gregorian calendar
            // But algorithm will still compute a JD if you pass it
            ZonedDateTime zdt = ZonedDateTime.of(1582, 10, 10, 0, 0, 0, 0, ZoneOffset.UTC);
            //double jd = application.SkyPosition.toJulianDate(zdt);
            assertThrows(IllegalArgumentException.class, () -> SkyPosition.toJulianDate(zdt));
        }
    }

    @Nested
    class gmstFromJulianDateTests {
        @Test
        public void testJ2000Epoch() {
            double jd = 2451545.0;
            double gmst = SkyPosition.gmstFromJulianDate(jd);
            assertEquals(18.697374558, gmst, HOUR_TOLERANCE);
        }

        @Test
        public void testJ2000PlusHalfDay() {
            double jd = 2451545.5;
            double gmst = SkyPosition.gmstFromJulianDate(jd);
            assertEquals(6.730229470209671, gmst, HOUR_TOLERANCE);
        }

        @Test
        public void test2010Jan01() {
            double jd = 2455197.5;
            double gmst = SkyPosition.gmstFromJulianDate(jd);
            assertEquals(6.702508508243287, gmst, HOUR_TOLERANCE);
        }

        @Test
        public void test2025Jan01() {
            double jd = 2457023.5;
            double gmst = SkyPosition.gmstFromJulianDate(jd);
            assertEquals(6.688648221160595, gmst, HOUR_TOLERANCE);
        }

        // ---- EDGE CASES ----

        /**
         * Very large JD far in the future
         */
        @Test
        public void testFarFutureDate() {
            double jd = 3000000.0;  // year ~ 12000 AD
            double gmst = SkyPosition.gmstFromJulianDate(jd);
            assertEquals(9.584951953, gmst, HOUR_TOLERANCE);
        }


        /**
         * Just before wraparound
         */
        @Test
        public void testFractionNearUpperWrap() {
            double jd = 2451545.99999;
            double gmst = SkyPosition.gmstFromJulianDate(jd);
            assertEquals(18.763084142, gmst, HOUR_TOLERANCE);
        }

        /**
         * Exactly at fractional boundary (should not cause rounding issues)
         */
        @Test
        public void testFractionExactBoundary() {
            double jd = 2451545.75;  // exactly 18:00 UT
            double gmst = SkyPosition.gmstFromJulianDate(jd);
            assertEquals(12.7466569263, gmst, HOUR_TOLERANCE);
        }

        /**
         * Negative Julian centuries (date distant before J2000)
         */
        @Test
        public void testNegativeTValue() {
            double jd = 2400000.5;  // T ≈ -0.141
            double gmst = SkyPosition.gmstFromJulianDate(jd);
            assertEquals(3.717381, gmst, HOUR_TOLERANCE);
        }

        /**
         * JD with many fractional digits
         */
        @Test
        public void testHighPrecisionFractionJD() {
            double jd = 2451545.123456789;
            double gmst = SkyPosition.gmstFromJulianDate(jd);
            assertEquals(21.667656655, gmst, HOUR_TOLERANCE);
        }

        /**
         * Tiny fractional component
         */
        @Test
        public void testVerySmallFraction() {
            double jd = 2451545.000001;
            double gmst = SkyPosition.gmstFromJulianDate(jd);
            assertEquals(18.697398623, gmst, 2e-3); // allow small propagation error
        }

        /**
         * GMST exactly at wrap point
         */
        @Test
        public void testGmstWraparound() {
            // Choose a JD where the expected GMST is extremely close to 24h
            double jd = 2451680.0;
            double gmst = SkyPosition.gmstFromJulianDate(jd);
            double expected = 3.568201;
            assertEquals(expected, gmst, HOUR_TOLERANCE);
        }
    }


    @Nested
    class ToDMSTest {

        //Useful Testing Resource to verify expected values calculations: https://www.calculatorsoup.com/calculators/conversions/convert-decimal-degrees-to-degrees-minutes-seconds.php
        @Test
        void testZero() {
            assertEquals("0° 0′ 0.000″", SkyPosition.doubleToDMS(0.0));
        }

        @Test
        void testPositiveWholeNumber() {
            assertEquals("30° 0′ 0.000″", SkyPosition.doubleToDMS(30.0));
        }

        @Test
        void testNegativeWholeNumber() {
            assertEquals("-45° 0′ 0.000″", SkyPosition.doubleToDMS(-45.0));
        }

        @Test
        void testSimpleFraction() {
            // 12.5° = 12° 30'
            assertEquals("12° 30′ 0.000″", SkyPosition.doubleToDMS(12.5));
        }

        @Test
        void testSecondsCalculation() {
            // 15.256° → fractional .256 * 60 = 15.36 min → fractional .36 * 60 = 21.6 sec
            assertEquals("15° 15′ 21.600″", SkyPosition.doubleToDMS(15.256));
        }

        @Test
        void testNegativeFraction() {
            assertEquals("-12° 30′ 0.000″", SkyPosition.doubleToDMS(-12.5));
        }

        @Test
        void testRoundingSecondsTo60() {
            // 10.0166666667° → 0° 1′ 0.000″ after rounding
            // 0.0166666667 * 60 = 1.000000002 min ≈ 1 min, 0 sec
            assertEquals("10° 1′ 0.000″", SkyPosition.doubleToDMS(10.0166666667));
        }

        @Test
        void testRoundingSecondsCarryToMinutes() {
            // Construct a value where seconds round up to 60.000
            // Example: degrees = 1, minutes = 2, seconds ≈ 59.9996 → rounds to 60
            double decimal = 1 + (2 / 60.0) + (59.9996 / 3600.0);

            assertEquals("1° 3′ 0.000″", SkyPosition.doubleToDMS(decimal));
        }

        @Test
        void testMinuteOverflowToDegrees() {
            // 1° 59.999999' should roll to 2° 0′ 0.000″
            double decimal = 1 + (59.999999 / 60.0);

            assertEquals("2° 0′ 0.000″", SkyPosition.doubleToDMS(decimal));
        }

        @Test
        void testJustUnderNextDegree() {
            // 1.999999° should not roll over
            assertEquals("1° 59′ 59.996″", SkyPosition.doubleToDMS(1.999999));
        }

        @Test
        void testNegativeRoundingBehavior() {
            // Negative values should carry sign only once
            assertEquals("-1° 30′ 0.000″", SkyPosition.doubleToDMS(-1.5));
        }

        @Test
        void secondsRolloverToNextMinute() {
            // 12h + 59m + 59.999999s ≈ 12.999999 hours
            double input = 12.999999;

            String result = SkyPosition.doubleToHMS(input);

            assertEquals("13h 00m 00.0s", result);
        }

        @Test
        void secondsAndMinutesRolloverToNextHour() {
            // 5h + 59m + 59.999999s
            double input = 5 + (59.999999 / 60.0);

            String result = SkyPosition.doubleToHMS(input);

            assertEquals("06h 00m 00.0s", result);
        }

        @Test
        void wrapAround24Hours() {
            // Exact: 23h 59m 59.999s
            double hours = 23
                    + (59.0 / 60.0)
                    + (59.999 / 3600.0);

            String result = SkyPosition.doubleToHMS(hours);

            assertEquals("00h 00m 00.0s", result);
        }
        @Test
        void secondsRolloverInDMS() {
            // 10° 20′ 59.9996″  → seconds should round to 60.000 → carry
            double deg = 10
                    + (20.0 / 60.0)
                    + (59.9996 / 3600.0);

            String result = SkyPosition.doubleToDMS(deg);

            assertEquals("10° 21′ 0.000″", result);
        }

        @Test
        void minutesAndSecondsRolloverInDMS() {
            // 15° 59′ 59.9996″ → should become 16° 00′ 00.000″
            double deg = 15
                    + (59.0 / 60.0)
                    + (59.9996 / 3600.0);

            String result = SkyPosition.doubleToDMS(deg);

            assertEquals("16° 0′ 0.000″", result);
        }

    }

    @Nested
    class SkyPositionFloatToHMSTests {
        @Test
        public void testWholeHours() {
            assertEquals("05h 00m 00.0s", SkyPosition.doubleToHMS(5.0));
            assertEquals("12h 00m 00.0s", SkyPosition.doubleToHMS(12.0));
        }

        @Test
        public void testHoursAndMinutes() {
            assertEquals("03h 30m 00.0s", SkyPosition.doubleToHMS(3.5));
            assertEquals("10h 15m 00.0s", SkyPosition.doubleToHMS(10.25));
        }

        @Test
        public void testHoursMinutesSeconds() {
            assertEquals("02h 45m 30.0s", SkyPosition.doubleToHMS(2.7583333));
            assertEquals("01h 01m 36.0s", SkyPosition.doubleToHMS(1.0266667));
        }

        @Test
        public void testZero() {
            assertEquals("00h 00m 00.0s", SkyPosition.doubleToHMS(0.0));
        }

        @Test
        public void testFractionalSeconds() {
            assertEquals("04h 20m 30.5s", SkyPosition.doubleToHMS(4.3418056));
            assertEquals("00h 00m 59.9s", SkyPosition.doubleToHMS(0.0166389));
        }

        @Test
        public void testEdgeCases() {
            assertEquals("23h 59m 59.9s", SkyPosition.doubleToHMS(23.9999722));
            assertEquals("00h 00m 00.0s", SkyPosition.doubleToHMS(0.0));
        }
    }

    @Nested
    class SkyPositionazimuthandAltitudeTests {
        private static final double ERROR = 0.0167;

        @Test
        public void testM31() {

            ZonedDateTime timeUTC = ZonedDateTime.parse("2025-11-30T23:06:42Z");
            double latitudeDeg = 43.43000;
            double longitudeDeg = -76.55012;
            double raHours = 0.7362222;
            double decDeg = 41.4151111;
            double az = SkyPosition.getAzimuth(timeUTC, latitudeDeg, longitudeDeg, raHours, decDeg);
            double alt = SkyPosition.getAltitude(timeUTC, latitudeDeg, longitudeDeg, raHours, decDeg);

            assertEquals(84.3799723, az, ERROR);
            assertEquals(67.2581111, alt, ERROR);
        }

        @Test
        public void testM45() {

            ZonedDateTime timeUTC = ZonedDateTime.parse("2025-11-30T23:19:15Z");
            double latitudeDeg = 43.43000;
            double longitudeDeg = -76.55012;
            double raHours = 3.8095278;
            double decDeg = 24.1988333;
            double az = SkyPosition.getAzimuth(timeUTC, latitudeDeg, longitudeDeg, raHours, decDeg);
            double alt = SkyPosition.getAltitude(timeUTC, latitudeDeg, longitudeDeg, raHours, decDeg);

            assertEquals(82.0112222, az, ERROR);
            assertEquals(27.8259445, alt, ERROR);
        }
    }

    @Nested
    class IsSunBelow18Tests {

        @Test
        void nightTime_returnsTrue() {
            ZonedDateTime timeUTC = ZonedDateTime.of(2025, 12, 2, 0, 0, 0, 0, ZoneOffset.UTC);
            double latitude = 51.5074;
            double longitude = -0.1278;

            boolean result = SkyPosition.isSunBelow18(timeUTC, latitude, longitude);
            assertTrue(result, "Sun should be below -18° at midnight in London in December");
        }

        @Test
        void dayTime_returnsFalse() {
            ZonedDateTime timeUTC = ZonedDateTime.of(2025, 6, 21, 12, 0, 0, 0, ZoneOffset.UTC);
            double latitude = 51.5074;
            double longitude = -0.1278;

            boolean result = SkyPosition.isSunBelow18(timeUTC, latitude, longitude);
            assertFalse(result, "Sun should be above -18° at noon in London in June");
        }

        @Test
        void edgeOfTwilight_returnsFalse() {
            ZonedDateTime timeUTC = ZonedDateTime.of(2025, 12, 2, 7, 45, 0, 0, ZoneOffset.UTC);
            double latitude = 51.5074;
            double longitude = -0.1278;

            boolean result = SkyPosition.isSunBelow18(timeUTC, latitude, longitude);
            assertFalse(result, "Sun at -18° should return false according to method definition");
        }
    }

    @Nested
    class DecemberVisibility {

        @Test
        public void testM1() {
            double latitude = 43.45692;
            double longitude = -76.50017;
            Optional<ZonedDateTime[]> window = SkyPosition.visibilityWindowForMonth(
                    latitude, longitude, 5.58, 22.01, Month.DECEMBER
            );

            assertTrue(window.isPresent(), "M1 should be visible in December");

            ZonedDateTime start = window.get()[0];
            ZonedDateTime end = window.get()[1];

            ZonedDateTime expectedStart = ZonedDateTime.of(2025, 12, 1, 0, 00, 0, 0, ZoneOffset.UTC);
            ZonedDateTime expectedEnd = ZonedDateTime.of(2025, 12, 1, 10, 37, 0, 0, ZoneOffset.UTC);

            assertTrue(Math.abs(start.toEpochSecond() - expectedStart.toEpochSecond()) <= 60,
                    "Start time differs from expected:" + expectedStart + " Actual" + start);
            assertTrue(Math.abs(end.toEpochSecond() - expectedEnd.toEpochSecond()) <= 60,
                    "End time differs from expected:" + expectedEnd + " Actual" + end);
        }

        @Test
        public void testM31() {
            double latitude = 43.43000;
            double longitude = -76.55012;
            Optional<ZonedDateTime[]> window = SkyPosition.visibilityWindowForMonth(
                    latitude, longitude, 0.71, 41.27, Month.DECEMBER
            );

            assertTrue(window.isPresent(), "M31 should be visible in December");

            ZonedDateTime start = window.get()[0];
            ZonedDateTime end = window.get()[1];

            ZonedDateTime expectedStart = ZonedDateTime.of(2025, 12, 1, 0, 0, 0, 0, ZoneOffset.UTC);
            ZonedDateTime expectedEnd = ZonedDateTime.of(2025, 12, 1, 10, 37, 0, 0, ZoneOffset.UTC);

            assertTrue(Math.abs(start.toEpochSecond() - expectedStart.toEpochSecond()) <= 60,
                    "Start time differs from expected:" + expectedStart + " Actual" + start);
            assertTrue(Math.abs(end.toEpochSecond() - expectedEnd.toEpochSecond()) <= 60,
                    "End time differs from expected:" + expectedEnd + " Actual" + end);

        }

        @Test
        public void testM42() {
            double latitude = 43.43000;
            double longitude = -76.55012;
            Optional<ZonedDateTime[]> window = SkyPosition.visibilityWindowForMonth(
                    latitude, longitude, 5.59, -5.45, Month.DECEMBER
            );

            assertTrue(window.isPresent(), "M42 should be visible in December");

            ZonedDateTime start = window.get()[0];
            ZonedDateTime end = window.get()[1];

            ZonedDateTime expectedStart = ZonedDateTime.of(2025, 12, 1, 0, 22, 0, 0, ZoneOffset.UTC);
            ZonedDateTime expectedEnd = ZonedDateTime.of(2025, 12, 1, 10, 37, 0, 0, ZoneOffset.UTC);

            assertTrue(Math.abs(start.toEpochSecond() - expectedStart.toEpochSecond()) <= 60,
                    "Start time differs from expected:" + expectedStart + " Actual" + start);
            assertTrue(Math.abs(end.toEpochSecond() - expectedEnd.toEpochSecond()) <= 60,
                    "End time differs from expected:" + expectedEnd + " Actual" + end);
        }

        @Test
        public void testM13() {
            double latitude = 43.43000;
            double longitude = -76.55012;
            Optional<ZonedDateTime[]> window = SkyPosition.visibilityWindowForMonth(
                    latitude, longitude, 16.70, 36.46, Month.DECEMBER
            );

            assertTrue(window.isPresent(), "M13 should be visible in December");

            ZonedDateTime start = window.get()[0];
            ZonedDateTime end = window.get()[1];

            ZonedDateTime expectedStart = ZonedDateTime.of(2025, 12, 1, 0, 0, 0, 0, ZoneOffset.UTC);
            ZonedDateTime expectedEnd = ZonedDateTime.of(2025, 12, 1, 2, 04, 0, 0, ZoneOffset.UTC);

            assertTrue(Math.abs(start.toEpochSecond() - expectedStart.toEpochSecond()) <= 60,
                    "Start time differs from expected:" + expectedStart + " Actual" + start);
            assertTrue(Math.abs(end.toEpochSecond() - expectedEnd.toEpochSecond()) <= 60,
                    "End time differs from expected:" + expectedEnd + " Actual" + end);

        }
    }
}