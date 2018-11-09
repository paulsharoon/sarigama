package com.sarigama.task;

import java.util.Calendar;

import com.sarigama.task.db.ScheduleDB;
import com.sarigama.task.db.TaskDB;
import com.sarigama.task.db.tables.RecuringTask;
import com.sarigama.task.db.tables.RecuringTaskConfig;
import com.sarigama.task.db.tables.ScheduleDetails;
import com.sarigama.task.db.tables.TaskConfig;
import com.sarigama.task.db.tables.TaskConfigEnd;
import com.sarigama.task.db.tables.TaskConfigRun;
import com.sarigama.task.db.tables.TaskConfigStart;
import com.sarigama.task.db.tables.TaskDetails;
import com.sarigama.task.exception.TaskException;

public class TaskEngine {


    TaskDetails taskDetails ;

    public TaskEngine() {
       this.taskDetails = new TaskDetails() ;
    }

    public TaskEngine( String taskName) {
        this.taskDetails = new TaskDetails( taskName ) ;
    }

    public TaskDetails getTaskDetails() {
        return this.taskDetails;
    }

    public void setName(String taskName){
        this.taskDetails.setTaskName(taskName);
    }

    public void setDescription(String taskDescription){
        this.taskDetails.setTaskDescription(taskDescription);
    }

    public void setClass(String taskClassName){
        this.taskDetails.setTaskExcute(taskClassName);
    }

    public void setPriority(int priority){
        this.taskDetails.setTaskPriority(priority);
    }

    public void setStartDate(int day , int month , int year ) {
        this.taskDetails.setStartDay( day ); 
        this.taskDetails.setStartMonth( month );
        this.taskDetails.setStartYear( year );
    }

    public void setStartTime(int hour , int minute , int second ) {
        this.taskDetails.setStartHour( hour ); 
        this.taskDetails.setStartMinute( minute );
        this.taskDetails.setStartSecond( second );
    }

    public void setEndDate(int day , int month , int year ) {
        this.taskDetails.setEndDay( day ); 
        this.taskDetails.setEndMonth( month );
        this.taskDetails.setEndYear( year );
    }

    public void setEndTime(int hour , int minute , int second ) {
        this.taskDetails.setEndHour( hour ); 
        this.taskDetails.setEndMinute( minute );
        this.taskDetails.setEndSecond( second );
    }

    public void setRunTime(int hour , int minute , int second ) {
        this.taskDetails.setRunHour( hour ); 
        this.taskDetails.setRunMinute( minute );
        this.taskDetails.setRunSecond( second );
    }

    public void setSchedule(int type ) {
        this.taskDetails.setRecuringTasKId(type);
    }

    public void setScheduleRun(int runType ) {
        this.taskDetails.setRecuringRunType(runType);
    }

    public void setScheduleRuFrequency(int runFrequency ) {
        this.taskDetails.setRecuringRunFrequency(runFrequency);
    }

    public void setCustomDay(int day ){
        this.taskDetails.setRecurringCustomDay( day ) ;
    }

    public TaskDetails addTask() throws Exception , TaskException {
       
        Calendar calendar = Calendar.getInstance(); 
        Long currentTime = calendar.getTimeInMillis();

        this.taskDetails.setCreatedTime(currentTime);
        this.taskDetails.setUpdatedTime(currentTime) ;

        if( this.taskDetails.getTaskName().equals("") || this.taskDetails.getTaskName().length() < 1 ){
            throw new TaskException("For adding task, Task Name is required, set it by using setName(String taskName)" );
        }

        if( this.taskDetails.getTaskExcute().equals("") || this.taskDetails.getTaskExcute().length() < 1 ){
            throw new TaskException("For adding Task, Excecution Class name is required, set it by using setClass(String taskClassName)" );
        }

        TaskConfig taskConfig = this.taskDetails.getTaskConfig() ;
        TaskConfigStart taskConfigStart = taskConfig.getTaskConfigStart() ;
        TaskConfigEnd taskConfigEnd = taskConfig.getTaskConfigEnd() ;
        TaskConfigRun taskConfigRun = taskConfig.getTaskConfigRun() ;
        RecuringTaskConfig recuringTaskConfig = taskConfig.getRecuringTaskConfig() ;

        // Check for start time, this mandatory
        if(  taskConfigStart.getYear() == -1 || taskConfigStart.getMonth() == -1 ||taskConfigStart.getDay() == -1  ){
            throw new TaskException("For adding Task, Start Date is required,  set it by using setStartDate(int day , int month , int year )" );
        }

         // Check for run time, this mandatory
        if( taskConfigRun.getHour() == -1 || taskConfigRun.getMinute() == -1 || taskConfigRun.getSecond() == -1 ){
            throw new TaskException("For adding Task,Running Timeis required , set it by using setRunTime(int hour , int minute , int second )" );
        }

        System.out.println(recuringTaskConfig.toString());
        // check for the conditions and set default values
        setScheduleRecuring( this.taskDetails ) ;

        System.out.println(recuringTaskConfig.toString());

        if(  taskConfigEnd.getYear() == -1  || taskConfigEnd.getMonth() == -1 ||  taskConfigEnd.getDay() == -1){
            throw new TaskException("For adding Task, End Date is required ,  set it by using setEndDate(int day , int month , int year )" );
        }

        if( recuringTaskConfig.getRecuringTaskID() == -1 ){
            throw new TaskException("For adding Task,Schedule type is required , set it by using setSchedule(int type) " );
        }

       
        System.out.println(recuringTaskConfig.toString());

        TaskDB taskDB = new TaskDB() ;
        ScheduleDB scheduleDB = new ScheduleDB() ;

        // generate Task_id for new Task
        if(!taskDB.addTask( this.taskDetails )){
            throw new TaskException("Error on adding new task " );
        }

        if( this.taskDetails.getTaskID() == null ){
            throw new TaskException("Error on adding new task " );
        }

        taskConfigStart.setTaskID(this.taskDetails.getTaskID()); // Set foreign Key for Task Start Information
        taskConfigEnd.setTaskID(this.taskDetails.getTaskID()); // Set foreign Key for Task End Information
        taskConfigRun.setTaskID(this.taskDetails.getTaskID()); // Set foreign Key for Task Run Information

        if( !taskDB.addTaskConfigStart( taskConfigStart ) ) // generate new Task Start Information
        {
            taskDB.deleteExceptionTask(  this.taskDetails , null ) ;
            throw new TaskException("Error in Task Start Configuration" );
        }
        if( !taskDB.addTaskConfigEnd( taskConfigEnd ) ) // generate new Task End Information
        {
            taskDB.deleteExceptionTask(  this.taskDetails , null ) ;
            throw new TaskException("Error in Task End Configuration" );
        }
        if( !taskDB.addTaskConfigRun( taskConfigRun ) ) // generate new Task Run Information
        {
            taskDB.deleteExceptionTask(  this.taskDetails , null ) ;
            throw new TaskException("Error in Task Run Configuration" );
        }
        if( !taskDB.getRecuringTaskConfig( recuringTaskConfig ) ) // get Task recursion Inforamation
        {
            taskDB.deleteExceptionTask(  this.taskDetails , null ) ;
            throw new TaskException("Error in Recuring Task Configuration" );
        }

        if( taskConfigStart.getTaskStartConfigID() == null ||  taskConfigEnd.getTaskEndConfigID() == null || taskConfigRun.getTaskRunConfigID() == null || recuringTaskConfig.getRecuringConfigID() == null)
        {
            taskDB.deleteExceptionTask(  this.taskDetails , null ) ;
            throw new TaskException("Error in Task Configuration" );
        }

        //System.out.println(recuringTaskConfig.toString());

        //recuringTaskConfig.setRecuringConfigID(new Long(1) );
        // Config the Task Configuration 
        taskConfig.setTaskID(taskDetails.getTaskID()); // Set foreign Key for Task Configuration Information
        taskConfig.setTaskConfigStartID( taskConfigStart.getTaskStartConfigID() );
        taskConfig.setTaskConfigEndID( taskConfigEnd.getTaskEndConfigID() );
        taskConfig.setTaskConfigRunID( taskConfigRun.getTaskRunConfigID() );
        taskConfig.setRucuringTaskCongfigID( recuringTaskConfig.getRecuringConfigID() );

        //System.out.println(taskConfig.toString());
        
        if( !taskDB.addTaskConfig( taskConfig ) )// generate new Task Configuration Information
        {
            taskDB.deleteExceptionTask(  this.taskDetails , null ) ;
            throw new TaskException("Error in Task Configuration" );
        }

        //System.out.println(taskConfig.toString());

        /*
            Generate the Schedule for the first Run of the Task
        */
        ScheduleGenerator scheduleGenerator = new ScheduleGenerator(this.taskDetails);
        ScheduleDetails newSchedule = scheduleGenerator.getNewSchedule() ;

        if( !scheduleDB.addSchedule( newSchedule ) )// generate new Task Configuration Information
        {
            taskDB.deleteExceptionTask(  this.taskDetails , null ) ;
            throw new TaskException("Error in Schedule Task" );
        }
        
        // Add new generated schedule data to the task
        this.taskDetails.setScheduleDetails(newSchedule);

        if( !taskDB.addTaskScheduleMap( this.taskDetails )){
            taskDB.deleteExceptionTask(  this.taskDetails , newSchedule) ;
            throw new TaskException("Error in Task <-> Schedule Map" );
        }

        //logger.info("New Task is added and scheduled for next excecution. Task :" + this.taskDetails.toString()  );
        return this.taskDetails ;
    }

    /*
        check for schedule type once , daily ...
        and set defaults .......
    */
    public void setScheduleRecuring( TaskDetails taskDetails) throws Exception {

        TaskConfig taskConfig = taskDetails.getTaskConfig() ;
        TaskConfigStart taskConfigStart  = taskConfig.getTaskConfigStart() ; 
        TaskConfigEnd taskConfigEnd  = taskConfig.getTaskConfigEnd() ; 

        Calendar endCalendar = Calendar.getInstance() ;
        endCalendar.setTimeInMillis( taskConfigStart.getTime() );

        RecuringTaskConfig recuringTaskConfig = taskConfig.getRecuringTaskConfig() ;
        Calendar tdyDate = Calendar.getInstance(); 

        DateProcessor dateProcessor =  null ;
        // Default schedule type is ONCE
        if( recuringTaskConfig.getRecuringTaskID() == -1){

            recuringTaskConfig.setRecuringTaskID( RecuringTask.ONCE );
        }

        // Check for Excecute only once 
        if( recuringTaskConfig.getRecuringTaskID() == RecuringTask.ONCE ){

            recuringTaskConfig.setRecuringRunType( RecuringTask.DEFAULT ) ;
            recuringTaskConfig.setRecuringRunFrequency( RecuringTask.DEFAULT ) ;
            dateProcessor = new DateProcessor( endCalendar.getTimeInMillis() );
        }  // Check for Excecute Daily
        else if(recuringTaskConfig.getRecuringTaskID() == RecuringTask.DAILY ){
            
            recuringTaskConfig.setRecuringRunType( RecuringTask.DEFAULT ) ;
            recuringTaskConfig.setRecuringRunFrequency( RecuringTask.DEFAULT ) ;

            endCalendar.add( Calendar.MONTH , 6) ;
            dateProcessor = new DateProcessor( endCalendar.getTimeInMillis() );
        }// Check for Excecute Weekly
        else if(recuringTaskConfig.getRecuringTaskID() == RecuringTask.WEEKLY ){

            recuringTaskConfig.setRecuringRunFrequency( RecuringTask.DEFAULT ) ;
            if( recuringTaskConfig.getRecuringRunType() == -1 || ( recuringTaskConfig.getRecuringRunType() < RecuringTask.SUNDAY &&  recuringTaskConfig.getRecuringRunType() > RecuringTask.SUNDAY ) ){
                int currentDay = tdyDate.get ( Calendar.DAY_OF_WEEK );
                recuringTaskConfig.setRecuringRunType( currentDay ) ;
            }

            endCalendar.add( Calendar.MONTH , 12 ) ;
            dateProcessor = new DateProcessor( endCalendar.getTimeInMillis() );
        } // Check for Excecute Monthly
        else if(recuringTaskConfig.getRecuringTaskID() == RecuringTask.MONTHLY ){

            //Set Default as month Start 
            if( recuringTaskConfig.getRecuringRunType() == -1 ){
                recuringTaskConfig.setRecuringRunType( RecuringTask.MONTH_START ) ;
                recuringTaskConfig.setRecuringRunFrequency( RecuringTask.DEFAULT ) ;
            }// If Month start or month End and start day set as default
            else if( recuringTaskConfig.getRecuringRunFrequency() == -1 ){ 
                recuringTaskConfig.setRecuringRunFrequency( RecuringTask.DEFAULT ) ;
            }//If Frequency is custom set the field as Custom and Custom date is not give set the default as 15
            else if( recuringTaskConfig.getRecuringRunFrequency() == RecuringTask.CUSTOM && taskConfig.getCustomDay() == 0 ){
                recuringTaskConfig.setRecuringRunType( RecuringTask.DEFAULT ) ;
                taskConfig.setCustomDay(  15  );
            }else if(recuringTaskConfig.getRecuringRunFrequency() == RecuringTask.CUSTOM){
                recuringTaskConfig.setRecuringRunType( RecuringTask.DEFAULT ) ;
            }
            endCalendar.add( Calendar.MONTH , 24 ) ;
            dateProcessor = new DateProcessor( endCalendar.getTimeInMillis() );
        }

        // if task end configuration not set , Set the default values 
        if( taskConfigEnd.getDay() == -1 || taskConfigEnd.getMonth() == -1 || taskConfigEnd.getYear() == -1){
            taskConfigEnd.setYear(dateProcessor.getYear());
            taskConfigEnd.setMonth(dateProcessor.getMonth());
            taskConfigEnd.setDay(dateProcessor.getDate());
            taskConfigEnd.setHour(dateProcessor.getHour());
            taskConfigEnd.setMinute(dateProcessor.getMinute());
            taskConfigEnd.setSecond(dateProcessor.getSecond());
        }

    }

    public static void main(String[] args) {

        TaskEngine taskEngine = new TaskEngine("test1") ;
        taskEngine.setClass("com.jkl.saas.socamps.mailer.task.MailerTask");
        taskEngine.setSchedule( RecuringTask.WEEKLY );
        taskEngine.setDescription("Testing ");
        taskEngine.setStartDate( 1 , 10 , 2018 );
        // taskEngine.setStartTime(13, 20, 15);

        taskEngine.setEndDate(3, 12, 2018);
        // taskEngine.setEndTime(15, 0, 0);

        taskEngine.setRunTime(14, 25, 13);

        taskEngine.setScheduleRun( RecuringTask.SUNDAY );
        taskEngine.setScheduleRuFrequency( RecuringTask.THURSDAY );
        //taskEngine.setCustomDay( 5 );

        try {
            System.out.println( taskEngine.addTask().toString() ) ;
        } catch (Exception e) {
            e.printStackTrace();
        }

        // ScheduleDetails scheduleDetails =  new ScheduleDetails() ;
        // scheduleDetails.setScheduleID(new Long(1));
        // scheduleDetails.setStartTime(new Long("1541010600600"));
        // scheduleDetails.setEndTime(new Long("1546507800600"));
        // scheduleDetails.setIsRecuring(1);
        // scheduleDetails.setNextRecuringTime(new Long("1541062513601"));
        // scheduleDetails.setLastRunnedTime(new Long("0"));
        // scheduleDetails.setCreatedTime(new Long("1540966018600"));
        // scheduleDetails.setEndTime(new Long("1540966018600"));
        // scheduleDetails.setExcecutedStatus(1);

        // taskEngine.getTaskDetails().setScheduleDetails(scheduleDetails);
        // try {
        //     ScheduleGenerator scheduleGenerator = new ScheduleGenerator(taskEngine.getTaskDetails());
        //     System.out.println( " scheduleGenerator : " + scheduleGenerator.getNewSchedule().toString() ) ;
        // } catch (ScheduleException e1) {
        //     // TODO Auto-generated catch block
        //     e1.printStackTrace();
        // } catch (Exception e1) {
        //     // TODO Auto-generated catch block
        //     e1.printStackTrace();
        //  }
    }
}