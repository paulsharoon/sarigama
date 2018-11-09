package com.sarigama.task.db.tables;


public class TaskConfigRun {


    Long taskID = null  ;
    Long taskRunConfigID ;
    int hour = -1 ;
    int minute = -1 ;
    int second = -1 ;

    public TaskConfigRun() {
        super() ;
    }

    public TaskConfigRun(Long taskID, Long taskRunConfigID) {
        this.taskID = taskID;
        this.taskRunConfigID = taskRunConfigID;
    }
    
    public TaskConfigRun(Long taskID, Long taskRunConfigID, int hour, int minute, int second) {
        this.taskID = taskID;
        this.taskRunConfigID = taskRunConfigID;
        this.hour = hour;
        this.minute = minute;
        this.second = second;
    }

    public Long getTaskID() {
        return this.taskID;
    }

    public void setTaskID(Long taskID) {
        this.taskID = taskID;
    }

    public Long getTaskRunConfigID() {
        return this.taskRunConfigID;
    }

    public void setTaskRunConfigID(Long taskRunConfigID) {
        this.taskRunConfigID = taskRunConfigID;
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
            ", \"taskRunConfigID\":" + getTaskRunConfigID() + "" +
            ", \"hour\":" + getHour() + "" +
            ", \"minute\":" + getMinute() + "" +
            ", \"second\":" + getSecond() + "" +
            "}";
    }


   
}