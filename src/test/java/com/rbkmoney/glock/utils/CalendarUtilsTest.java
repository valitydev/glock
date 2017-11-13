package com.rbkmoney.glock.utils;

import org.junit.Test;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static org.junit.Assert.*;

/**
 * Created by inalarsanukaev on 13.11.17.
 */
public class CalendarUtilsTest {
    @Test
    public void isWorkingDay() throws Exception {
        assertTrue(CalendarUtils.isWorkingDay(LocalDate.parse("2017-11-13", DateTimeFormatter.ISO_DATE)));
    }

    @Test
    public void isHoliday() throws Exception {
        assertTrue(CalendarUtils.isHoliday(LocalDate.parse("2017-11-04", DateTimeFormatter.ISO_DATE)));
    }
}
