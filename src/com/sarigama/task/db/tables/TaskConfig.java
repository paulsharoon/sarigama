package com.sarigama.task.db.tables;

import java.util.ArrayList;
import java.util.List;

public class TaskConfig {

    public static final int ACTIVE = 1 ;
    public static final int IN_ACTIVE = 2 ;

    Long taskID ;
    Long taskConfigID ;
    Long taskConfigStartID ;
    Long taskConfigEndID ;
    Long taskConfigRunID ;
    Long rucuringTaskCongfigID ;
    int customDay = 0  ;
    int taskConfigStatus = TaskConfig.ACTIVE ;

    TaskConfigStart taskConfigStart ;
    TaskConfigEnd taskConfigEnd ;
    TaskConfigRun taskConfigRun ;
    RecuringTaskConfig recuringTaskConfig ;

   
    public TaskConfig() {
        super() ;
        this.taskConfigStart = new TaskConfigStart() ;
        this.taskConfigEnd = new TaskConfigEnd() ;
        this.taskConfigRun = new TaskConfigRun() ;
        this.recuringTaskConfig = new RecuringTaskConfig() ;
        this.taskConfigStatus = TaskConfig.ACTIVE ;
    }
    
    public TaskConfig(Long taskID, Long taskConfigID) {
        this.taskID = taskID;
        this.taskConfigID = taskConfigID;
    }

    public TaskConfig(Long taskID, Long taskConfigID, Long taskConfigStartID, Long taskConfigEndID, Long taskConfigRunID, Long rucuringTaskCongfigID, int taskConfigStatus) {
        this.taskID = taskID;
        this.taskConfigID = taskConfigID;
        this.taskConfigStartID = taskConfigStartID;
        this.taskConfigEndID = taskConfigEndID;
        this.taskConfigRunID = taskConfigRunID;
        this.rucuringTaskCongfigID = rucuringTaskCongfigID;
        this.taskConfigStatus = taskConfigStatus;
    }

    public Long getTaskID() {
        return this.taskID;
    }

    public void setTaskID(Long taskID) {
        this.taskID = taskID;
    }

    public Long getTaskConfigID() {
        return this.taskConfigID;
    }

    public void setTaskConfigID(Long taskConfigID) {
        this.taskConfigID = taskConfigID;
    }

    public Long getTaskConfigStartID() {
        return this.taskConfigStartID;
    }

    public void setTaskConfigStartID(Long taskConfigStartID) {
        this.taskConfigStartID = taskConfigStartID;
    }

    public Long getTaskConfigEndID() {
        return this.taskConfigEndID;
    }

    public void setTaskConfigEndID(Long taskConfigEndID) {
        this.taskConfigEndID = taskConfigEndID;
    }

    public Long getTaskConfigRunID() {
        return this.taskConfigRunID;
    }

    public void setTaskConfigRunID(Long taskConfigRunID) {
        this.taskConfigRunID = taskConfigRunID;
    }

    public Long getRucuringTaskCongfigID() {
        return this.rucuringTaskCongfigID;
    }

    public void setRucuringTaskCongfigID(Long rucuringTaskCongfigID) {
        this.rucuringTaskCongfigID = rucuringTaskCongfigID;
    }

    public int getCustomDay() {
        return this.customDay;
    }

    public void setCustomDay(int customDay) {
        this.customDay = customDay;
    }

    public int getTaskConfigStatus() {
        return this.taskConfigStatus;
    }

    public void setTaskConfigStatus(int taskConfigStatus) {
        this.taskConfigStatus = taskConfigStatus;
    }

    public TaskConfigStart getTaskConfigStart() {
        return this.taskConfigStart;
    }

    public void setTaskConfigStart(TaskConfigStart taskConfigStart) {
        this.taskConfigStart = taskConfigStart;
    }

    public TaskConfigEnd getTaskConfigEnd() {
        return this.taskConfigEnd;
    }

    public void setTaskConfigEnd(TaskConfigEnd taskConfigEnd) {
        this.taskConfigEnd = taskConfigEnd;
    }
    
    public TaskConfigRun getTaskConfigRun() {
        return this.taskConfigRun;
    }

    public void setTaskConfigRun(TaskConfigRun taskConfigRun) {
        this.taskConfigRun = taskConfigRun;
    }

    public void addTaskConfigRun( TaskConfigRun taskConfigRun){
        this.taskConfigRun  = taskConfigRun ;
    }

    public RecuringTaskConfig getRecuringTaskConfig() {
        return this.recuringTaskConfig;
    }

    public void setRecuringTaskConfig(RecuringTaskConfig recuringTaskConfig) {
        this.recuringTaskConfig = recuringTaskConfig;
    }
   
    @Override
    public String toString() {
        return "{" +
            " \"taskID\":" + getTaskID() + "" +
            ", \"taskConfigID\":" + getTaskConfigID() + "" +
            ", \"taskConfigStartID\":" + getTaskConfigStartID() + "" +
            ", \"taskConfigEndID\":" + getTaskConfigEndID() + "" +
            ", \"taskConfigRunID\":" + getTaskConfigRunID() + "" +
            ", \"rucuringTaskCongfigID\":" + getRucuringTaskCongfigID() + "" +
            ", \"customDay\":" + getCustomDay() + "" +
            ", \"taskConfigStatus\":" + getTaskConfigStatus() + "" +
            ", \"taskConfigStart\":" + getTaskConfigStart().toString() + "" +
            ", \"taskConfigEnd\":" + getTaskConfigEnd().toString() + "" +
            ", \"taskConfigRun\":" + getTaskConfigRun().toString() + "" +
            ", \"recuringTaskConfig\":" + getRecuringTaskConfig().toString() + "" +
            "}";
    }

}