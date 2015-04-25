package com.example.yogesh16991.test_proj;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import org.json.JSONObject;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
public class EventDetailsJSon{

    public static final String MyPREFERENCES = "MyPrefs";
    SharedPreferences sharedpreferences;
    List<Map<String,?>> eventsList;

    public EventDetailsJSon(Context context){
       eventsList = new ArrayList<Map<String,?>>();
       sharedpreferences = context.getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
    }

    public List<Map<String, ?>> getEventsList() {
       return eventsList;
    }

    public int getSize(){
        return eventsList.size();
    }

    public HashMap getItem(int i){
        if (i >=0 && i < eventsList.size()){
            return (HashMap) eventsList.get(i);
        } else return null;
    }

    public void addEvent(String name,String desc,String marker,String category,String sdate,String edate){
        eventsList.add(createEvent(name,desc,marker,category,sdate,edate));
    }

    private HashMap<String, String> createEvent(String eventname,String eventdesc,String markertitle,String category,String sdate, String edate) {
        int size;
        HashMap<String, String> event = new HashMap<>();
        event.put("EventName",eventname);
        event.put("EventDesc",eventdesc);
        event.put("MarkerTitle", markertitle);
        event.put("Category", category);
        event.put("Sdate",sdate);
        event.put("Edate",edate);
        SharedPreferences.Editor editor= sharedpreferences.edit();
        size = sharedpreferences.getInt("size",0);
        saveMap(event, "event"+size);
        size++;
        editor.putInt("size",size).apply();
        editor.putBoolean("Check",true);

        return event;
    }

    private void saveMap(HashMap<String,String> inputMap, String mapStr){
        if (sharedpreferences!= null){
            JSONObject jsonObject = new JSONObject(inputMap);
            String jsonString = jsonObject.toString();
            SharedPreferences.Editor editor = sharedpreferences.edit();
            editor.putString(mapStr, jsonString);
            editor.apply();
        }
    }
}
