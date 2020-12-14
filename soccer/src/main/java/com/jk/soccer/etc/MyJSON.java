package com.jk.soccer.etc;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MyJSON {

    public static JSONObject myJSONObject(JSONObject jsonObject, String string) throws JSONException {
        if (jsonObject == null)
            return null;
        if (jsonObject.has(string)){
            return jsonObject.getJSONObject(string);
        }
        return null;
    }

    public static JSONArray myJSONArray(JSONObject jsonObject, String string) throws JSONException {
        if (jsonObject == null)
            return null;
        if (jsonObject.has(string)){
            return jsonObject.getJSONArray(string);
        }
        return null;
    }

    public static String myJSONString(JSONObject jsonObject, String string) throws JSONException {
        if (jsonObject == null)
            return "";
        if (jsonObject.has(string)){
            return jsonObject.getString(string);
        }
        return "";
    }

    public static Integer myJSONInt(JSONObject jsonObject, String string) throws JSONException {
        if (jsonObject == null)
            return 0;
        if (jsonObject.has(string)){
            return jsonObject.getInt(string);
        }
        return 0;
    }

    public static Boolean myJSONBoolean(JSONObject jsonObject, String string) throws JSONException {
        if (jsonObject == null)
            return false;
        if (jsonObject.has(string)){
            return jsonObject.getBoolean(string);
        }
        return false;
    }

}
