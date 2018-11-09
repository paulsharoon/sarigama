package com.sarigama.task.db;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import com.sarigama.task.db.tables.RecuringTaskConfig;
import com.sarigama.task.db.tables.ScheduleDetails;
import com.sarigama.task.db.tables.TaskConfig;
import com.sarigama.task.db.tables.TaskConfigEnd;
import com.sarigama.task.db.tables.TaskConfigRun;
import com.sarigama.task.db.tables.TaskConfigStart;
import com.sarigama.task.db.tables.TaskDetails;


public class ScheduleDB  //extends DatabaseAbstract 
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
            //logger.error(e.getMessage(), e);
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
			//logger.error(e.getMessage(), e);
		}
    }

    public boolean addSchedule( ScheduleDetails scheduleDetails){
        boolean toRet = false ;
        PreparedStatement prepStatement = null;
        try {

           Long uniqueScheduleId = getScheduleUniqueID();

           if( uniqueScheduleId == null ){
               return toRet ;
           }
           
           scheduleDetails.setScheduleID(uniqueScheduleId);

            String sqlString = "INSERT INTO SCHEDULE(SCHEDULE_ID,START_TIME,END_TIME,IS_RECURING ,NEXT_RECURING_TIME ,LAST_RUNNED_TIME,EXCECUTED_STATUS ,CREATED_TIME, UPDATED_TIME) " ;
                   sqlString += " VALUES( ? , ? , ? , ? , ? , ? , ? , ? , ? ) ;" ;
            //System.out.println( sqlString ) ;
            this.getConnection();
            prepStatement = this.connection.prepareStatement( sqlString );
           
            prepStatement.setLong(1, scheduleDetails.getScheduleID() );
            prepStatement.setLong(2, scheduleDetails.getStartTime());
            prepStatement.setLong(3, scheduleDetails.getEndTime());
            prepStatement.setInt(4, scheduleDetails.getIsRecuring());
            prepStatement.setLong(5, scheduleDetails.getNextRecuringTime());
            prepStatement.setLong(6,  scheduleDetails.getLastRunnedTime());
            prepStatement.setInt(7,  scheduleDetails.getExcecutedStatus());
            prepStatement.setLong(8,  scheduleDetails.getCreatedTime());
            prepStatement.setLong(9,  scheduleDetails.getEndTime());

            prepStatement.execute();

            toRet = true ;

        } catch(Exception e){  //logger.error(e.getMessage(), e); 
         }
        finally{
            try { 
            if (null != prepStatement) { prepStatement.close() ; } 
            this.finalize();
            }catch(Exception e){ //logger.error(e.getMessage(), e);
             }
        }

        return toRet ;
    }

    public boolean updateSchedule( ScheduleDetails scheduleDetails ){
        boolean toRet = false ;
        PreparedStatement prepStatement = null;
        try {
            String sqlString = "UPDATE SCHEDULE " ;
            sqlString += " SET NEXT_RECURING_TIME = ? " ;
            sqlString += " , LAST_RUNNED_TIME = ? " ;
            sqlString += " , EXCECUTED_STATUS = ?  " ;
            sqlString += " , UPDATED_TIME = ? " ;
            sqlString += " WHERE SCHEDULE_ID = ?" ;

            this.getConnection();
            prepStatement = this.connection.prepareStatement( sqlString );
            prepStatement.setLong(1, scheduleDetails.getNextRecuringTime());
            prepStatement.setLong(2, scheduleDetails.getLastRunnedTime());
            prepStatement.setInt(3, scheduleDetails.getExcecutedStatus());
            prepStatement.setLong(4, scheduleDetails.getUpdatedTime());
            prepStatement.setLong(5, scheduleDetails.getScheduleID());
            System.out.println( prepStatement ) ;

            prepStatement.execute();

            toRet = true ;
        } catch(Exception e){  //logger.error(e.getMessage(), e); 
         }
        finally{
            try { 
            if (null != prepStatement) { prepStatement.close() ; } 
            this.finalize();
            }catch(Exception e){ //logger.error(e.getMessage(), e); 
            }
        }
        return toRet ;
    }

    public boolean updateScheduleTiming(Map<String,Serializable> sceduleTimingDetails ){
        boolean toRet = false ;
        PreparedStatement prepStatement = null;
        try {
            String sqlString = "UPDATE SCEDULE_EXCECUTED_TIMINIG " ;
            sqlString += " SET START_TIME = ? " ;
            sqlString += " , END_TIME = ? " ;
            sqlString += " WHERE SCEDULE_TIMING_ID = ?" ;

            this.getConnection();
            prepStatement = this.connection.prepareStatement( sqlString );
            prepStatement.setLong(1, (Long) sceduleTimingDetails.get("startTime") );
            prepStatement.setLong(2, (Long) sceduleTimingDetails.get("endTime") );
            prepStatement.setLong(3, (Long) sceduleTimingDetails.get("scheduleTimingID") );
            System.out.println( prepStatement ) ;

            prepStatement.execute();

            toRet = true ;
        } catch(Exception e){  //logger.error(e.getMessage(), e);  
        }
        finally{
            try { 
            if (null != prepStatement) { prepStatement.close() ; } 
            this.finalize();
            }catch(Exception e){ //logger.error(e.getMessage(), e);
             }
        }
        return toRet ;
    }

    public boolean addScheduleTiming(Map<String,Serializable> sceduleTimingDetails ){
        boolean toRet = false ;
        PreparedStatement prepStatement = null;
        try {
            String sqlString = "INSERT INTO SCEDULE_EXCECUTED_TIMINIG(SCEDULE_TIMING_ID , START_TIME , END_TIME) " ;
            sqlString += " VALUES( ? , ? , ? ) " ;
           
            this.getConnection();
            prepStatement = this.connection.prepareStatement( sqlString );
            prepStatement.setLong( 1 , (Long) sceduleTimingDetails.get("scheduleTimingID") );
            prepStatement.setLong( 2 , (Long) sceduleTimingDetails.get("startTime") );
            prepStatement.setLong( 3 , (Long) sceduleTimingDetails.get("endTime") );
          
            System.out.println( prepStatement ) ;

            prepStatement.execute();

            toRet = true ;
        } catch(Exception e){  //logger.error(e.getMessage(), e); 
         }
        finally{
            try { 
            if (null != prepStatement) { prepStatement.close() ; } 
            this.finalize();
            }catch(Exception e){ //logger.error(e.getMessage(), e);
             }
        }
        return toRet ;
    }

    public boolean getLastExcecutedDetails(Map<String,Serializable> sceduleTimingDetails){
        boolean toRet = false ;
        PreparedStatement prepStatement = null;
        ResultSet resultSet = null ;
        try {
            String sqlString = "SELECT START_TIME , END_TIME FROM SCEDULE_EXCECUTED_TIMINIG WHERE SCEDULE_TIMING_ID = ? ;" ;

            this.getConnection();
            prepStatement = this.connection.prepareStatement( sqlString );
            prepStatement.setLong(1, (Long) sceduleTimingDetails.get("scheduleTimingID") );
        
            System.out.println( prepStatement ) ;

            resultSet = prepStatement.executeQuery();

            if(resultSet.next())
            { 
                sceduleTimingDetails.put("startTime" , resultSet.getLong("START_TIME") );
                sceduleTimingDetails.put("endTime" , resultSet.getLong("END_TIME") );

                toRet = true ;
            }

            
        } catch(Exception e){  //logger.error(e.getMessage(), e); 
         }
        finally{
            try { 
            if (null != prepStatement) { prepStatement.close() ; } 
            this.finalize();
            }catch(Exception e){ //logger.error(e.getMessage(), e);
             }
        }
        return toRet ;
    }

    public Long getScheduleUniqueID(){
        Long toRet = new Long(1) ;
        PreparedStatement prepStatement = null;
        ResultSet resultSet = null ;
        try {

            String sqlString = "SELECT MAX( SCHEDULE_ID ) AS UNIQUE_ID FROM SCHEDULE" ;
            //System.out.println( sqlString ) ;
            this.getConnection();
            prepStatement = this.connection.prepareStatement( sqlString );
            resultSet = prepStatement.executeQuery();
            if(resultSet.next())
            { 
                toRet = resultSet.getLong("UNIQUE_ID") + 1 ;
            }
           
        } catch(Exception e){  //logger.error(e.getMessage(), e); 
        }
        finally{
            try { 
            if (null != prepStatement) { prepStatement.close() ; } 
            if (null != resultSet) { resultSet.close() ; } 
            this.finalize();
            }catch(Exception e){ //logger.error(e.getMessage(), e);
             }
        }
        
        return toRet ;
    }

    public boolean getTaskForScheduling(List<TaskDetails> tasks , int excecuteStatus ,Long excecuteStartTime , Long excecuteEndTime ){
        boolean toRet = false ;
        PreparedStatement prepStatement = null;
        ResultSet resultSet = null ;
        try {

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
            sqlString += " WHERE S.EXCECUTED_STATUS = ? " ;
            sqlString += " AND S.NEXT_RECURING_TIME >= ? " ;
            sqlString += " AND S.NEXT_RECURING_TIME <= ? " ;
           
            
            this.getConnection();
            prepStatement = this.connection.prepareStatement( sqlString );
            prepStatement.setLong(1 , excecuteStatus );
            prepStatement.setLong(2 , excecuteStartTime );
            prepStatement.setLong(3 , excecuteEndTime );
            System.out.println( prepStatement );
            
            //prepStatement.execute();
            resultSet = prepStatement.executeQuery();
            while(resultSet.next())
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

               TaskDetails taskDetails = new TaskDetails( resultSet.getLong("T_TASK_ID") ) ;
               taskDetails.setTaskName(resultSet.getString("T_TASK_NAME"));
               taskDetails.setTaskExcute(resultSet.getString("T_TASK_EXECUTE"));
               taskDetails.setTaskDescription(resultSet.getString("T_TASK_DESCRIPTION"));
               taskDetails.setTaskPriority(resultSet.getInt("T_TASK_PRIORITY"));
               taskDetails.setTaskStatus(resultSet.getInt("T_TASK_STATUS"));
               taskDetails.setCreatedTime(resultSet.getLong("T_CREATED_TIME"));
               taskDetails.setUpdatedTime(resultSet.getLong("T_UPDATED_TIME"));

               taskDetails.setTaskConfig(taskConfig);
               taskDetails.setScheduleDetails(scheduleDetails);

               tasks.add( taskDetails ) ;
            }
            toRet = true ;
        } catch(Exception e){  //logger.error(e.getMessage(), e); 
        }
        finally{
            try { 
            if (null != prepStatement) { prepStatement.close() ; } 
            if (null != resultSet) { resultSet.close() ; } 
            this.finalize();
            }catch(Exception e){ //logger.error(e.getMessage(), e);
             }
        }
        return toRet ;
    }


}