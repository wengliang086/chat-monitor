package com.hoolai.chatmonitor.open.utils;

import java.util.Date;

public class DateRange {

    private Date start;

    private Date end;

    public DateRange(Date startDate, Date endDate) {
        this.start = startDate;
        this.end = endDate;
    }

    public Date getStart() {
        return start;
    }

    public void setStart(Date start) {
        this.start = start;
    }

    public Date getEnd() {
        return end;
    }

    public void setEnd(Date end) {
        this.end = end;
    }

    public String getStartDate() {
        return DateUtil.format(start, "yyyy-MM-dd");
    }

    public String getStartDateTime() {
        return DateUtil.format(start, "yyyy-MM-dd HH:mm:ss");
    }

    public String getEndDate() {
        return DateUtil.format(end, "yyyy-MM-dd");
    }

    public String getEndDateTime() {
        return DateUtil.format(end, "yyyy-MM-dd HH:mm:ss");
    }


}
