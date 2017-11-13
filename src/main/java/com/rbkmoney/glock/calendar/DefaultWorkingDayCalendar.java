package com.rbkmoney.glock.calendar;

import au.com.bytecode.opencsv.CSVReader;
import com.rbkmoney.glock.utils.CalendarUtils;

import java.io.IOException;
import java.io.InputStreamReader;
import java.time.LocalDate;
import java.util.*;

/**
 * Created by inalarsanukaev on 13.11.17.
 */
public class DefaultWorkingDayCalendar implements WorkingDayCalendar {

    private static final String DEFAULT_FILE_PATH = "/data/calendar.csv";

    private Map<Integer, Map<Integer, List<Integer>>> calendar;
    private String filePath;

    public DefaultWorkingDayCalendar(String filePath) {
        this.filePath = filePath;
        init();
    }

    public DefaultWorkingDayCalendar() {
        this(DEFAULT_FILE_PATH);
    }

    private void init() {
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

    @Override
    public boolean isWorkingDay(LocalDate localDate) {
        return !isHoliday(localDate);
    }

    @Override
    public boolean isHoliday(LocalDate localDate) {
        try {
            return Optional.of(calendar.get(localDate.getYear()))
                    .map(months -> months.get(localDate.getMonthValue()))
                    .map(days -> days.contains(localDate.getDayOfMonth()))
                    .orElse(false);
        } catch (NullPointerException ex) {
            throw new IllegalArgumentException(String.format("Failed to process date, date='%s'", localDate), ex);
        }
    }
}
