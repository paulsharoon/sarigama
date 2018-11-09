package com.sarigama.task.db.tables;

import java.util.Calendar;
import java.util.GregorianCalendar;


public class TaskConfigStart {
    

    Long taskID = null ;
    Long taskStartConfigID ;
    int year = -1 ;
    int month = -1;
    int day = -1 ;
    int hour = 0 ;
    int minute = 0 ;
    int second = 0 ;

    public TaskConfigStart() {
        super() ;
    }

    public TaskConfigStart(Long taskID, Long taskStartConfigID) {
        this.taskID = taskID;
        this.taskStartConfigID = taskStartConfigID;
    }
    
    public TaskConfigStart(Long taskID, Long taskStartConfigID, int year, int month, int day, int hour, int minute, int second) {
        this.taskID = taskID;
        this.taskStartConfigID = taskStartConfigID;
        this.year = year;
        this.month = month;
        this.day = day;
        this.hour = hour;
        this.minute = minute;
        this.second = second;
    }

    public Long getTime() throws Exception{
        Long startTime = null ;
        if( this.year != -1 && this.month != -1 && this.day != -1){
            Calendar newStartCalender = GregorianCalendar.getInstance();
            newStartCalender.set(this.year, this.month, this.day );
            newStartCalender.set(Calendar.HOUR_OF_DAY, this.hour );
            newStartCalender.set(Calendar.MINUTE, this.minute );
            newStartCalender.set(Calendar.SECOND, this.second );

            startTime = newStartCalender.getTimeInMillis() ;
        }
        return startTime ;
    }

    public Long getTaskID() {
        return this.taskID;
    }

    public void setTaskID(Long taskID) {
        this.taskID = taskID;
    }

    public Long getTaskStartConfigID() {
        return this.taskStartConfigID;
    }

    public void setTaskStartConfigID(Long taskStartConfigID) {
        this.taskStartConfigID = taskStartConfigID;
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

    public int getDay() {
        return this.day;
    }

    public void setDay(int day) {
        this.day = day;
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

    public void setSecond(int second) {
        this.second = second;
    }

    @Override
    public String toString() {
        return "{" +
            " \"taskID\":" + getTaskID() + "" +
            ", \"taskStartConfigID\":" + getTaskStartConfigID() + "" +
            ", \"year\":" + getYear() + "" +
            ", \"month\":" + getMonth() + "" +
            ", \"day\":" + getDay() + "" +
            ", \"hour\":" + getHour() + "" +
            ", \"minute\":" + getMinute() + "" +
            ", \"second\":" + getSecond() + "" +
            "}";
    }


}