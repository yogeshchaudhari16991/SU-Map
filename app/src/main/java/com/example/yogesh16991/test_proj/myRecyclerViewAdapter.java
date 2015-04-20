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
    List<Map<String, ?>> mDataset;


    public myRecyclerViewAdapter(Context mycontext,List<Map<String, ?>> myDataset) {
        context = mycontext;
         mDataset= myDataset;

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
        Map<String, ?> event = mDataset.get(position);
        viewHolder.bindEventData(event);
    }

    @Override
    public int getItemViewType(int position){
        //nothing

        return position;
    }


    public int getItemCount() {
        return mDataset.size();
    }
    //@Override
    public Object getItem(int position) {
        return mDataset.get(position);
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

        public TextView title;
        public TextView desit;


        public ViewHolder(View v) {
            super(v);
                title = (TextView) v.findViewById(R.id.title);
                desit = (TextView) v.findViewById(R.id.desit);




                v.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (mitemClickListener != null) {
                            mitemClickListener.onItemClick(v, getPosition());
                        }
                    }
                });

                v.setOnLongClickListener(new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View v) {
                        if (mitemClickListener != null) {
                            mitemClickListener.onItemLongClick(v, getPosition());
                        }
                        return true;
                    }
                });

                }




        public void bindEventData(Map<String,?>event) {
            if(title!=null)
                title.setText((String) event.get("title"));
            if(desit!=null)
               desit.setText((String) event.get("desit"));
        }


    }

}
