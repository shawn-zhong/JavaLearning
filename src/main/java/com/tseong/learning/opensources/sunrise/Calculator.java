package com.tseong.learning.opensources.sunrise;

import com.luckycatlabs.sunrisesunset.SunriseSunsetCalculator;
import com.luckycatlabs.sunrisesunset.dto.Location;

import java.time.Clock;
import java.util.Calendar;

public class Calculator {

    public static void main(String[] args) {

       // System.out.println(ZoneId.getAvailableZoneIds());
        System.out.println(Clock.systemDefaultZone().getZone().toString());

        Location location = new Location("23.1170553", "113.27599");
        SunriseSunsetCalculator calculator = new SunriseSunsetCalculator(location, "Asia/Shanghai");

        String officialSunrise = calculator.getOfficialSunriseForDate(Calendar.getInstance());
        System.out.println(officialSunrise);

        String officialSet = calculator.getOfficialSunsetForDate(Calendar.getInstance());
        System.out.println(officialSet);

        /*
        Calendar officialSunset = calculator.getOfficialSunsetCalendarForDate(Calendar.getInstance());
        System.out.println(officialSunset);*/

        String civilSunrise = calculator.getCivilSunriseForDate(Calendar.getInstance());
        System.out.println(civilSunrise);

    }
}
