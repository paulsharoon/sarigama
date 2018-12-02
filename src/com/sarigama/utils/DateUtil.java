package com.sarigama.utils;

import java.util.Calendar;

public class DateUtil {

    public static Long getCurrentTime(){
        Calendar calendar = Calendar.getInstance(); 

        return calendar.getTimeInMillis();
    }

    public static Long calculateDate(int days){
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE, days);

        return calendar.getTimeInMillis();
    }
}