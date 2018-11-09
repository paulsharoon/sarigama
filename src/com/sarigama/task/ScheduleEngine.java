package com.sarigama.task;

import java.io.Serializable;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.Timer;

import com.sarigama.task.db.ScheduleDB;
import com.sarigama.task.exception.ScheduleException;


public class ScheduleEngine {

    //Default if not exit create the timing
    static final Long scheduleTimingID = new Long(99) ;

    static int minutes =  5  ;
    static int seconds = 60 ;
    static int milliseconds = 1000 ;

    public void start() throws ScheduleException{
        try {

            Calendar calendar = Calendar.getInstance(); 
            Long currentTime = calendar.getTimeInMillis();
            ScheduleDB scheduleDB = new ScheduleDB() ;

            Map<String,Serializable> sceduleTimingDetails = new HashMap<String,Serializable>();
            sceduleTimingDetails.put( "scheduleTimingID" , ScheduleEngine.scheduleTimingID ) ;

            if( !scheduleDB.getLastExcecutedDetails(sceduleTimingDetails)){
                sceduleTimingDetails.put("startTime" , currentTime );
                sceduleTimingDetails.put("endTime" , currentTime );
                if(!scheduleDB.addScheduleTiming(sceduleTimingDetails)){
                    throw new ScheduleException("Error, while Start Schedule Engine") ;
                }
            }

            Timer timer = new Timer();
            ScheduleTask scheduleTask = new ScheduleTask() ;
            timer.schedule( scheduleTask , 1000  ,  minutes * seconds * milliseconds );
            
        } catch (Exception e) {
           
        }
    }

    public static void main(String arg[]) throws ScheduleException {
       new ScheduleEngine().start() ;
    }

}