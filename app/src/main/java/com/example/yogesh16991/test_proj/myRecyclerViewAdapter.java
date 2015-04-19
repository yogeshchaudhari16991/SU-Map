package com.example.yogesh16991.test_proj;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import java.util.List;
import java.util.Map;

/**
 * Created by HP on 17-02-2015.
 */
public class myRecyclerViewAdapter extends RecyclerView.Adapter<myRecyclerViewAdapter.ViewHolder> {


    private Context context;
    private OnItemClickListener mitemClickListener;


    public myRecyclerViewAdapter(Context mycontext) {
        context = mycontext;

    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v;

            v = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.card_view, parent, false);

        ViewHolder vh = new ViewHolder(v);
        return vh;

    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int position) {
        //nothing
    }

    @Override
    public int getItemViewType(int position){
        //nothing

        return position;
    }


    public int getItemCount() {
        return 0;
    }
    //@Override
    public Object getItem(int position) {
        return null;
    }
    public interface OnItemClickListener {
        public void onItemClick(View view, int position);
        public void onItemLongClick(View view, int position);
        public void onOverflowMenuClick(View v, int position);
    }

    public void setOnItemClickListener(final OnItemClickListener mItemClickListener) {
        this.mitemClickListener = mItemClickListener;
    }

    public void duplicate() {
        //nothing
    }
    public void delete(){
       //nothing
    }

    public class ViewHolder extends RecyclerView.ViewHolder {


        public ViewHolder(View v) {
            super(v);

        }

        public void bindMovieData() {

        }


    }

}
