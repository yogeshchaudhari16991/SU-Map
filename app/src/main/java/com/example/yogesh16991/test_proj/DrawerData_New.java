package com.example.yogesh16991.test_proj;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by HP on 07-03-2015.
 */
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

        List.add(createList("Dialog",R.drawable.abc_btn_radio_material,1));
        List.add(createList("MovieList LinearLayout",R.drawable.abc_btn_radio_material,1));
        List.add(createList("MovieList GridLayout",R.drawable.abc_btn_radio_material,1));
        List.add(createList(null,R.drawable.abc_btn_radio_material,2));
        List.add(createList("About Me",0,3));
        List.add(createList("Hello World",0,3));
        List.add(createList("Exit",0,3));

    }


    private HashMap createList(String name, int image, int type){

        HashMap list = new HashMap();
        list.put("image",image);
        list.put("name", name);
        list.put("type",type);

        return list;
    }
}
