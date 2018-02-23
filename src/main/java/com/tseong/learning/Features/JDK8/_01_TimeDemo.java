package com.tseong.learning.Features.JDK8;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.time.temporal.ChronoField;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.Locale;

public class _01_TimeDemo {

    public static void main(String[] args) {
        clock();
        localDateTime();
        timezones();
    }

    public static void clock() {
        Clock clock = Clock.systemDefaultZone();
        long milis = clock.millis();

        Instant instant = clock.instant();
        Date legacyDate = Date.from(instant);

        Date date2 = Date.from(Clock.systemDefaultZone().instant());

        System.out.println(legacyDate);
        System.out.println(date2);
    }

    public static void localDateTime() {
        LocalDateTime timePoint = LocalDateTime.now();
        System.out.println(timePoint);

        LocalDate date1 = LocalDate.of(2014, Month.OCTOBER, 12);
        LocalTime time1 = LocalTime.of(12, 30, 59);
        LocalTime time2 = LocalTime.parse("10:15:23");

        System.out.println(time1);
    }

    public static void timezones() {
        System.out.println(ZoneId.getAvailableZoneIds());

        ZoneId zone1 = ZoneId.of("Europe/Berlin");
        ZoneId zone2 = ZoneId.of("Brazil/East");
        System.out.println(zone1.getRules());
        System.out.println(zone2.getRules());

        LocalTime now1 = LocalTime.now(zone1);
        LocalTime now2 = LocalTime.now(zone2);

        System.out.println(now1.isBefore(now2));

        long hoursBetween = ChronoUnit.HOURS.between(now1, now2);
        long minutesBetween = ChronoUnit.MINUTES.between(now1, now2);

        System.out.println("Hours between : " + hoursBetween);
        System.out.println("Minutes between : " + minutesBetween);

        LocalTime late = LocalTime.of(23, 59, 59);
        System.out.println(late);

        DateTimeFormatter germanFormatter = DateTimeFormatter
                .ofLocalizedTime(FormatStyle.SHORT)
                .withLocale(Locale.GERMAN);
        LocalTime leetTime = LocalTime.parse("13:37", germanFormatter);
        System.out.println(leetTime);

        LocalDate today = LocalDate.now();
        LocalDate tomorrow = today.plus(1, ChronoUnit.DAYS);
        LocalDate yesterday = tomorrow.minusDays(2);

        LocalDate independenceDay = LocalDate.of(2014, Month.JULY, 2);
        DayOfWeek dayOfWeek = independenceDay.getDayOfWeek();
        System.out.println(dayOfWeek);
        System.out.println(independenceDay.getMonth());

        LocalDateTime now = LocalDateTime.now();
        System.out.println(now.getLong(ChronoField.MINUTE_OF_DAY));


        Instant instant = now.atZone(ZoneId.systemDefault()).toInstant();
        Date legacyDate = Date.from(instant);
        System.out.println(legacyDate);


        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM dd, yyyy - HH:mm");
        LocalDateTime parsed = LocalDateTime.parse("12 03, 2014 - 07:13", formatter);
        String string = formatter.format(parsed);
        System.out.println(string);     // Nov 03, 2014 - 07:13

        OffsetDateTime nowOff = OffsetDateTime.now();
        String offLabel = nowOff.toString();
        System.out.println(offLabel);

    }
}
