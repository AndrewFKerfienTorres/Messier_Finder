import application.SkyPosition;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.time.ZoneOffset;
import java.time.ZonedDateTime;

class SkyPositionTest {

    @Nested
    class ToJulianDateTests{
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
            assertEquals(2460996.5,  jd, DELTA);
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
            assertEquals(2299159.5,  jd, DELTA); // standard JD for 1582-10-04
        }

        @Test
        void testFirstGregorianDay() {
            // October 15, 1582 is the first day of the Gregorian calendar
            ZonedDateTime zdt = ZonedDateTime.of(1582, 10, 15, 0, 0, 0, 0, ZoneOffset.UTC);
            double jd = SkyPosition.toJulianDate(zdt);
            assertEquals(2299160.5 , jd, DELTA); // standard JD for 1582-10-15
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
        void gmstFromJulianDate() {
        }
    }

    @Nested
    class getAltitudeTests{
        @Test
        void getAltitude() {
        }
    }

    @Nested
    class GetAzimuthTests {

        @Test
        void getAzimuth() {
        }
    }

    @Nested
    class ToDMSTest{

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
    }


}