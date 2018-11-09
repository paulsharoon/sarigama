
package com.sarigama.task;


import java.util.TimerTask;

import com.sarigama.task.db.ScheduleDB;
import com.sarigama.task.db.tables.ScheduleDetails;
import com.sarigama.task.db.tables.TaskDetails;


public class TaskRunnable extends TimerTask {


    TaskDetails taskDetails ;

    public TaskRunnable( TaskDetails taskDetails ) {
        this.taskDetails = taskDetails ;
    }

    @Override
    public void run() {
        Class cls;
        try {

            cls = Class.forName( this.taskDetails.getTaskExcute() );
            Task taskObject;
            try {
                taskObject = (Task) cls.newInstance();
                taskObject.execute( taskDetails );
            }catch (IllegalAccessException e) {
                e.printStackTrace();
            }
            
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        }

        try {
            ScheduleGenerator scheduleGenerator = new ScheduleGenerator( this.taskDetails );
            ScheduleDetails scheduleDetails  = scheduleGenerator.getNewSchedule() ;

            ScheduleDB scheduleDB = new ScheduleDB() ;
            if(!scheduleDB.updateSchedule(scheduleDetails)){
              // logger.info( "Schedule details not updated") ;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
       
    }

    public static void main(String arg[]){
        // TaskRunnable taskRunnable  = new TaskRunnable() ;
        // taskRunnable.setTaskID(new Long(1));
        // taskRunnable.setRunClassName("com.jkl.saas.socamps.mailer.task.MailerTask");
        // taskRunnable.start() ;

        // Timer timer = new Timer();
        // timer.schedule(new TaskRunnable( new Long(1) , "com.jkl.saas.socamps.mailer.task.MailerTask" ), 5000);
    }
}