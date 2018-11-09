package com.sarigama.task;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import com.sarigama.task.db.ScheduleDB;
import com.sarigama.task.db.tables.ScheduleDetails;
import com.sarigama.task.db.tables.TaskDetails;



public class ScheduleTask extends TimerTask{

    static int minutes =  5  ;

   
    @Override
    public void run() {
        try {

            System.out.println("Coming ........") ;
            Calendar calendar = Calendar.getInstance(); 
            Long currentTime = calendar.getTimeInMillis();

            Timer timer = new Timer();
            ScheduleDB scheduleDB = new ScheduleDB() ;
           


            List<TaskDetails> tasks = new ArrayList<TaskDetails>();
            int excecuteStatus = ScheduleDetails.OPEN ;

            // get the schedule already excecuted details
            Map<String,Serializable> sceduleTimingDetails = new HashMap<String,Serializable>();
            sceduleTimingDetails.put( "scheduleTimingID" , ScheduleEngine.scheduleTimingID ) ;
            if(!scheduleDB.getLastExcecutedDetails( sceduleTimingDetails )){
                return ;
            }

            // set the start time and end time for tasks run
            Long excecuteStartTime =  (Long) sceduleTimingDetails.get( "endTime" ) ;
            Long excecuteEndTime =  currentTime + ( minutes * 60 * 1000 ) ;
            

            scheduleDB.getTaskForScheduling(tasks , excecuteStatus , excecuteStartTime , excecuteEndTime ) ;

            for(TaskDetails task : tasks ){
                
                if( task.getTaskID() != null && task.getScheduleDetails() != null && task.getScheduleDetails().getScheduleID() != null ){
                    Long excecuteTime = task.getScheduleDetails().getNextRecuringTime() ;
                    Date runningDate = new Date( excecuteTime) ;
                    TaskRunnable taskRunnable = new TaskRunnable( task ) ;
                    timer.schedule( taskRunnable , runningDate ) ;
                }

            }

            sceduleTimingDetails.put("startTime" ,excecuteStartTime );
            sceduleTimingDetails.put("endTime" , excecuteEndTime );
            if(!scheduleDB.updateScheduleTiming( sceduleTimingDetails )){
                return ;
            }
            

        } catch (Exception e) {
           
        }
    }

}