package com.sarigama.task.db.tables;


public class RecuringTask {

    public static final int ONCE = 1 ;
    public static final int DAILY = 2 ;
    public static final int WEEKLY = 4 ;
    public static final int MONTHLY = 8 ;
    
    public static final int SUNDAY = 1 ;
    public static final int MONDAY = 2 ;
    public static final int TUESDAY = 3 ;
    public static final int WEDNESDAY = 4 ;
    public static final int THURSDAY = 5 ;
    public static final int FRIDAY = 6 ;
    public static final int SATURDAY = 7 ;

    public static final int MONTH_START = 1 ;
    public static final int MONTH_END = 2 ;
    
    public static final int DEFAULT = 99 ;
    public static final int CUSTOM = 999 ;

    public static final int RUCURING = 1 ;
    public static final int NON_RECURING = 0 ;

    Long recuringID ;
    int recuringType ; 
    Long recuringDescription ;

    public RecuringTask(Long recuringID) {
        this.recuringID = recuringID;
    }

    public RecuringTask(Long recuringID, int recuringType, Long recuringDescription) {
        this.recuringID = recuringID;
        this.recuringType = recuringType;
        this.recuringDescription = recuringDescription;
    }

    public Long getRecuringID() {
        return this.recuringID;
    }

    public void setRecuringID(Long recuringID) {
        this.recuringID = recuringID;
    }

    public int getRecuringType() {
        return this.recuringType;
    }

    public void setRecuringType(int recuringType) {
        this.recuringType = recuringType;
    }

    public Long getRecuringDescription() {
        return this.recuringDescription;
    }

    public void setRecuringDescription(Long recuringDescription) {
        this.recuringDescription = recuringDescription;
    }

    @Override
    public String toString() {
        return "{" +
            " recuringID='" + getRecuringID() + "'" +
            ", recuringType='" + getRecuringType() + "'" +
            ", recuringDescription='" + getRecuringDescription() + "'" +
            "}";
    }

}