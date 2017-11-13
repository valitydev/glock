package com.rbkmoney.glock.utils;

import au.com.bytecode.opencsv.CSVReader;
import com.rbkmoney.glock.model.TimeRange;

import java.io.IOException;
import java.io.InputStreamReader;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;
import java.util.*;

public class CalendarUtils {

    private static Map<Integer, Map<Integer, List<Integer>>> calendar;
    private static final String filePath = "/data/calendar.csv";

    static  {
        init();
    }

    private static void init() {
        Map<Integer, Map<Integer, List<Integer>>> years = new HashMap<>();

        try (CSVReader reader = new CSVReader(new InputStreamReader(CalendarUtils.class.getResourceAsStream(filePath)))) {
            //Skip first row
            String[] row = reader.readNext();

            try {
                while ((row = reader.readNext()) != null) {
                    Map<Integer, List<Integer>> months = new HashMap<>();
                    for (int month = 1; month <= 12; month++) {
                        List<Integer> days = new ArrayList<>();
                        for (String day : row[month].split(",")) {
                            if (!day.endsWith("*")) {
                                days.add(Integer.valueOf(day));
                            }
                        }
                        months.put(month, days);
                    }
                    int year = Integer.valueOf(row[0]);
                    years.put(year, months);
                }
            } catch (Exception ex) {
                throw new RuntimeException(String.format("Failed to read calendar from csv file, row='%s'", Arrays.toString(row)), ex);
            }
        } catch (IOException e) {
            throw new RuntimeException("Failed to init calendar", e);
        }
        calendar = Collections.unmodifiableMap(years);
    }

    public static boolean isWorkingDay(LocalDate localDate) {
        return !isHoliday(localDate);
    }

    public static boolean isHoliday(LocalDate localDate) {
        try {
            return Optional.of(calendar.get(localDate.getYear()))
                    .map(months -> months.get(localDate.getMonthValue()))
                    .map(days -> days.contains(localDate.getDayOfMonth()))
                    .orElse(false);
        } catch (NullPointerException ex) {
            throw new IllegalArgumentException(String.format("Failed to process date, date='%s'", localDate), ex);
        }
    }

    public static TimeRange buildTimeRange(ZonedDateTime day, int delayDays) {
        ZonedDateTime today = day.truncatedTo(ChronoUnit.DAYS); //today 0:00

        ZonedDateTime from = minusWorkDays(today, delayDays);
        ZonedDateTime to = countTo(from);
        //get time in UTC
        LocalDateTime fromTime = from.withZoneSameInstant(ZoneOffset.UTC).toLocalDateTime();
        LocalDateTime toTime = to.withZoneSameInstant(ZoneOffset.UTC).toLocalDateTime();

        TimeRange timeRange = new TimeRange(fromTime, toTime);
        return timeRange;
    }

    private static ZonedDateTime minusWorkDays(ZonedDateTime today, int delayDays) {
        ZonedDateTime day = today;
        for (int i = 0; i < delayDays; i++) {
            day = day.minusDays(1);
            while (isHoliday(day.toLocalDate())) {
                day = day.minusDays(1);
            }
        }
        return day;
    }

    private static ZonedDateTime countTo(ZonedDateTime from) {
        ZonedDateTime to = from.plusDays(1);

        while (isHoliday(to.toLocalDate())) {
            to = to.plusDays(1);
        }
        return to;
    }
}
