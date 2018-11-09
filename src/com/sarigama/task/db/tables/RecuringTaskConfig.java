package com.sarigama.task.db.tables;


public class RecuringTaskConfig {

    Long recuringConfigID = null;
    int recuringTaskID = -1;
    int recuringRunType = -1;
    int recuringRunFrequency = -1;
    String recuringPeriodeDescription ;

    public RecuringTaskConfig() {
        
    }

    public RecuringTaskConfig(Long recuringConfigID) {
        this.recuringConfigID = recuringConfigID;
    }
    
    public RecuringTaskConfig(Long recuringConfigID, int recuringTaskID, int recuringRunType, int recuringRunFrequency, String recuringPeriodeDescription) {
        this.recuringConfigID = recuringConfigID;
        this.recuringTaskID = recuringTaskID;
        this.recuringRunType = recuringRunType;
        this.recuringRunFrequency = recuringRunFrequency;
        this.recuringPeriodeDescription = recuringPeriodeDescription;
    }

    public Long getRecuringConfigID() {
        return this.recuringConfigID;
    }

    public void setRecuringConfigID(Long recuringConfigID) {
        this.recuringConfigID = recuringConfigID;
    }

    public int getRecuringTaskID() {
        return this.recuringTaskID;
    }

    public void setRecuringTaskID(int recuringTaskID) {
        this.recuringTaskID = recuringTaskID;
    }

    public int getRecuringRunType() {
        return this.recuringRunType;
    }

    public void setRecuringRunType(int recuringRunType) {
        this.recuringRunType = recuringRunType;
    }

    public int getRecuringRunFrequency() {
        return this.recuringRunFrequency;
    }

    public void setRecuringRunFrequency(int recuringRunFrequency) {
        this.recuringRunFrequency = recuringRunFrequency;
    }

    public String getRecuringPeriodeDescription() {
        return this.recuringPeriodeDescription;
    }

    public void setRecuringPeriodeDescription(String recuringPeriodeDescription) {
        this.recuringPeriodeDescription = recuringPeriodeDescription;
    }

    @Override
    public String toString() {
        return "{" +
            " \"recuringConfigID\":" + getRecuringConfigID() + "" +
            ", \"recuringTaskID\":" + getRecuringTaskID() + "" +
            ", \"recuringRunType\":" + getRecuringRunType() + "" +
            ", \"recuringRunFrequency\":" + getRecuringRunFrequency() + "" +
            ", \"recuringPeriodeDescription\":\"" + getRecuringPeriodeDescription() + "\"" +
            "}";
    }

}