package com.jk.soccer.etc;

import org.json.JSONObject;

public class MyJSONObject {

    public MyJSONObject(String string) {
        try{
            val = new JSONObject(string);
        } catch (Exception e){
            printLog(e);
        }
    }

    public MyJSONObject(JSONObject val) {
        this.val = val;
    }

    public MyJSONObject getJSONObject(String string){
        if (!check(string))
            return null;
        try{
            return new MyJSONObject(val.getJSONObject(string));
        } catch (Exception e){
            printLog(e);
            return null;
        }
    }

    public MyJSONArray getJSONArray(String string){
        if (!check(string))
            return null;
        try{
            return new MyJSONArray(val.getJSONArray(string));
        } catch (Exception e){
            printLog(e);
            return null;
        }
    }

    public String getString(String string){
        if (!check(string))
            return null;
        try{
            return val.getString(string);
        } catch (Exception e){
            printLog(e);
            return null;
        }
    }

    public Integer getInt(String string){
        if (!check(string))
            return null;
        try{
            return val.getInt(string);
        } catch (Exception e){
            printLog(e);
            return null;
        }
    }

    public Boolean getBoolean(String string){
        if (!check(string))
            return null;
        try{
            return val.getBoolean(string);
        } catch (Exception e){
            printLog(e);
            return null;
        }
    }

    final static private String logTag = "MyJSONObject";
    private JSONObject val;

    private boolean check(String string){
        if (!val.has(string))
            return false;
        return !val.isNull(string);
    }

    private static void printLog(Exception e){
        MyException.printLog(e, logTag);
    }


}
