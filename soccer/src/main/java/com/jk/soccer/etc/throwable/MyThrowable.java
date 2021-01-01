package com.jk.soccer.etc.throwable;

import android.util.Log;

public class MyThrowable {

    public static void printLog(String tag, Throwable t){
        Log.e(tag, t.getMessage());
    }

}
