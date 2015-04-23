package com.example.yogesh16991.test_proj;

import android.content.Context;
import android.graphics.Color;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
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
    private final Context mContext;
    private final List<Map<String, ?>>  eventDetailsJson;
    private DisplayMetrics metrics_; ///////////////////////////////////

    public MyBaseAdapter_List(Context context, List<Map<String, ?>> strings,DisplayMetrics metrics){
        mContext = context;
        eventDetailsJson = strings;
        metrics_ = metrics;
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
        return eventDetailsJson.size();
    }

    @Override
    public Object getItem(int position) {
        return eventDetailsJson.get(position);
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
            LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            rowView = inflater.inflate(R.layout.list_row, parent, false);
            holder = new ViewHolder();
            holder.title = (TextView) rowView.findViewById(R.id.place_title);


            rowView.setTag(holder);
        } else {
            rowView = convertView;
            holder = (ViewHolder) rowView.getTag();

        }
        //Toast.makeText(context, position, Toast.LENGTH_SHORT).show();


        holder.title.setText(eventDetailsJson.get(position).get("EventName").toString());
        Animation animation = null;
        animation = new TranslateAnimation(metrics_.widthPixels / 2, 0,
                0, 0);
/*        if(position%2==0){
            holder.title.setTextColor(Color.YELLOW);
        } else {
            holder.title.setTextColor(Color.RED);
        }*/
        rowView.startAnimation(animation);
        return rowView;
    }
}
