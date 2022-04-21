package com.zlz.websocialplatform.utils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

public class MyUtils {

    public static String randomCode(){
        return String.valueOf(new Random().nextInt(899999)+100000);
    }

    public static String dateOfNow(){
        return new SimpleDateFormat("yyyy-MM-hh HH:mm:ss").format(new Date());
    }

}
