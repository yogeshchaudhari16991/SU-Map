package com.example.yogesh16991.test_proj;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
public class DrawerData_New {

    java.util.List<Map<String,?>> List;

    public java.util.List<Map<String, ?>> getList() {
        return List;
    }

    public int getSize(){
        return List.size();
    }

    public HashMap getItem(int i){
        if (i >=0 && i < List.size()){
            return (HashMap) List.get(i);
        } else return null;
    }

    public DrawerData_New() {
        List = new ArrayList<Map<String,?>>();

        List.add(createList("Map View",R.drawable.map_icon,1));
        List.add(createList("List All Events",R.drawable.eventlist_icon,1));
        List.add(createList("Add Event",R.drawable.addevent_icon,1));
        List.add(createList("List categories",R.drawable.ic_action_select_all,1));
        List.add(createList(null,R.drawable.abc_btn_radio_material,2));
        List.add(createList("About Us",0,3));
    }

    private HashMap createList(String name, int image, int type){
        HashMap list = new HashMap();
        list.put("image",image);
        list.put("name", name);
        list.put("type",type);
        return list;
    }
}
