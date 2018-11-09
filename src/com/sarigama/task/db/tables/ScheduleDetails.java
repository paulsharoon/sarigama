package com.sarigama.task.db.tables;


public class ScheduleDetails {


    public static final Integer CLOSED = 0 ;
    public static final Integer OPEN = 1 ;

    Long scheduleID ;
    Long startTime ;
    Long endTime ;
    Integer isRecuring ;
    Long nextRecuringTime ;
    Long lastRunnedTime ;
    Integer excecutedStatus ;
    Long createdTime ;
    Long updatedTime ;

    public ScheduleDetails() {
        super() ;
    }

    public ScheduleDetails(Long scheduleID) {
        this.scheduleID = scheduleID;
    }

    public ScheduleDetails(Long scheduleID, Long startTime, Long endTime, Integer isRecuring, Long nextRecuringTime, Long lastRunnedTime, Integer excecutedStatus, Long createdTime) {
        this.scheduleID = scheduleID;
        this.startTime = startTime;
        this.endTime = endTime;
        this.isRecuring = isRecuring;
        this.nextRecuringTime = nextRecuringTime;
        this.lastRunnedTime = lastRunnedTime;
        this.excecutedStatus = excecutedStatus;
        this.createdTime = createdTime;
    }

    public Long getScheduleID() {
        return this.scheduleID;
    }

    public void setScheduleID(Long scheduleID) {
        this.scheduleID = scheduleID;
    }

    public Long getStartTime() {
        return this.startTime;
    }

    public void setStartTime(Long startTime) {
        this.startTime = startTime;
    }

    public Long getEndTime() {
        return this.endTime;
    }

    public void setEndTime(Long endTime) {
        this.endTime = endTime;
    }

    public Integer getIsRecuring() {
        return this.isRecuring;
    }

    public void setIsRecuring(Integer isRecuring) {
        this.isRecuring = isRecuring;
    }

    public Long getNextRecuringTime() {
        return this.nextRecuringTime;
    }

    public void setNextRecuringTime(Long nextRecuringTime) {
        this.nextRecuringTime = nextRecuringTime;
    }

    public Long getLastRunnedTime() {
        return this.lastRunnedTime;
    }

    public void setLastRunnedTime(Long lastRunnedTime) {
        this.lastRunnedTime = lastRunnedTime;
    }

    public Integer getExcecutedStatus() {
        return this.excecutedStatus;
    }

    public void setExcecutedStatus(Integer excecutedStatus) {
        this.excecutedStatus = excecutedStatus;
    }

    public Long getCreatedTime() {
        return this.createdTime;
    }

    public void setCreatedTime(Long createdTime) {
        this.createdTime = createdTime;
    }

    public Long getUpdatedTime() {
        return this.updatedTime;
    }

    public void setUpdatedTime(Long updatedTime) {
        this.updatedTime = updatedTime;
    }
   
    public Object clone()throws CloneNotSupportedException{  
        return super.clone();  
    }  

    @Override
    public String toString() {
        return "{" +
            " \"scheduleID\":" + getScheduleID() + "" +
            ", \"startTime\":" + getStartTime() + "" +
            ", \"endTime\":" + getEndTime() + "" +
            ", \"isRecuring\":" + getIsRecuring() + "" +
            ", \"nextRecuringTime\":" + getNextRecuringTime() + "" +
            ", \"lastRunnedTime\":" + getLastRunnedTime() + "" +
            ", \"excecutedStatus\":" + getExcecutedStatus() + "" +
            ", \"createdTime\":" + getCreatedTime() + "" +
            ", \"updatedTime\":" + getUpdatedTime() + "" +
            "}";
    }

}
