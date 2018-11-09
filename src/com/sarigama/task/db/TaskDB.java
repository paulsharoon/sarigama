package com.sarigama.task.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import com.sarigama.task.ScheduleGenerator;
import com.sarigama.task.db.tables.RecuringTaskConfig;
import com.sarigama.task.db.tables.ScheduleDetails;
import com.sarigama.task.db.tables.TaskConfig;
import com.sarigama.task.db.tables.TaskConfigEnd;
import com.sarigama.task.db.tables.TaskConfigRun;
import com.sarigama.task.db.tables.TaskConfigStart;
import com.sarigama.task.db.tables.TaskDetails;


public class TaskDB  
{

    protected Connection connection = null;
    public final void getConnection() throws Exception
	{
        try{
       	if (connection == null) 
		{
            Class.forName("com.mysql.jdbc.Driver");  
            connection = DriverManager.getConnection( "jdbc:mysql://localhost:3306/APERTAINDB??characterEncoding=UTF-8&amp;allowMultiQueries=true&amp;sessionVariables=sql_mode=''","aptappuser","s932kjkl@tech2k12apt932k");
        }
        }catch(Exception e)
        {
            ////logger.error(e.getMessage(), e);
        }
    }

    public final void finalize()
	{
		try
		{
			if(connection != null || connection.isClosed() == false)
			{
				connection.close(); connection = null;
			}
 	    }
		catch(SQLException e)
		{
			////logger.error(e.getMessage(), e);
		}
    }

    public boolean addTask( TaskDetails taskDetails ){
        boolean toRet = false ;
        PreparedStatement prepStatement = null;
        try {

           Long uniqueTaskId = getTaskUniqueID();

           if( uniqueTaskId == null ){
               return toRet ;
           }

           taskDetails.setTaskID(uniqueTaskId);

            String sqlString = "INSERT INTO TASK(TASK_ID , TASK_NAME , TASK_DESCRIPTION , TASK_EXECUTE , CREATED_TIME , UPDATED_TIME , TASK_PRIORITY , TASK_STATUS) " ;
                   sqlString += " VALUES( ? , ? , ? , ? , ? , ? , ? , ? ) ;" ;
            //System.out.println( sqlString ) ;
            this.getConnection();
            prepStatement = this.connection.prepareStatement( sqlString );
           
            prepStatement.setLong(1, taskDetails.getTaskID() );
            prepStatement.setString(2, taskDetails.getTaskName());
            prepStatement.setString(3, taskDetails.getTaskDescription());
            prepStatement.setString(4, taskDetails.getTaskExcute());
            prepStatement.setLong(5, taskDetails.getCreatedTime());
            prepStatement.setLong(6,  taskDetails.getUpdatedTime());
            prepStatement.setInt(7,  taskDetails.getTaskPriority());
            prepStatement.setInt(8,  taskDetails.getTaskStatus());

            prepStatement.execute();

            toRet = true ;
        } catch(Exception e){  ////logger.error(e.getMessage(), e); 
         }
        finally{
            try { 
            if (null != prepStatement) { prepStatement.close() ; } 
            this.finalize();
            }catch(Exception e){ ////logger.error(e.getMessage(), e); 
            }
        }

        return toRet ;
    }

    public boolean addTaskScheduleMap( TaskDetails taskDetails ){ 
        boolean toRet = false ;
        PreparedStatement prepStatement = null;

        try {
            if( taskDetails.getTaskID() == null ||  taskDetails.getScheduleDetails().getScheduleID() == null){
                return toRet ;
            }

            String sqlString = "INSERT INTO TASK_SCHEDULE_MAP(TASK_ID , SCHEDULE_ID ) " ;
            sqlString += " VALUES( ? , ?  ) ;" ;
            //System.out.println( sqlString ) ;
            this.getConnection();
            prepStatement = this.connection.prepareStatement( sqlString );
            
            prepStatement.setLong(1, taskDetails.getTaskID() );
            prepStatement.setLong(2, taskDetails.getScheduleDetails().getScheduleID() );

            prepStatement.execute();

            toRet = true ;            
        } catch(Exception e){  ////logger.error(e.getMessage(), e); 
        }
        finally{
            try { 
            if (null != prepStatement) { prepStatement.close() ; } 
            this.finalize();
            }catch(Exception e){ ////logger.error(e.getMessage(), e); 
            }
        }

        return toRet ;
    }

    public Long getTaskUniqueID(){
        Long toRet = new Long(1) ;
        PreparedStatement prepStatement = null;
        ResultSet resultSet = null ;
        try {
            String sqlString = "SELECT MAX( TASK_ID ) AS UNIQUE_ID FROM TASK" ;
            //System.out.println( sqlString ) ;
            this.getConnection();
            prepStatement = this.connection.prepareStatement( sqlString );
            prepStatement.execute();
            resultSet = prepStatement.executeQuery();
            if(resultSet.next())
            { 
                toRet = resultSet.getLong("UNIQUE_ID") +  1 ;
            }
           
        } catch(Exception e){  ////logger.error(e.getMessage(), e); 
        }
        finally{
            try { 
            if (null != prepStatement) { prepStatement.close() ; } 
            if (null != resultSet) { resultSet.close() ; } 
            this.finalize();
            }catch(Exception e){ ////logger.error(e.getMessage(), e); 
            }
        }
        return toRet ;
    }


    public Long getUniqueID( String whereClauseColoumn ,  String maxColoumn , String tableName ,  Long whereConditionValue ){
        Long toRet = new Long(1) ;
        PreparedStatement prepStatement = null;
        ResultSet resultSet = null ;
        try {
            String sqlString = "SELECT MAX( " + maxColoumn + " )  AS UNIQUE_ID FROM " + tableName + " WHERE " + whereClauseColoumn + "  = ? ;" ;
            //System.out.println( sqlString ) ;
            this.getConnection();
            prepStatement = this.connection.prepareStatement( sqlString );
            prepStatement.setLong(1, whereConditionValue );
            prepStatement.execute();
            resultSet = prepStatement.executeQuery();
            if(resultSet.next())
            { 
            	toRet = resultSet.getLong("UNIQUE_ID") +  1 ;
            }
           
        } catch(Exception e){  ////logger.error(e.getMessage(), e);
         }
        finally{
            try { 
            if (null != prepStatement) { prepStatement.close() ; } 
            if (null != resultSet) { resultSet.close() ; } 
            this.finalize();
            }catch(Exception e){ ////logger.error(e.getMessage(), e);
             }
        }
        return toRet ;
    }

    public boolean addTaskConfigStart( TaskConfigStart taskConfigStart){
        boolean toRet = false ;
        PreparedStatement prepStatement = null;
        try {

           Long uniqueStartId = getUniqueID("TASK_ID" , "TASK_START_CONFIG_ID" , "TASK_START_CONFIG" ,  taskConfigStart.getTaskID()  );

           if( uniqueStartId == null ){
               return toRet ;
           }

           taskConfigStart.setTaskStartConfigID(uniqueStartId);

            String sqlString = "INSERT INTO TASK_START_CONFIG(TASK_ID,TASK_START_CONFIG_ID,YEAR,MONTH,DAY,HOUR,MINUTE,SECOND) " ;
                   sqlString += " VALUES( ? , ? , ? , ? , ? , ? , ? , ? ) ;" ;
            //System.out.println( sqlString ) ;
            this.getConnection();
            prepStatement = this.connection.prepareStatement( sqlString );
           
            prepStatement.setLong(1, taskConfigStart.getTaskID() );
            prepStatement.setLong(2, taskConfigStart.getTaskStartConfigID());
            prepStatement.setInt(3, taskConfigStart.getYear());
            prepStatement.setInt(4, taskConfigStart.getMonth());
            prepStatement.setInt(5, taskConfigStart.getDay());
            prepStatement.setInt(6,  taskConfigStart.getHour());
            prepStatement.setInt(7,  taskConfigStart.getMinute());
            prepStatement.setInt(8,  taskConfigStart.getSecond());

            prepStatement.execute();

            toRet = true ;
        } catch(Exception e){  ////logger.error(e.getMessage(), e);
         }
        finally{
            try { 
            if (null != prepStatement) { prepStatement.close() ; } 
            this.finalize();
            }catch(Exception e){ ////logger.error(e.getMessage(), e); 
            }
        }

        return toRet ;
    }

    public boolean addTaskConfigEnd( TaskConfigEnd taskConfigEnd){
        boolean toRet = false ;
        PreparedStatement prepStatement = null;
        try {

           Long uniqueEndId = getUniqueID("TASK_ID" , "TASK_END_CONFIG_ID" ,  "TASK_END_CONFIG" ,   taskConfigEnd.getTaskID()  );

           if( uniqueEndId == null ){
               return toRet ;
           }

           taskConfigEnd.setTaskEndConfigID(uniqueEndId);

            String sqlString = "INSERT INTO TASK_END_CONFIG(TASK_ID,TASK_END_CONFIG_ID,YEAR,MONTH,DAY,HOUR,MINUTE,SECOND) " ;
                   sqlString += " VALUES( ? , ? , ? , ? , ? , ? , ? , ? ) ;" ;
            //System.out.println( sqlString ) ;
            this.getConnection();
            prepStatement = this.connection.prepareStatement( sqlString );
           
            prepStatement.setLong(1, taskConfigEnd.getTaskID() );
            prepStatement.setLong(2, taskConfigEnd.getTaskEndConfigID());
            prepStatement.setInt(3, taskConfigEnd.getYear());
            prepStatement.setInt(4, taskConfigEnd.getMonth());
            prepStatement.setInt(5, taskConfigEnd.getDay());
            prepStatement.setInt(6,  taskConfigEnd.getHour());
            prepStatement.setInt(7,  taskConfigEnd.getMinute());
            prepStatement.setInt(8,  taskConfigEnd.getSecond());

            prepStatement.execute();

            toRet = true ;
        } catch(Exception e){  ////logger.error(e.getMessage(), e); 
         }
        finally{
            try { 
            if (null != prepStatement) { prepStatement.close() ; } 
            this.finalize();
            }catch(Exception e){ ////logger.error(e.getMessage(), e);
             }
        }

        return toRet ;
    }

    public boolean addTaskConfigRun( TaskConfigRun taskConfigRun){
        boolean toRet = false ;
        PreparedStatement prepStatement = null;
        try {

           Long uniqueRunId = getUniqueID("TASK_ID" , "TASK_RUN_CONFIG_ID" ,  "TASK_RUN_CONFIG" ,  taskConfigRun.getTaskID()  );

           if( uniqueRunId == null ){
               return toRet ;
           }

           taskConfigRun.setTaskRunConfigID(uniqueRunId);

            String sqlString = "INSERT INTO TASK_RUN_CONFIG(TASK_ID,TASK_RUN_CONFIG_ID,HOUR,MINUTE,SECOND) " ;
                   sqlString += " VALUES( ? , ? , ? , ? , ?  ) ;" ;
            //System.out.println( sqlString ) ;
            this.getConnection();
            prepStatement = this.connection.prepareStatement( sqlString );
           
            prepStatement.setLong(1, taskConfigRun.getTaskID() );
            prepStatement.setLong(2, taskConfigRun.getTaskRunConfigID());
            prepStatement.setInt(3,  taskConfigRun.getHour());
            prepStatement.setInt(4,  taskConfigRun.getMinute());
            prepStatement.setInt(5,  taskConfigRun.getSecond());

            prepStatement.execute();

            toRet = true ;
        } catch(Exception e){  ////logger.error(e.getMessage(), e);
          }
        finally{
            try { 
            if (null != prepStatement) { prepStatement.close() ; } 
            this.finalize();
            }catch(Exception e){ ////logger.error(e.getMessage(), e);
             }
        }

        return toRet ;
    }

    public boolean addTaskConfig( TaskConfig taskConfig){
        boolean toRet = false ;
        PreparedStatement prepStatement = null;
        try {

           Long uniqueConfigId = getUniqueID("TASK_ID" , "TASK_CONFIG_ID" ,  "TASK_CONFIG" ,   taskConfig.getTaskID()  );

           if( uniqueConfigId == null ){
               return toRet ;
           }

           taskConfig.setTaskConfigID(uniqueConfigId);

            String sqlString = "INSERT INTO TASK_CONFIG(TASK_ID,TASK_CONFIG_ID,TASK_START_CONFIG_ID,TASK_RUN_CONFIG_ID,TASK_END_CONFIG_ID,RECCURRING_TASK_CONFIG_ID,CUSTOM_DAY,TASK_CONFIG_STATUS) " ;
                   sqlString += "  VALUES( ? , ? , ? , ? , ? , ? , ? , ? ) ;" ;
            //System.out.println( sqlString ) ;
            this.getConnection();
            prepStatement = this.connection.prepareStatement( sqlString );
           
            prepStatement.setLong(1, taskConfig.getTaskID() );
            prepStatement.setLong(2, taskConfig.getTaskConfigID());
            prepStatement.setLong(3,  taskConfig.getTaskConfigStartID());
            prepStatement.setLong(4,  taskConfig.getTaskConfigRunID());
            prepStatement.setLong(5,  taskConfig.getTaskConfigEndID());
            prepStatement.setLong(6,  taskConfig.getRucuringTaskCongfigID());
            prepStatement.setInt(7,  taskConfig.getCustomDay());
            prepStatement.setInt(8,  taskConfig.getTaskConfigStatus());
            
            prepStatement.execute();

            toRet = true ;
        } catch(Exception e){  ////logger.error(e.getMessage(), e); 
         }
        finally{
            try { 
            if (null != prepStatement) { prepStatement.close() ; } 
            this.finalize();
            }catch(Exception e){ ////logger.error(e.getMessage(), e); 
            }
        }
        return toRet ;
    }

    public boolean getRecuringTaskConfig( RecuringTaskConfig recuringTaskConfig){
        boolean toRet = false ;
        PreparedStatement prepStatement = null;
        ResultSet resultSet = null ;
        try { 

            String sqlString = "SELECT RECCURRING_TASK_CONFIG_ID FROM RECCURRING_TASK_CONFIG " ;
            sqlString += " WHERE RECCURRING_TASK_ID = ? AND RECCURRING_RUN_TYPE = ? AND RECCURRING_RUN_FREQUENCY = ? ;  " ;
            this.getConnection();
            System.out.println(recuringTaskConfig.toString());
           
            prepStatement = this.connection.prepareStatement( sqlString );
            prepStatement.setInt(1, recuringTaskConfig.getRecuringTaskID() );
            prepStatement.setInt(2, recuringTaskConfig.getRecuringRunType() );
            prepStatement.setInt(3, recuringTaskConfig.getRecuringRunFrequency() );

            System.out.println( prepStatement );
            resultSet = prepStatement.executeQuery();
            if(resultSet.next())
            { 
                System.out.println( resultSet.getLong("RECCURRING_TASK_CONFIG_ID") );
                recuringTaskConfig.setRecuringConfigID(resultSet.getLong("RECCURRING_TASK_CONFIG_ID") );
            }

            toRet = true ;
        } catch(Exception e){  ////logger.error(e.getMessage(), e); 
        }
        finally{
            try { 
            if (null != prepStatement) { prepStatement.close() ; } 
            if (null != resultSet) { resultSet.close() ; } 
            this.finalize();
            }catch(Exception e){ ////logger.error(e.getMessage(), e);
             }
        }
        return toRet ; 
    }

    public boolean deleteExceptionTask(  TaskDetails taskDetails , ScheduleDetails scheduleDetails ){
        boolean toRet = false ;
        PreparedStatement prepStatement = null;
        try {
            this.getConnection();
            Long taskID = taskDetails.getTaskID() ;
            System.out.println(taskDetails.toString());
            if( taskID != null ){
                
                String sqlString = "DELETE FROM TASK_CONFIG WHERE TASK_ID = ?" ;
                prepStatement = this.connection.prepareStatement( sqlString );
                prepStatement.setLong(1, taskID );
                // System.out.println(prepStatement);
                prepStatement.execute();

                sqlString = "DELETE FROM TASK_START_CONFIG WHERE TASK_ID = ?" ;
                prepStatement = this.connection.prepareStatement( sqlString );
                prepStatement.setLong(1, taskID );
                // System.out.println(prepStatement);
                prepStatement.execute();

                sqlString = "DELETE FROM TASK_END_CONFIG WHERE TASK_ID = ?" ;
                prepStatement = this.connection.prepareStatement( sqlString );
                prepStatement.setLong(1, taskID );
                // System.out.println(prepStatement);
                prepStatement.execute();

                sqlString = "DELETE FROM TASK_RUN_CONFIG WHERE TASK_ID = ?" ;
                prepStatement = this.connection.prepareStatement( sqlString );
                prepStatement.setLong(1, taskID );
                // System.out.println(prepStatement);
                prepStatement.execute();

                if( taskDetails.getScheduleDetails() != null  && taskDetails.getScheduleDetails().getScheduleID() != null){

                    Long scheduleID = taskDetails.getScheduleDetails().getScheduleID() ;

                    sqlString = "DELETE FROM TASK_SCHEDULE_MAP WHERE TASK_ID = ? AND SCHEDULE_ID = ?" ;
                    prepStatement = this.connection.prepareStatement( sqlString );
                    prepStatement.setLong(1, taskID );
                    prepStatement.setLong(2, scheduleID );
                    // System.out.println(prepStatement);
                    prepStatement.execute();

                   
                    sqlString = "DELETE FROM SCHEDULE WHERE SCHEDULE_ID = ?" ;
                    prepStatement = this.connection.prepareStatement( sqlString );
                    prepStatement.setLong(1, scheduleID );
                    // System.out.println(prepStatement);
                    prepStatement.execute();

                }else if( scheduleDetails != null && scheduleDetails.getScheduleID() != null ){

                    Long scheduleID = scheduleDetails.getScheduleID() ;

                    sqlString = "DELETE FROM SCHEDULE WHERE SCHEDULE_ID = ?" ;
                    prepStatement = this.connection.prepareStatement( sqlString );
                    prepStatement.setLong(1, taskID );
                    // System.out.println(prepStatement);
                    prepStatement.execute();
                }
               
                sqlString = "DELETE FROM TASK WHERE TASK_ID = ?" ;
                prepStatement = this.connection.prepareStatement( sqlString );
                prepStatement.setLong(1, taskID );
                // System.out.println(prepStatement);
                prepStatement.execute();

                toRet = true ;
            }
            
        }catch(Exception e){  ////logger.error(e.getMessage(), e); 
        }
        finally{
            try { 
            if (null != prepStatement) { prepStatement.close() ; } 
            this.finalize();
            }catch(Exception e){ ////logger.error(e.getMessage(), e);
             }
        }
        return toRet ;
    }
    
    public boolean getTask( TaskDetails taskDetails , int isTaskOrSchedule){
        boolean toRet = false ;
        PreparedStatement prepStatement = null;
        ResultSet resultSet = null ;
        try {

            if( taskDetails == null || taskDetails.getTaskID() == null ){
                return toRet ;
            }

            String sqlString = "SELECT  " ;
            sqlString += " T.TASK_ID AS T_TASK_ID , T.TASK_NAME AS T_TASK_NAME ,T.TASK_DESCRIPTION AS T_TASK_DESCRIPTION ,T.TASK_EXECUTE AS T_TASK_EXECUTE,T.CREATED_TIME AS T_CREATED_TIME,T.UPDATED_TIME  AS T_UPDATED_TIME , T.TASK_PRIORITY  AS T_TASK_PRIORITY ,T.TASK_STATUS  AS T_TASK_STATUS," ;
            sqlString += " S.SCHEDULE_ID AS S_SCHEDULE_ID, S.START_TIME AS S_START_TIME,S.END_TIME AS S_END_TIME,S.IS_RECURING AS S_IS_RECURING,S.NEXT_RECURING_TIME AS S_NEXT_RECURING_TIME,S.LAST_RUNNED_TIME AS S_LAST_RUNNED_TIME,S.EXCECUTED_STATUS AS S_EXCECUTED_STATUS ,S.CREATED_TIME AS S_CREATED_TIME,S.UPDATED_TIME AS S_UPDATED_TIME," ;
            sqlString += " TSM.TASK_ID AS TSM_TASK_ID,TSM.SCHEDULE_ID AS TSM_SCHEDULE_ID," ;
            sqlString += " TC.TASK_ID AS TC_TASK_ID,TC.TASK_CONFIG_ID AS TC_TASK_CONFIG_ID ,TC.TASK_START_CONFIG_ID AS TC_TASK_START_CONFIG_ID,TC.TASK_RUN_CONFIG_ID AS TC_TASK_RUN_CONFIG_ID ,TC.TASK_END_CONFIG_ID AS TC_TASK_END_CONFIG_ID," ;
            sqlString += " TC.RECCURRING_TASK_CONFIG_ID AS TC_RECCURRING_TASK_CONFIG_ID ,TC.CUSTOM_DAY AS TC_CUSTOM_DAY ,TC.TASK_CONFIG_STATUS AS TC_TASK_CONFIG_STATUS ," ;
            sqlString += " RTC.RECCURRING_TASK_CONFIG_ID AS RTC_RECCURRING_TASK_CONFIG_ID,RTC.RECCURRING_TASK_ID AS RTC_RECCURRING_TASK_ID,RTC.RECCURRING_RUN_TYPE AS RTC_RECCURRING_RUN_TYPE," ;
            sqlString += " RTC.RECCURRING_RUN_FREQUENCY AS RTC_RECCURRING_RUN_FREQUENCY,RTC.RECCURRING_TASK_DESCRIPTION AS RTC_RECCURRING_TASK_DESCRIPTION," ;
            sqlString += " TSC.TASK_ID AS TSC_TASK_ID,TSC.TASK_START_CONFIG_ID  AS TSC_TASK_START_CONFIG_ID ,TSC.YEAR  AS TSC_YEAR,TSC.MONTH  AS TSC_MONTH,TSC.DAY  AS TSC_DAY,TSC.HOUR  AS TSC_HOUR,TSC.MINUTE  AS TSC_MINUTE,TSC.SECOND  AS TSC_SECOND," ;
            sqlString += " TEC.TASK_ID AS TEC_TASK_ID,TEC.TASK_END_CONFIG_ID AS TEC_TASK_END_CONFIG_ID,TEC.YEAR AS TEC_YEAR,TEC.MONTH AS TEC_MONTH,TEC.DAY AS TEC_DAY,TEC.HOUR AS TEC_HOUR,TEC.MINUTE AS TEC_MINUTE,TEC.SECOND AS TEC_SECOND," ;
            sqlString += " TRC.TASK_ID AS TRC_TASK_ID,TRC.TASK_RUN_CONFIG_ID AS TRC_TASK_RUN_CONFIG_ID,TRC.HOUR AS TRC_HOUR ,TRC.MINUTE AS TRC_MINUTE ,TRC.SECOND AS TRC_SECOND" ;
            sqlString += " FROM TASK T" ;
            sqlString += " LEFT JOIN TASK_SCHEDULE_MAP TSM ON T.TASK_ID = TSM.TASK_ID" ;
            sqlString += " LEFT JOIN SCHEDULE S ON TSM.SCHEDULE_ID = S.SCHEDULE_ID" ;
            sqlString += " LEFT JOIN TASK_CONFIG TC ON T.TASK_ID = TC.TASK_ID " ;
            sqlString += " LEFT JOIN RECCURRING_TASK_CONFIG RTC ON TC.RECCURRING_TASK_CONFIG_ID = RTC.RECCURRING_TASK_CONFIG_ID" ;
            sqlString += " LEFT JOIN TASK_START_CONFIG TSC ON TC.TASK_ID = TSC.TASK_ID AND TC.TASK_START_CONFIG_ID = TSC.TASK_START_CONFIG_ID" ;
            sqlString += " LEFT JOIN TASK_END_CONFIG TEC ON TC.TASK_ID = TEC.TASK_ID AND TC.TASK_END_CONFIG_ID = TEC.TASK_END_CONFIG_ID" ;
            sqlString += " LEFT JOIN TASK_RUN_CONFIG TRC ON TC.TASK_ID = TRC.TASK_ID AND TC.TASK_RUN_CONFIG_ID = TRC.TASK_RUN_CONFIG_ID";
            if( isTaskOrSchedule == 1){
                sqlString += " WHERE TSM.TASK_ID = ? " ;
            }else if(  isTaskOrSchedule == 2 ){
                sqlString += " WHERE TSM.SCHEDULE_ID = ? " ;
            }
            
            this.getConnection();
            prepStatement = this.connection.prepareStatement( sqlString );
            if( isTaskOrSchedule == 1){
                prepStatement.setLong(1, taskDetails.getTaskID() );
            }else if(  isTaskOrSchedule == 2 ){
                prepStatement.setLong(1, taskDetails.getScheduleDetails().getScheduleID() );
            }
            System.out.println( prepStatement );
            
            //prepStatement.execute();
            resultSet = prepStatement.executeQuery();
            if(resultSet.next())
            { 
               TaskConfigStart taskConfigStart =  new TaskConfigStart() ;
               TaskConfigEnd taskConfigEnd =  new TaskConfigEnd() ;
               TaskConfigRun taskConfigRun = new TaskConfigRun() ;
               TaskConfig taskConfig = new TaskConfig() ;
               ScheduleDetails scheduleDetails = new ScheduleDetails();
               RecuringTaskConfig recuringTaskConfig = new RecuringTaskConfig();

               scheduleDetails.setScheduleID(resultSet.getLong("S_SCHEDULE_ID"));
               scheduleDetails.setStartTime(resultSet.getLong("S_START_TIME"));
               scheduleDetails.setEndTime(resultSet.getLong("S_END_TIME"));
               scheduleDetails.setIsRecuring(resultSet.getInt("S_IS_RECURING"));
               scheduleDetails.setNextRecuringTime(resultSet.getLong("S_NEXT_RECURING_TIME"));
               scheduleDetails.setLastRunnedTime(resultSet.getLong("S_LAST_RUNNED_TIME"));
               scheduleDetails.setExcecutedStatus(resultSet.getInt("S_EXCECUTED_STATUS"));
               scheduleDetails.setCreatedTime(resultSet.getLong("S_CREATED_TIME"));
               scheduleDetails.setUpdatedTime(resultSet.getLong("S_UPDATED_TIME"));

               recuringTaskConfig.setRecuringConfigID(resultSet.getLong("RTC_RECCURRING_TASK_CONFIG_ID"));
               recuringTaskConfig.setRecuringPeriodeDescription(resultSet.getString("RTC_RECCURRING_TASK_DESCRIPTION"));
               recuringTaskConfig.setRecuringTaskID(resultSet.getInt("RTC_RECCURRING_TASK_ID"));
               recuringTaskConfig.setRecuringRunType(resultSet.getInt("RTC_RECCURRING_RUN_TYPE"));
               recuringTaskConfig.setRecuringRunFrequency(resultSet.getInt("RTC_RECCURRING_RUN_FREQUENCY"));

               taskConfigStart.setTaskID(resultSet.getLong("TSC_TASK_ID"));
               taskConfigStart.setTaskStartConfigID(resultSet.getLong("TSC_TASK_START_CONFIG_ID"));
               taskConfigStart.setYear(resultSet.getInt("TSC_YEAR"));
               taskConfigStart.setMonth(resultSet.getInt("TSC_MONTH"));
               taskConfigStart.setDay(resultSet.getInt("TSC_DAY"));
               taskConfigStart.setHour(resultSet.getInt("TSC_HOUR"));
               taskConfigStart.setMinute(resultSet.getInt("TSC_MINUTE"));
               taskConfigStart.setSecond(resultSet.getInt("TSC_SECOND"));

               taskConfigEnd.setTaskID(resultSet.getLong("TEC_TASK_ID"));
               taskConfigEnd.setTaskEndConfigID(resultSet.getLong("TEC_TASK_END_CONFIG_ID"));
               taskConfigEnd.setYear(resultSet.getInt("TEC_YEAR"));
               taskConfigEnd.setMonth(resultSet.getInt("TEC_MONTH"));
               taskConfigEnd.setDay(resultSet.getInt("TEC_DAY"));
               taskConfigEnd.setHour(resultSet.getInt("TEC_HOUR"));
               taskConfigEnd.setMinute(resultSet.getInt("TEC_MINUTE"));
               taskConfigEnd.setSecond(resultSet.getInt("TEC_SECOND"));

               taskConfigRun.setTaskID(resultSet.getLong("TRC_TASK_ID"));
               taskConfigRun.setTaskRunConfigID(resultSet.getLong("TRC_TASK_RUN_CONFIG_ID"));
               taskConfigRun.setHour(resultSet.getInt("TRC_HOUR"));
               taskConfigRun.setMinute(resultSet.getInt("TRC_MINUTE"));
               taskConfigRun.setSecond(resultSet.getInt("TRC_SECOND"));

               taskConfig.setTaskID(resultSet.getLong("TC_TASK_ID"));
               taskConfig.setTaskConfigID(resultSet.getLong("TC_TASK_CONFIG_ID"));
               taskConfig.setTaskConfigStartID(resultSet.getLong("TC_TASK_START_CONFIG_ID"));
               taskConfig.setTaskConfigEndID(resultSet.getLong("TC_TASK_END_CONFIG_ID"));
               taskConfig.setTaskConfigRunID(resultSet.getLong("TC_TASK_RUN_CONFIG_ID"));
               taskConfig.setRucuringTaskCongfigID(resultSet.getLong("TC_RECCURRING_TASK_CONFIG_ID"));
               taskConfig.setCustomDay(resultSet.getInt("TC_CUSTOM_DAY"));
               taskConfig.setTaskConfigStatus(resultSet.getInt("TC_TASK_CONFIG_STATUS"));

               taskConfig.setTaskConfigStart(taskConfigStart);
               taskConfig.setTaskConfigEnd(taskConfigEnd);
               taskConfig.setTaskConfigRun(taskConfigRun);
               taskConfig.setRecuringTaskConfig(recuringTaskConfig);

               taskDetails.setTaskID(resultSet.getLong("T_TASK_ID"));
               taskDetails.setTaskName(resultSet.getString("T_TASK_NAME"));
               taskDetails.setTaskExcute(resultSet.getString("T_TASK_EXECUTE"));
               taskDetails.setTaskDescription(resultSet.getString("T_TASK_DESCRIPTION"));
               taskDetails.setTaskPriority(resultSet.getInt("T_TASK_PRIORITY"));
               taskDetails.setTaskStatus(resultSet.getInt("T_TASK_STATUS"));
               taskDetails.setCreatedTime(resultSet.getLong("T_CREATED_TIME"));
               taskDetails.setUpdatedTime(resultSet.getLong("T_UPDATED_TIME"));

               taskDetails.setTaskConfig(taskConfig);
               taskDetails.setScheduleDetails(scheduleDetails);

            }
            toRet = true ;
        } catch(Exception e){  ////logger.error(e.getMessage(), e); 
        }
        finally{
            try { 
            if (null != prepStatement) { prepStatement.close() ; } 
            if (null != resultSet) { resultSet.close() ; } 
            this.finalize();
            }catch(Exception e){ ////logger.error(e.getMessage(), e);
             }
        }
        return toRet ;
    }

   
    public static void main(String[] args) {
        try{
            TaskDB taskDB = new TaskDB();
            TaskDetails taskDetails = new TaskDetails(new Long(1)) ;
            taskDB.getTask(taskDetails, 1) ;
            System.out.println( taskDetails.toString() );

            ScheduleGenerator scheduleGenerator = new ScheduleGenerator( taskDetails );
            ScheduleDetails scheduleDetails  = scheduleGenerator.getNewSchedule() ;

            System.out.println( scheduleDetails.toString() );
        }catch(Exception e){

        }
    }
}