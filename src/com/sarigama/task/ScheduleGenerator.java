package com.sarigama.task;

import java.util.Calendar;
import java.util.GregorianCalendar;

import com.sarigama.task.db.tables.RecuringTask;
import com.sarigama.task.db.tables.RecuringTaskConfig;
import com.sarigama.task.db.tables.ScheduleDetails;
import com.sarigama.task.db.tables.TaskConfig;
import com.sarigama.task.db.tables.TaskConfigEnd;
import com.sarigama.task.db.tables.TaskConfigRun;
import com.sarigama.task.db.tables.TaskConfigStart;
import com.sarigama.task.db.tables.TaskDetails;
import com.sarigama.task.exception.ScheduleException;

public class ScheduleGenerator {


    TaskDetails taskDetails  ;
    ScheduleDetails newSchedule = new ScheduleDetails() ;
    ScheduleDetails excistingSchedule =  null ; 

    public ScheduleGenerator(TaskDetails taskDetails) throws ScheduleException , Exception {
        this.taskDetails = taskDetails;
        generateSchedule() ;
    }

    public ScheduleDetails generateSchedule() throws ScheduleException , Exception {
    
            // Intialize the excisting schedule if exist already
            this.excistingSchedule = taskDetails.getScheduleDetails() ;
            generateNewSchedule();
            
        return this.newSchedule ;
    }

    public boolean generateNewSchedule() throws Exception {

        TaskConfig taskConfig = this.taskDetails.getTaskConfig() ;
        TaskConfigStart taskConfigStart = taskConfig.getTaskConfigStart() ;
        TaskConfigEnd taskConfigEnd = taskConfig.getTaskConfigEnd() ;
        TaskConfigRun taskConfigRun = taskConfig.getTaskConfigRun() ;
        RecuringTaskConfig recuringTaskConfig = taskConfig.getRecuringTaskConfig() ;

        Long startTime = taskConfigStart.getTime();
        Long endTime = taskConfigEnd.getTime();

        // if schedule alredy exit copy the default data to new schedule
        // else set the data from new task configuration
        if( this.excistingSchedule != null ){ 
            this.newSchedule.setScheduleID( this.excistingSchedule.getScheduleID() );
            this.newSchedule.setStartTime( this.excistingSchedule.getStartTime() );
            this.newSchedule.setEndTime( this.excistingSchedule.getEndTime() );
        }else{
            this.newSchedule.setStartTime( startTime );
            this.newSchedule.setEndTime(endTime);
        }
        
        // Generate the Next Excecution schedule 
        generateNextExcecution( recuringTaskConfig.getRecuringTaskID() );

        return false ;
    }

    public void generateNextExcecution( int recuringType  ) throws Exception {

        if( recuringType == RecuringTask.ONCE) { // check for excecute only ONCE

            // set the schedule as non recuring 
           this.newSchedule.setIsRecuring( RecuringTask.NON_RECURING ); 
           generateScheduleOnce() ;

        }else if( recuringType == RecuringTask.DAILY) { // check for excecute DAILY
            
             // set the schedule as recuring 
            this.newSchedule.setIsRecuring( RecuringTask.RUCURING );
            generateScheduleDaily();

        }else if( recuringType == RecuringTask.WEEKLY) { // check for excecute WEEKLY

            // set the schedule as recuring 
            this.newSchedule.setIsRecuring( RecuringTask.RUCURING );
            generateScheduleWeekly();
            
        }else if( recuringType == RecuringTask.MONTHLY) { // check for excecute MONTHLY

            // set the schedule as recuring 
            this.newSchedule.setIsRecuring( RecuringTask.RUCURING );
            generateScheduleMonthly();
            
        }
    }
    public void generateScheduleOnce( ) throws Exception{

        // Intialize the sixMinuties, if the date is minimm of given start time 
        // excecute after six minutes
        Long sixMinutes = new Long( 6 * 60 * 60 * 1000 ) ;
        Calendar calendar = Calendar.getInstance(); 
        Long currentTime = calendar.getTimeInMillis();

        TaskConfig taskConfig = this.taskDetails.getTaskConfig() ;
        TaskConfigStart taskConfigStart = taskConfig.getTaskConfigStart() ;
        Long excecuteTime = taskConfigStart.getTime() ;

        if(this.excistingSchedule != null ){ //Already scheduled

            this.newSchedule = this.excistingSchedule ;
            // If already scheduled, we want to stop the Task.
            // Due to only once scenario
            this.newSchedule.setExcecutedStatus( ScheduleDetails.CLOSED ) ;
            this.newSchedule.setNextRecuringTime( new Long(0) );
            this.newSchedule.setUpdatedTime( currentTime ) ;

        }else{ // if new schedule

            // if current time is greater than the scheduled run time.
            // Add the six min to current time and excecute the task only once.
            if( currentTime >  excecuteTime ){  
                excecuteTime = currentTime + sixMinutes ;
            } 
        
            this.newSchedule.setNextRecuringTime( excecuteTime );
            // scheduled for first time, the task is to get open.
            this.newSchedule.setExcecutedStatus( ScheduleDetails.OPEN ) ;
            this.newSchedule.setCreatedTime( currentTime ) ;
            this.newSchedule.setUpdatedTime( currentTime ) ;
        }
       
    }

    public void generateScheduleDaily() throws Exception {
        
        Calendar calendar = Calendar.getInstance(); 
        Long currentTime = calendar.getTimeInMillis();

        TaskConfig taskConfig = this.taskDetails.getTaskConfig() ;
        TaskConfigStart taskConfigStart = taskConfig.getTaskConfigStart() ;
        TaskConfigRun taskConfigRun = taskConfig.getTaskConfigRun() ;
        TaskConfigEnd taskConfigEnd = taskConfig.getTaskConfigEnd() ;

        Long endTime = taskConfigEnd.getTime() ; 
        
        if(this.excistingSchedule == null ){  //New scheduled

            // create a DateProcessor for start date and RunTime
            DateProcessor dateProcessor = new DateProcessor( taskConfigStart.getYear() , taskConfigStart.getMonth() , taskConfigStart.getDay() 
                                                           , taskConfigRun.getHour() , taskConfigRun.getMinute() , taskConfigRun.getSecond() ) ;
            Long excecuteTime = dateProcessor.getTime()  ;  
              
            this.newSchedule.setCreatedTime( currentTime ) ;
            this.newSchedule.setUpdatedTime( currentTime ) ;

            // check for the excecuteTime in between start and end time for New Schedule
            if( currentTime >=  excecuteTime ||  endTime <= excecuteTime  ) {
                // if excecuteTime is not between the start and end , set the new schedule as CLOSED
                this.newSchedule.setExcecutedStatus( ScheduleDetails.CLOSED ) ;
                this.newSchedule.setNextRecuringTime( new Long(0) );
                this.newSchedule.setLastRunnedTime(  new Long(0) );
            }else{
                // if excecuteTime is  between the start and end , set the new schedule as OPEN
                this.newSchedule.setExcecutedStatus( ScheduleDetails.OPEN ) ;
                this.newSchedule.setNextRecuringTime( excecuteTime );
                this.newSchedule.setLastRunnedTime(  new Long(0) );
            }

        }else{ // Already Scheduled 
            Long lastExcecutedTime = this.excistingSchedule.getNextRecuringTime() ;

            Calendar calendar2 = GregorianCalendar.getInstance();
            calendar2.setTimeInMillis(lastExcecutedTime);

            // add days to the Schedule :::  default one day
            calendar2.add( Calendar.DATE, taskConfig.getCustomDay() );

            Long excecuteTime = calendar2.getTimeInMillis() ;

            this.newSchedule.setUpdatedTime( currentTime ) ;

            // check for the excecuteTime is lesser than end time generate Schedule
            if( excecuteTime >  endTime ){
                // if excecuteTime greater than the end time set the schedule as CLOSED
                this.newSchedule.setExcecutedStatus( ScheduleDetails.CLOSED ) ;
                this.newSchedule.setNextRecuringTime( new Long(0) );
                this.newSchedule.setLastRunnedTime( lastExcecutedTime );
            }else{
                 // if excecuteTime lesser than the end time set the schedule as OPEN
                this.newSchedule.setExcecutedStatus( ScheduleDetails.OPEN ) ;
                this.newSchedule.setNextRecuringTime( excecuteTime );
                this.newSchedule.setLastRunnedTime( lastExcecutedTime );
            }

        }

    }


    public void generateScheduleWeekly() throws Exception {
        Calendar calendar = Calendar.getInstance(); 
        Long currentTime = calendar.getTimeInMillis();

        TaskConfig taskConfig = this.taskDetails.getTaskConfig() ;
        TaskConfigStart taskConfigStart = taskConfig.getTaskConfigStart() ;
        TaskConfigRun taskConfigRun = taskConfig.getTaskConfigRun() ;
        TaskConfigEnd taskConfigEnd = taskConfig.getTaskConfigEnd() ;
        RecuringTaskConfig recuringTaskConfig = taskConfig.getRecuringTaskConfig() ;

        Long endTime = taskConfigEnd.getTime() ; 
        Long startTime = taskConfigStart.getTime();
        int excecuteWeekDay = recuringTaskConfig.getRecuringRunType() ;

        Calendar calendar2 = Calendar.getInstance(); 
        if(this.excistingSchedule == null ){ //New scheduled

            // generate the excecuteTime
            DateProcessor dateProcessor = new DateProcessor( taskConfigStart.getYear() , taskConfigStart.getMonth() , taskConfigStart.getDay() 
            , taskConfigRun.getHour() , taskConfigRun.getMinute() , taskConfigRun.getSecond() ) ;
            Long runTime = dateProcessor.getTime()  ;  
            calendar2.setTimeInMillis( runTime );
            
            // if the excecuteTime is same as excecuted we day and the time is finished 
            if( calendar2.get(Calendar.DAY_OF_WEEK) ==  excecuteWeekDay &&  runTime < startTime ){
                calendar2.add(Calendar.DATE, 1); // change the date to next date
            }

            // iterate to find the next excecuted wee day
            while (calendar2.get(Calendar.DAY_OF_WEEK) != excecuteWeekDay ) {
                calendar2.add(Calendar.DATE, 1);
            }

           
            Long excecuteTime =  calendar2.getTimeInMillis() ;
            this.newSchedule.setCreatedTime( currentTime ) ;
            this.newSchedule.setUpdatedTime( currentTime ) ;

            if( excecuteTime >  endTime ){ // if next excecutedTime is greater than tha endTime put the schedule as closed
                this.newSchedule.setExcecutedStatus( ScheduleDetails.CLOSED ) ;
                this.newSchedule.setNextRecuringTime( new Long(0) );
                this.newSchedule.setLastRunnedTime( excecuteTime );
            }else{ // if it is bwetween start and end time set open and excexutedTime
                this.newSchedule.setExcecutedStatus( ScheduleDetails.OPEN ) ;
                this.newSchedule.setNextRecuringTime( excecuteTime );
                this.newSchedule.setLastRunnedTime( new Long(0) );
            }


        }else{ // Already Scheduled 
            // get the last excecuted time
            Long lastExcecutedTime = this.excistingSchedule.getNextRecuringTime() ;
            calendar2.setTimeInMillis(lastExcecutedTime);

             // add one day to the excecuted time
            calendar2.add(Calendar.DATE, 1);

            // find the next excecuted time
            while (calendar2.get(Calendar.DAY_OF_WEEK) != excecuteWeekDay ) {
                calendar2.add(Calendar.DATE, 1);
            }

            Long excecuteTime =  calendar2.getTimeInMillis() ;
            this.newSchedule.setCreatedTime( this.excistingSchedule.getCreatedTime() ) ;
            this.newSchedule.setUpdatedTime( currentTime ) ;
            if( excecuteTime >  endTime ){
                // if next excecutedTime is greater than tha endTime put the schedule as closed
                this.newSchedule.setExcecutedStatus( ScheduleDetails.CLOSED ) ;
                this.newSchedule.setNextRecuringTime( new Long(0) );
                this.newSchedule.setLastRunnedTime( lastExcecutedTime );
            }else{
                // if it is bwetween start and end time set open and excexutedTime
                this.newSchedule.setExcecutedStatus( ScheduleDetails.OPEN ) ;
                this.newSchedule.setNextRecuringTime( excecuteTime );
                this.newSchedule.setLastRunnedTime( lastExcecutedTime );
            }

        }
    }

    public void generateScheduleMonthly() throws Exception{
        Calendar calendar = Calendar.getInstance(); 
        Long currentTime = calendar.getTimeInMillis();

        TaskConfig taskConfig = this.taskDetails.getTaskConfig() ;
        TaskConfigStart taskConfigStart = taskConfig.getTaskConfigStart() ;
        TaskConfigRun taskConfigRun = taskConfig.getTaskConfigRun() ;
        TaskConfigEnd taskConfigEnd = taskConfig.getTaskConfigEnd() ;
        RecuringTaskConfig recuringTaskConfig = taskConfig.getRecuringTaskConfig() ;

        Long endTime = taskConfigEnd.getTime() ; 
        Long startTime = taskConfigStart.getTime();

        int recuringType =  recuringTaskConfig.getRecuringTaskID() ;
        int rucuringRunType =  recuringTaskConfig.getRecuringRunType() ;
        int rucuringFrequency =  recuringTaskConfig.getRecuringRunFrequency() ;
       // Calendar calendar2 = Calendar.getInstance(); 
      
        if(this.excistingSchedule == null ){
            
            
            Calendar curretMonth = Calendar.getInstance() ;

            curretMonth.setTimeInMillis( startTime < currentTime ? currentTime :  startTime  );
            curretMonth.set(Calendar.HOUR_OF_DAY , taskConfigRun.getHour() ); 
            curretMonth.set(Calendar.MINUTE , taskConfigRun.getMinute() ); 
            curretMonth.set(Calendar.SECOND , taskConfigRun.getSecond() ); 
            Long excecuteTime  = curretMonth.getTimeInMillis();
            // for month start checking and their options
            if( rucuringRunType == RecuringTask.MONTH_START )
            {
                // set the month first date
                curretMonth.set( Calendar.DAY_OF_MONTH , 1);

                if( rucuringFrequency != RecuringTask.DEFAULT ){
                    // if set as run on some specified day get the specified day
                    while (curretMonth.get(Calendar.DAY_OF_WEEK) != rucuringFrequency ) {
                        curretMonth.add(Calendar.DATE, 1);
                    }
                }
               
               excecuteTime =  curretMonth.getTimeInMillis() ;
                if( excecuteTime <  startTime ){
                    curretMonth.add( Calendar.DAY_OF_MONTH , 1 );
                    while (curretMonth.get(Calendar.DAY_OF_WEEK) != rucuringFrequency ) {
                        curretMonth.add(Calendar.DATE, 1);
                    }
                }
             
                excecuteTime =  curretMonth.getTimeInMillis() ;

            } // for month end checking and their options
            else if( rucuringRunType == RecuringTask.MONTH_END ){ 
                curretMonth.set(Calendar.DAY_OF_MONTH, curretMonth.getActualMaximum(Calendar.DAY_OF_MONTH));

                if( rucuringFrequency != RecuringTask.DEFAULT ){
                    // if set as run on some specified day get the specified day
                    while (curretMonth.get(Calendar.DAY_OF_WEEK) != rucuringFrequency ) {
                        curretMonth.add(Calendar.DATE, -1);
                    }
                }

                excecuteTime =  curretMonth.getTimeInMillis() ;
                if( excecuteTime <  startTime ){
                    curretMonth.add( Calendar.DAY_OF_MONTH , 1 );
                    while (curretMonth.get(Calendar.DAY_OF_WEEK) != rucuringFrequency ) {
                        curretMonth.add(Calendar.DATE, -1);
                    }
                }

                excecuteTime =  curretMonth.getTimeInMillis() ;
            }

            this.newSchedule.setCreatedTime( currentTime ) ;
            this.newSchedule.setUpdatedTime( currentTime ) ;
            if(  excecuteTime > endTime ){
                this.newSchedule.setExcecutedStatus( ScheduleDetails.CLOSED ) ;
                this.newSchedule.setNextRecuringTime( new Long(0) );
                this.newSchedule.setLastRunnedTime( excecuteTime );
            }else{
                this.newSchedule.setExcecutedStatus( ScheduleDetails.OPEN ) ;
                this.newSchedule.setNextRecuringTime( excecuteTime );
                this.newSchedule.setLastRunnedTime( new Long(0) );
            }

        }else{  // Already Scheduled 
            Long lastExcecutedTime = this.excistingSchedule.getNextRecuringTime() ;

            // set calendar as last excecutedTime 
            Calendar curretMonth = Calendar.getInstance() ;
            curretMonth.setTimeInMillis(lastExcecutedTime); 
            curretMonth.set(Calendar.HOUR_OF_DAY , taskConfigRun.getHour() ); 
            curretMonth.set(Calendar.MINUTE , taskConfigRun.getMinute() ); 
            curretMonth.set(Calendar.SECOND , taskConfigRun.getSecond() ); 

            Long excecuteTime  = curretMonth.getTimeInMillis();

            curretMonth.add( Calendar.MONTH , 1 ) ;
            if( rucuringRunType == RecuringTask.MONTH_START )
            { 
                curretMonth.set( Calendar.DAY_OF_MONTH , 1 );

                if( rucuringFrequency != RecuringTask.DEFAULT ){
                    while (curretMonth.get(Calendar.DAY_OF_WEEK) != rucuringFrequency ) {
                        curretMonth.add(Calendar.DATE, 1);
                    }
                }

                excecuteTime = curretMonth.getTimeInMillis() ;
            }
            else if( rucuringRunType == RecuringTask.MONTH_END)
            {
                curretMonth.set(Calendar.DAY_OF_MONTH, curretMonth.getActualMaximum(Calendar.DAY_OF_MONTH));

                if( rucuringFrequency != RecuringTask.DEFAULT ){
                    while (curretMonth.get(Calendar.DAY_OF_WEEK) != rucuringFrequency ) {
                        curretMonth.add(Calendar.DATE, -1);
                    }
                }

                excecuteTime =  curretMonth.getTimeInMillis() ;
            }

            this.newSchedule.setCreatedTime( this.excistingSchedule.getCreatedTime() ) ;
            this.newSchedule.setUpdatedTime( currentTime ) ;
            if(  excecuteTime > endTime ){
                this.newSchedule.setExcecutedStatus( ScheduleDetails.CLOSED ) ;
                this.newSchedule.setNextRecuringTime( new Long(0) );
                this.newSchedule.setLastRunnedTime( lastExcecutedTime );
            }else{
                this.newSchedule.setExcecutedStatus( ScheduleDetails.OPEN ) ;
                this.newSchedule.setNextRecuringTime( excecuteTime );
                this.newSchedule.setLastRunnedTime( lastExcecutedTime );
            }
        }
    }

    public TaskDetails getTaskDetails() {
        return this.taskDetails;
    }

    public void setTaskDetails(TaskDetails taskDetails) {
        this.taskDetails = taskDetails;
    }

    public ScheduleDetails getNewSchedule() {
        return this.newSchedule;
    }

    public void setNewSchedule(ScheduleDetails newSchedule) {
        this.newSchedule = newSchedule;
    }

    public ScheduleDetails getExcistingSchedule() {
        return this.excistingSchedule;
    }

    public void setExcistingSchedule(ScheduleDetails excistingSchedule) {
        this.excistingSchedule = excistingSchedule;
    }

    
}