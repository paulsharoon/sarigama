package com.sarigama.task.db.tables;


public class TaskDetails {

    public static Integer PRIORITY_HIGH = 1 ;
    public static Integer PRIORITY_MEDIUM = 2 ;
    public static Integer PRIORITY_LOW = 3 ;
    
    public static Integer ACTIVE = 1 ;
    public static Integer INCATIVE = 2 ;


    Long taskID = null  ;
    String taskName = "" ;
    String taskDescription = "";
    String taskExcute = "" ;
    Long createdTime ;
    Long updatedTime ;
    Integer taskPriority  ;
    Integer taskStatus ;
    ScheduleDetails scheduleDetails ;
    TaskConfig taskConfig  ;

    // new task Constructors
    public TaskDetails() {
        super() ;
        this.taskConfig = new TaskConfig() ;
        this.scheduleDetails = null  ;
        this.taskPriority = TaskDetails.PRIORITY_LOW ;
        this.taskStatus = TaskDetails.ACTIVE ;
    }

    public TaskDetails(String taskName) {
        this.taskName = taskName;
        this.taskConfig = new TaskConfig() ;
        this.scheduleDetails = null  ;
        this.taskPriority = TaskDetails.PRIORITY_LOW ;
        this.taskStatus = TaskDetails.ACTIVE ;
    }

    // Exit Task Constructor 
    public TaskDetails(Long taskID) {
        this.taskID = taskID;
        this.taskConfig = new TaskConfig() ;
        this.scheduleDetails = new ScheduleDetails() ;
    }


    public TaskDetails(Long taskID, String taskName, String taskDescription, String taskExcute, Long createdTime, Long updatedTime, Integer taskPriority, Integer taskStatus) {
        this.taskID = taskID;
        this.taskName = taskName;
        this.taskDescription = taskDescription;
        this.taskExcute = taskExcute;
        this.createdTime = createdTime;
        this.updatedTime = updatedTime;
        this.taskPriority = taskPriority;
        this.taskStatus = taskStatus;
    }

    public Long getTaskID() {
        return this.taskID;
    }

    public void setTaskID(Long taskID) {
        this.taskID = taskID;
    }

    public String getTaskName() {
        return this.taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public String getTaskDescription() {
        return this.taskDescription;
    }

    public void setTaskDescription(String taskDescription) {
        this.taskDescription = taskDescription;
    }

    public String getTaskExcute() {
        return this.taskExcute;
    }

    public void setTaskExcute(String taskExcute) {
        this.taskExcute = taskExcute;
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

    public Integer getTaskPriority() {
        return this.taskPriority;
    }

    public void setTaskPriority(Integer taskPriority) {
        this.taskPriority = taskPriority;
    }

    public Integer getTaskStatus() {
        return this.taskStatus;
    }

    public void setTaskStatus(Integer taskStatus) {
        this.taskStatus = taskStatus;
    }

    public ScheduleDetails getScheduleDetails() {
        return this.scheduleDetails;
    }

    public void setScheduleDetails(ScheduleDetails scheduleDetails) {
        this.scheduleDetails = scheduleDetails;
    }

    public TaskConfig getTaskConfig() {
        return this.taskConfig;
    }

    public void setTaskConfig(TaskConfig taskConfig) {
        this.taskConfig = taskConfig;
    }
    /* Set Task Config start*/
    public void setStartYear(int year){
        this.getTaskConfig().getTaskConfigStart().setYear( year ) ;
    }

    public void setStartMonth(int month){
        this.getTaskConfig().getTaskConfigStart().setMonth( month ) ;
    }

    public void setStartDay(int day){
        this.getTaskConfig().getTaskConfigStart().setDay( day ) ;
    }

    public void setStartHour(int hour){
        this.getTaskConfig().getTaskConfigStart().setHour( hour ) ;
    }

    public void setStartMinute(int minute){
        this.getTaskConfig().getTaskConfigStart().setMinute( minute ) ;
    }

    public void setStartSecond(int second){
        this.getTaskConfig().getTaskConfigStart().setSecond( second ) ;
    }

     /* Set Task Config end*/
     public void setEndYear(int year){
        this.getTaskConfig().getTaskConfigEnd().setYear( year ) ;
    }

    public void setEndMonth(int month){
        this.getTaskConfig().getTaskConfigEnd().setMonth( month ) ;
    }

    public void setEndDay(int day){
        this.getTaskConfig().getTaskConfigEnd().setDay( day ) ;
    }

    public void setEndHour(int hour){
        this.getTaskConfig().getTaskConfigEnd().setHour( hour ) ;
    }

    public void setEndMinute(int minute){
        this.getTaskConfig().getTaskConfigEnd().setMinute( minute ) ;
    }

    public void setEndSecond(int second){
        this.getTaskConfig().getTaskConfigEnd().setSecond( second ) ;
    }

    /* Set Task Config run*/

    public void setRunHour(int hour){
        this.getTaskConfig().getTaskConfigRun().setHour( hour ) ;
    }

    public void setRunMinute(int minute){
        this.getTaskConfig().getTaskConfigRun().setMinute( minute ) ;
    }

    public void setRunSecond(int second){
        this.getTaskConfig().getTaskConfigRun().setSecond( second ) ;
    }

    /* Set recurring Task Config run*/
    public void setRecuringTasKId( int recuringTaskID ){
        this.getTaskConfig().getRecuringTaskConfig().setRecuringTaskID(recuringTaskID);
    }

    public void setRecuringRunType( int recuringRunType ){
        this.getTaskConfig().getRecuringTaskConfig().setRecuringRunType(recuringRunType);
    }

    public void setRecuringRunFrequency( int runFrequency ){
        this.getTaskConfig().getRecuringTaskConfig().setRecuringRunFrequency(runFrequency);
    }

    public void setRecurringCustomDay( int day ){
        this.getTaskConfig().setCustomDay( day ) ;
    }


    @Override
    public String toString() {
        return "{" +
            " \"taskID\":" + getTaskID() + "" +
            ", \"taskName\":\"" + getTaskName() + "\"" +
            ", \"taskDescription\":\"" + getTaskDescription() + "\"" +
            ", \"taskExcute\":\"" + getTaskExcute() + "\"" +
            ", \"createdTime\":" + getCreatedTime() + "" +
            ", \"updatedTime\":" + getUpdatedTime() + "" +
            ", \"taskPriority\":" + getTaskPriority() + "" +
            ", \"taskStatus\":" + getTaskStatus() + "" +
            ", \"scheduleDetails\":" + getScheduleDetails().toString() +
            ", \"taskConfig\":" + getTaskConfig().toString() +
            "}";
    }

}
