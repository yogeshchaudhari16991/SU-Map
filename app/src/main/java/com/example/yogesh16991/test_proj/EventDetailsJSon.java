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
    private Context mcontext;
    List<Map<String,?>> eventsList;
    private String mcategory = null;
    private int option = 0;

    public List<Map<String, ?>> getMoviesList() {
        return eventsList;
    }



    public List<Map<String, ?>> getEventsList(int option) {
        setCategory(option);
        createEventList();
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



    public EventDetailsJSon(Context context) throws JSONException {
        mcontext = context;
        setCategory(option);
        createEventList();

    }
    public void createEventList(){
        String eventdesc = null;
        String markertitle = null;
        String eventname = null;
        String category = null;
        JSONArray eventsJsonArray = null;
        JSONObject eventJsonObj = null;
        eventsList = new ArrayList<Map<String,?>>();
        String eventsArray = loadEventJSONFromAsset(mcontext);
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
            if (option == 0)
                eventsList.add(createEvent(eventname,eventdesc,markertitle,category));
            else if(category == mcategory)
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
        event.put("MarkerTitle",markertitle);
        event.put("Category",category);
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
    public void setCategory(int option){
        switch (option) {
            case 2 :
                mcategory = "educational";
                break;
            case 3:
                mcategory = "food";
                break;
            case 4:
                mcategory = "sports";
                break;
            case 5:
                mcategory = "entertainment";
                break;
            case 6:
                mcategory = "others";
                break;

            default:
                mcategory = "educational";
                break;

        }
    }
}
