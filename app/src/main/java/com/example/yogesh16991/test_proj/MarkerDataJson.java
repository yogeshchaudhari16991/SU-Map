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
 * Created by rajasave on 4/20/2015.
 */
public class MarkerDataJson {
    private Context mcontext;
    List<Map<String,?>> eventsList;

    public List<Map<String, ?>> getMoviesList() {
        return eventsList;
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



    public MarkerDataJson(Context context) throws JSONException {
       /* String description = null;
        String title = null;
        String desit = null;*/
        double lat = 0;
        double lng = 0;
        String Pname = null;
        JSONArray eventsJsonArray = null;
        JSONObject eventJsonObj = null;
        eventsList = new ArrayList<Map<String,?>>();
        String eventsArray = loadEventJSONFromAsset(context);
        eventsJsonArray = new JSONArray(eventsArray);
        for(int i = 0; i <eventsJsonArray.length();i++){
            eventJsonObj = (JSONObject) eventsJsonArray.get(i);
            if(eventJsonObj != null) {
                lat = (double) eventJsonObj.get("lat");
                lng = (double) eventJsonObj.get("lng");
                Pname =  eventJsonObj.get("MarkerTitle").toString();
            }
            eventsList.add(createEvent(lat,lng,Pname));

        }
    }


    private HashMap createEvent(double lat,double lng,String Pname) {
        HashMap event = new HashMap();
        event.put("lat",lat);
        event.put("lng",lng);
        event.put("MarkerTitle",Pname);
        return event;
    }

    public String loadEventJSONFromAsset(Context context) {
        String json = null;
        try {
            InputStream is = context.getResources().openRawResource(R.raw.marker);
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
