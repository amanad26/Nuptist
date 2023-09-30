package com.Nuptist.Models;

import java.util.Calendar;

public class BookedModel {
    Calendar calendar ;
    String date ;


    public BookedModel(Calendar calendar, String date) {
        this.calendar = calendar;
        this.date = date;
    }

    public BookedModel() {
    }

    public Calendar getCalendar() {
        return calendar;
    }

    public void setCalendar(Calendar calendar) {
        this.calendar = calendar;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
