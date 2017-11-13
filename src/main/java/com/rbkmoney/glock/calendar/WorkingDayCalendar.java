package com.rbkmoney.glock.calendar;

import java.time.LocalDate;

/**
 * Created by inalarsanukaev on 13.11.17.
 */
public interface WorkingDayCalendar {
    boolean isWorkingDay(LocalDate localDate);
    boolean isHoliday(LocalDate localDate);
}
