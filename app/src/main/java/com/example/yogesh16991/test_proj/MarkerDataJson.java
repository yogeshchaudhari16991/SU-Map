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

public class MarkerDataJson {

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
        pName = "Hendricks Chapel";
        lat = 43.03763;
        lng =-76.135121;
        noOfEvents = 2;
        desc = "Hendricks Chapel is the diverse religious, spiritual, ethical and cultural heart of Syracuse University that connects people of all faiths and no faith through active engagement, mutual dialogue, reflective spirituality, responsible leadership and a rigorous commitment to social justice.";
        img = R.drawable.hendricks_chapel;
        markersList.add(createMarker(lat,lng,pName,noOfEvents,desc,img));
        pName = "Bird Library";
        lat=43.03989;
        lng = -76.132602;
        noOfEvents=2;
        desc= "Bird Library is the main library on campus and houses materials in the humanities and social sciences. ";
        img = R.drawable.bird_lib;
        markersList.add(createMarker(lat,lng,pName,noOfEvents,desc,img));
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
}