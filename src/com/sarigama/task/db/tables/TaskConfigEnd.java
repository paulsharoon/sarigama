package com.sarigama.task.db.tables;

import java.util.Calendar;
import java.util.GregorianCalendar;


public class TaskConfigEnd {

    Long taskID = null;
    Long taskEndConfigID ;
    int year = -1 ;
    int month = -1;
    int day = -1 ;
    int hour = 0 ;
    int minute = 0 ;
    int second = 0 ;

    public TaskConfigEnd() {
        super() ;
    }

    public TaskConfigEnd(Long taskID, Long taskEndConfigID) {
        this.taskID = taskID;
        this.taskEndConfigID = taskEndConfigID;
    }

    public TaskConfigEnd(Long taskID, Long taskEndConfigID, int year, int month, int day, int hour, int minute, int second) {
        this.taskID = taskID;
        this.taskEndConfigID = taskEndConfigID;
        this.year = year;
        this.month = month;
        this.day = day;
        this.hour = hour;
        this.minute = minute;
        this.second = second;
    }

    public Long getTime() throws Exception{
        Long endTime = null ;
        if( this.year != -1 && this.month != -1 && this.day != -1){
            Calendar newEndCalender = GregorianCalendar.getInstance();
            newEndCalender.set(this.year, this.month, this.day );
            newEndCalender.set(Calendar.HOUR_OF_DAY, this.hour );
            newEndCalender.set(Calendar.MINUTE, this.minute );
            newEndCalender.set(Calendar.SECOND, this.second );

            endTime = newEndCalender.getTimeInMillis() ;
        }
        return endTime ;
    }

    public Long getTaskID() {
        return this.taskID;
    }

    public void setTaskID(Long taskID) {
        this.taskID = taskID;
    }

    public Long getTaskEndConfigID() {
        return this.taskEndConfigID;
    }

    public void setTaskEndConfigID(Long taskEndConfigID) {
        this.taskEndConfigID = taskEndConfigID;
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
            ", \"taskEndConfigID\":" + getTaskEndConfigID() + "" +
            ", \"year\":" + getYear() + "" +
            ", \"month\":" + getMonth() + "" +
            ", \"day\":" + getDay() + "" +
            ", \"hour\":" + getHour() + "" +
            ", \"minute\":" + getMinute() + "" +
            ", \"second\":" + getSecond() + "" +
            "}";
    }


    
}