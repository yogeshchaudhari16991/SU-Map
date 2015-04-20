package com.example.yogesh16991.test_proj;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Yogesh16991 on 2/5/2015.
 */

class ViewHolder{
    TextView title;

}

public class MyBaseAdapter_List extends BaseAdapter {
    private final Context context;
    private final String[] titleList;

    public MyBaseAdapter_List(Context context, String[] strings){
        this.context = context;
        this.titleList = strings;
    }



    @Override
    public boolean isEnabled(int position){
        if(position==0 || position==1 || position==2){

            return false;
        }
        return true;
    }

    @Override
    public int getCount() {
        return titleList.length;
    }

    @Override
    public Object getItem(int position) {
        return titleList[position];
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }



    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View rowView;
        ViewHolder holder = null;
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            rowView = inflater.inflate(R.layout.list_row, parent, false);
            holder = new ViewHolder();
            holder.title = (TextView) rowView.findViewById(R.id.place_title);


            rowView.setTag(holder);
        } else {
            rowView = convertView;
            holder = (ViewHolder) rowView.getTag();

        }
        //Toast.makeText(context, position, Toast.LENGTH_SHORT).show();


        holder.title.setText(titleList[position]);


        if(position%2==0){
            holder.title.setTextColor(Color.YELLOW);
        } else {
            holder.title.setTextColor(Color.RED);
        }

        return rowView;
    }
}
