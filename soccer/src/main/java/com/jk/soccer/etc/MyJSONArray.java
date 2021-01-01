package com.jk.soccer.etc;

import org.json.JSONArray;

public class MyJSONArray {

    public MyJSONArray(String string) {
        try{
            val = new JSONArray(string);
        } catch (Exception e){
            printLog(e);
        }
    }

    public MyJSONArray(JSONArray val) {
        this.val = val;
    }

    public MyJSONObject getJSONObject(int index){
        if (!check(index))
            return null;
        try {
            return new MyJSONObject(val.getJSONObject(index));
        } catch (Exception e){
            printLog(e);
            return null;
        }
    }

    public MyJSONArray getJSONArray(int index){
        if (!check(index))
            return null;
        try{
            return new MyJSONArray(val.getJSONArray(index));
        } catch (Exception e){
            printLog(e);
            return null;
        }
    }

    public String getString(int index){
        if (!check(index))
            return null;
        try {
            return val.getString(index);
        } catch (Exception e){
            printLog(e);
            return null;
        }
    }

    public Integer getInt(int index){
        if (!check(index))
            return null;
        try{
            return val.getInt(index);
        } catch (Exception e){
            printLog(e);
            return null;
        }
    }

    public int length(){
        return val.length();
    }

    final static private String logTag = "MYJSONArray";
    private JSONArray val;

    private boolean check(int index){
        if (index >= val.length())
            return false;
        return !val.isNull(index);
    }

    private static void printLog(Exception e){
        MyException.printLog(e, logTag);
    }

}
