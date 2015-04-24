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

/**
 * Created by HP on 20-04-2015.
 */
public class EventDetailsJSon{
    List<Map<String,?>> eventsList;

    public List<Map<String, ?>> getEventsList() {
       return eventsList;
    }
    public static final String MyPREFERENCES = "MyPrefs";
    SharedPreferences sharedpreferences;
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

    public EventDetailsJSon(Context context){
        String eventDesc = null;
        String markerTitle = null;
        String eventName = null;
        String category = null;
        eventsList = new ArrayList<Map<String,?>>();
        sharedpreferences = context.getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);

    }



    private HashMap<String, String> createEvent(String eventname,String eventdesc,String markertitle,String category,String sdate, String edate) {
        int size;
        //Log.e("createEvent", "inCreate event");
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
        editor.putBoolean("ultimateJugad",true);

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


    public String loadEventJSONFromAsset(Context context) {
        String json = null;
        try {
            InputStream is = context.getResources().openRawResource(R.raw.events);
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
        return json;
    }

}
