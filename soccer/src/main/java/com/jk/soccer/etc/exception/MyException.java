package com.jk.soccer.etc.exception;

import android.util.Log;

public class MyException {

    public static void printLog(Exception e, String tag){
        Log.e(tag, e.getMessage());
    }

}
