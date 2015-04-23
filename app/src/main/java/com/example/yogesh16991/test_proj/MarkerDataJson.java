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
    List<Map<String,?>> markersList;

   public List<Map<String, ?>> getMarkersList() {
        return markersList;
    }

    public int getSize(){
        return markersList.size();
    }

    public HashMap getItem(int i){
        if (i >=0 && i < markersList.size()){
            return (HashMap) markersList.get(i);
        } else return null;
    }

    public MarkerDataJson(Context context) throws JSONException {
       /* String description = null;
        String title = null;
        String desit = null;*/
        double lat = 0;
        double lng = 0;
        String Pname = null;
        String NoOfMarkers= null;
        String MarkerDescription = null;
        String img= null;
        int resID = 0;
        JSONArray markersJsonArray = null;
        JSONObject markerJsonObj = null;
        markersList = new ArrayList<Map<String,?>>();
        String markersArray = loadMarkerJSONFromAsset(context);
        markersJsonArray = new JSONArray(markersArray);
        for(int i = 0; i <markersJsonArray.length();i++){
            markerJsonObj = (JSONObject) markersJsonArray.get(i);
            if(markerJsonObj != null) {
                lat = (double) markerJsonObj.get("lat");
                lng = (double) markerJsonObj.get("lng");
                Pname =  markerJsonObj.get("MarkerTitle").toString();
                NoOfMarkers = markerJsonObj.get("NoOfMarkers").toString();
                MarkerDescription =  markerJsonObj.get("MarkerDescription").toString();
                img = ((String) markerJsonObj.get("img"));
                resID = context.getResources().getIdentifier(img,"drawable",context.getPackageName());
            }
            markersList.add(createMarker(lat,lng,Pname,NoOfMarkers,MarkerDescription,img,resID));

        }
    }

    private HashMap createMarker(double lat,double lng,String Pname,String NoOfMarkers,String MarkerDescription, String img, int resID) {
        HashMap marker = new HashMap();
        marker.put("lat",lat);
        marker.put("lng",lng);
        marker.put("MarkerTitle",Pname);
        marker.put("NoOfMarkers",NoOfMarkers);
        marker.put("MarkerDescription",MarkerDescription);
        marker.put("img",img);
        marker.put("resID",resID);
        return marker;
    }

    public String loadMarkerJSONFromAsset(Context context) {
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