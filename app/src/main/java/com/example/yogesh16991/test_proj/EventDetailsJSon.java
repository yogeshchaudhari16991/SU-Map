package com.example.yogesh16991.test_proj;

import android.content.Context;

import org.json.JSONArray;
import org.json.JSONException;
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

    public int getSize(){
        return eventsList.size();
    }

    public HashMap getItem(int i){
        if (i >=0 && i < eventsList.size()){
            return (HashMap) eventsList.get(i);
        } else return null;
    }

    public void addEvent(String name,String desc,String marker,String category){
        eventsList.add(createEvent(name,desc,marker,category));
    }

    public EventDetailsJSon(){
        String eventDesc = null;
        String markerTitle = null;
        String eventName = null;
        String category = null;
        eventsList = new ArrayList<Map<String,?>>();
        eventDesc = "Set the tone for your special event right on the bat with an invitation that sets the tone for the entire event. Whether you want your event to be formal or casual, classic or trendy, our large selection of invitations will be sure to meet your needs.";
        eventName = "iProm";
        category = "sports";
        markerTitle = "Carrier Dome";
        eventsList.add(createEvent(eventName,eventDesc,markerTitle,category));
        eventDesc = "Come and Catch the World Series with Corn & Kelly Corporation!";
        eventName = "Cricket";
        category = "educational";
        markerTitle = "Life Sciences Complex";
        eventsList.add(createEvent(eventName,eventDesc,markerTitle,category));


    }

    public EventDetailsJSon(Context context) throws JSONException {
        String eventdesc = null;
        String markertitle = null;
        String eventname = null;
        String category = null;
        JSONArray eventsJsonArray = null;
        JSONObject eventJsonObj = null;
        eventsList = new ArrayList<Map<String,?>>();
        String eventsArray = loadEventJSONFromAsset(context);
        try {
            eventsJsonArray = new JSONArray(eventsArray);

        for(int i = 0; i <eventsJsonArray.length();i++){
            eventJsonObj = (JSONObject) eventsJsonArray.get(i);
            if(eventJsonObj != null) {
                eventname = (String) eventJsonObj.get("EventName");
                eventdesc = (String) eventJsonObj.get("EventDesc");
                markertitle = (String)eventJsonObj.get("MarkerTitle");
                category = (String)eventJsonObj.get("category");

            }
            eventsList.add(createEvent(eventname,eventdesc,markertitle,category));

        }
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }


    private HashMap createEvent(String eventname,String eventdesc,String markertitle,String category) {
        HashMap event = new HashMap();
        event.put("EventName",eventname);
        event.put("EventDesc",eventdesc);
        event.put("MarkerTitle", markertitle);
        event.put("Category", category);
        return event;
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
