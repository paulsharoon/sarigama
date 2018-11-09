package com.sarigama.task;

import java.util.Calendar;
import java.util.GregorianCalendar;

public class DateProcessor {
    
    Long time = null ;
    int year = -1 ;
    int month = -1;
    int date = -1 ;
    int hour = 0 ;
    int minute = 0 ;
    int second = 0 ;

    public void setSecond(int second) {
        this.second = second;
    }

    public DateProcessor( int year , int month , int date ) throws Exception{
        this.year = year ;
        this.month = month ;
        this.date = date ;
        this.generateTime() ;
    }

    public DateProcessor( int year , int month , int date , int hour , int minute , int second ) throws Exception{
        this.year = year ;
        this.month = month ;
        this.date = date ;

        this.hour = hour ;
        this.minute = minute ;
        this.second = second ;
        this.generateTime() ;
    }

    public DateProcessor( Long time ) throws Exception{
        this.time = time ;
        generateDate() ;
    }

    public void generateTime() throws Exception{
        if( this.year != -1 && this.month != -1 && this.date != -1){
            Calendar calender = GregorianCalendar.getInstance();
            calender.set(this.year, this.month, this.date );
            calender.set(Calendar.HOUR_OF_DAY, this.hour );
            calender.set(Calendar.MINUTE, this.minute );
            calender.set(Calendar.SECOND, this.second );
            this.time = calender.getTimeInMillis() ;
        }
    }

    public void generateDate() throws Exception {
        if( time != null ){
            Calendar calender = GregorianCalendar.getInstance();
            calender.setTimeInMillis( this.time );

            this.year = calender.get( Calendar.YEAR ) ;
            this.month = calender.get( Calendar.MONTH ) ;
            this.date = calender.get( Calendar.DATE ) ;
            this.hour = calender.get( Calendar.HOUR_OF_DAY ) ;
            this.minute = calender.get( Calendar.MINUTE ) ;
            this.second = calender.get( Calendar.SECOND ) ; 
        }
    }

    public Long getTime() {
        return this.time;
    }

    public void setTime(Long time) {
        this.time = time;
    }

    public int getYear() {
        return this.year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getMonth() {
        return this.month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public int getDate() {
        return this.date;
    }

    public void setDate(int date) {
        this.date = date;
    }

    public int getHour() {
        return this.hour;
    }

    public void setHour(int hour) {
        this.hour = hour;
    }

    public int getMinute() {
        return this.minute;
    }

    public void setMinute(int minute) {
        this.minute = minute;
    }

    public int getSecond() {
        return this.second;
    }

    @Override
    public String toString() {
        return "{" +
            " time='" + getTime() + "'" +
            ", year='" + getYear() + "'" +
            ", month='" + getMonth() + "'" +
            ", date='" + getDate() + "'" +
            ", hour='" + getHour() + "'" +
            ", minute='" + getMinute() + "'" +
            ", second='" + getSecond() + "'" +
            "}";
    }

}