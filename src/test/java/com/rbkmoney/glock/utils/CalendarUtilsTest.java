package com.rbkmoney.glock.utils;

import com.rbkmoney.glock.calendar.DefaultWorkingDayCalendar;
import org.junit.Test;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static org.junit.Assert.*;

/**
 * Created by inalarsanukaev on 13.11.17.
 */
public class CalendarUtilsTest {
    @Test
    public void minusWorkDays() throws Exception {
        LocalDate date = LocalDate.parse("2017-11-13", DateTimeFormatter.ISO_DATE);
        LocalDate minusOneDay = CalendarUtils.minusWorkDays(new DefaultWorkingDayCalendar(), date, 1);
        LocalDate expected = LocalDate.parse("2017-11-10", DateTimeFormatter.ISO_DATE);
        assertEquals(minusOneDay, expected);
    }

    @Test
    public void plusWorkDays() throws Exception {
        LocalDate date = LocalDate.parse("2017-11-17", DateTimeFormatter.ISO_DATE);
        LocalDate plusOneDay = CalendarUtils.plusWorkDays(new DefaultWorkingDayCalendar(), date, 1);
        LocalDate expected = LocalDate.parse("2017-11-20", DateTimeFormatter.ISO_DATE);
        assertEquals(plusOneDay, expected);
    }

}
