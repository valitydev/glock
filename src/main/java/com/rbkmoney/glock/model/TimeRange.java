package com.rbkmoney.glock.model;

import java.time.LocalDateTime;

/**
 * Created by jeckep on 18.08.17.
 */
public class TimeRange {
    LocalDateTime from;
    LocalDateTime to;

    public TimeRange(LocalDateTime from, LocalDateTime to) {
        this.from = from;
        this.to = to;
    }

    public LocalDateTime getFrom() {
        return from;
    }

    public void setFrom(LocalDateTime from) {
        this.from = from;
    }

    public LocalDateTime getTo() {
        return to;
    }

    public void setTo(LocalDateTime to) {
        this.to = to;
    }

    @Override
    public String toString() {
        return "TimeRange{" +
                "from=" + from +
                ", to=" + to +
                '}';
    }
}
