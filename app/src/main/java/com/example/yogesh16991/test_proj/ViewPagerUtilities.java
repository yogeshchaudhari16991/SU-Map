package com.example.yogesh16991.test_proj;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by HP on 22-04-2015.
 */
public class ViewPagerUtilities {
    private Context mcontext;
    List<Map<String,?>> eventsList;
    private int moption = 0;


    EventDetailsJSon eventData;
    public ViewPagerUtilities(Context context){
       try {
            eventData = new EventDetailsJSon(context);
       } catch (JSONException e) {
           e.printStackTrace();
       }
    }

    public Fragment createHashmap(int option)
    {
        String eventdesc = null;
        String markertitle = null;
        String eventname = null;
        String category = null;
        Map<String,?> event;
        List<Map<String,?>> NewEventsList = new ArrayList<Map<String,?>>();

        eventsList = new ArrayList<Map<String,?>>();
        eventsList = eventData.getEventsList();
        String mcategory = setCategory(option);

        for(int i = 0;i<eventData.getSize();i++){
            event = eventsList.get(i);
            category = (String)event.get("Category");
            eventname = (String) event.get("EventName");
            eventdesc = (String) event.get("EventDesc");
            markertitle = (String)event.get("MarkerTitle");
            Log.e("view pager category ","mcategory "+mcategory);
            if(mcategory.equals(category)){
                NewEventsList.add(createEvent(eventname,eventdesc,markertitle,category));
            }
        }
        return EventList.newInstance(NewEventsList,1);

    }

    private HashMap createEvent(String eventname,String eventdesc,String markertitle,String category) {
        HashMap event = new HashMap();
        event.put("EventName",eventname);
        event.put("EventDesc",eventdesc);
        event.put("MarkerTitle",markertitle);
        event.put("Category",category);
        return event;
    }

    public String setCategory(int option){
        String mcategory;
        switch (option) {
            case 0 :
                mcategory = "educational";
                break;
            case 1:
                mcategory = "food";
                break;
            case 2:
                mcategory = "sports";
                break;
            case 3:
                mcategory = "entertainment";
                break;
            case 4:
                mcategory = "others";
                break;

            default:
                mcategory = "educational";
                break;

        }
        return mcategory;
    }

}
