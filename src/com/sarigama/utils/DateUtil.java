package com.sarigama.utils;

import java.util.Calendar;

public class DateUtil {

    public static Long getCurrentTime(){
        Calendar calendar = Calendar.getInstance(); 
        Long currentTime = calendar.getTimeInMillis();

        return currentTime ;
    }
}