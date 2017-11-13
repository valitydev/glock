package com.rbkmoney.glock.utils;

import com.rbkmoney.glock.calendar.DefaultWorkingDayCalendar;
import com.rbkmoney.glock.calendar.WorkingDayCalendar;

import java.time.LocalDate;

public class CalendarUtils {

    private static final WorkingDayCalendar calendar = new DefaultWorkingDayCalendar();

    public static LocalDate minusWorkDays(LocalDate today, int delayDays) {
        LocalDate day = today;
        for (int i = 0; i < delayDays; i++) {
            day = day.minusDays(1);
            while (calendar.isHoliday(day)) {
                day = day.minusDays(1);
            }
        }
        return day;
    }

    public static LocalDate plusWorkDays(LocalDate today, int delayDays) {
        LocalDate day = today;
        for (int i = 0; i < delayDays; i++) {
            day = day.plusDays(1);
            while (calendar.isHoliday(day)) {
                day = day.plusDays(1);
            }
        }
        return day;
    }
}
