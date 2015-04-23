package com.example.yogesh16991.test_proj;

import android.content.Context;

import com.google.android.gms.maps.model.Marker;

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
    List<Map<String,?>> markersList1;

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

    public MarkerDataJson() {
        double lat = 0;
        double lng = 0;
        String pName = null;
        int noOfEvents = 0;
        String desc = null;
        int img= 0;
        markersList = new ArrayList<Map<String, ?>>();
        //#1-10
        pName = "Carrier Dome";
        lat = 43.035924;
        lng = -76.136265;
        noOfEvents = 2;
        img = R.drawable.carrier_dome_night;
        desc="The Carrier Dome is a 49,262-seat domed sports stadium located on the campus of Syracuse University in the University Hill neighborhood of Syracuse, New York.";
        markersList.add(createMarker(lat,lng,pName,noOfEvents,desc,img));
        pName = "Life Sciences Complex";
        lat = 43.038099;
        lng = -76.130557;
        noOfEvents = 2;
        img = R.drawable.lcs;
        desc = "The 230,000 square-foot, state-of-the-art Life Sciences Complex at Syracuse University is a vital instructional facility, a major research center, a training ground for future scientists, and a place of discovery for all who enter.";
        markersList.add(createMarker(lat,lng,pName,noOfEvents,desc,img));
        pName = "Quad";
        lat = 43.037535;
        lng = -76.133913;
        desc = "Syracuse University's Kenneth A. Shaw quadrangle, affectionately known as The Quad, is an open green space designed to be accessible, safe, and attractive and to be used by members of the University community and their guests.";
        noOfEvents = 2;
        img = R.drawable.quad;
        markersList.add(createMarker(lat,lng,pName,noOfEvents,desc,img));
    }

        public MarkerDataJson(Context context) throws JSONException {
       /* String description = null;
        String title = null;
        String desit = null;*/
        double lat = 0;
        double lng = 0;
        String Pname = null;
        int NoOfEvents = 0;
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
                NoOfEvents = Integer.parseInt(markerJsonObj.get("NoOfEvents").toString());
                MarkerDescription =  markerJsonObj.get("MarkerDescription").toString();
                img = ((String) markerJsonObj.get("img"));
                resID = context.getResources().getIdentifier(img, "drawable", context.getPackageName());
            }
            markersList.add(createMarker(lat, lng, Pname, NoOfEvents, MarkerDescription, img, resID));

        }
    }

    private HashMap createMarker(double lat,double lng,String Pname,int NoOfEvents,String MarkerDescription, int img) {
        HashMap marker = new HashMap();
        marker.put("lat",lat);
        marker.put("lng",lng);
        marker.put("MarkerTitle",Pname);
        marker.put("NoOfEvents",NoOfEvents);
        marker.put("MarkerDescription",MarkerDescription);
        marker.put("img",img);
        return marker;
    }



    private HashMap createMarker(double lat,double lng,String Pname,int NoOfEvents,String MarkerDescription, String img, int resID) {
        HashMap marker = new HashMap();
        marker.put("lat",lat);
        marker.put("lng",lng);
        marker.put("MarkerTitle",Pname);
        marker.put("NoOfEvents",NoOfEvents);
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