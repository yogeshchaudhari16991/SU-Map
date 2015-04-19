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

    private List<Map<String, ?>> mDataset;
    private Context context;
    private OnItemClickListener mitemClickListener;
    int moption;

    public myRecyclerViewAdapter(Context mycontext, List<Map<String, ?>> myDataset, int option) {
        context = mycontext;
        mDataset = myDataset;
        moption = option;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v;
        if(moption == 0)
            v = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.card_view, parent, false);
        else if(moption == 1)
            v = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.card_view, parent, false);
        else
            v = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.card_view, parent, false);

        ViewHolder vh = new ViewHolder(v);
        return vh;

    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int position) {
        Map<String, ?> movie = mDataset.get(position);
        viewHolder.bindMovieData(movie);
    }

    @Override
    public int getItemViewType(int position){
        Map<String, ?> movie = mDataset.get(position);
        float rating = Float.parseFloat(String.valueOf(movie.get("rating")));
        if(rating>=8.0)
            return 1;
        else
            return 0;
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

    public void duplicate(int position,Map<String, ?> item) {
        mDataset.add(position + 1, item);
    }
    public void delete(int position){
       mDataset.remove(position);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView image;
        public TextView name;
        public TextView desc;
        public RatingBar ratingBar;
        public ImageView vMenu;

        public ViewHolder(View v) {
            super(v);
            image = (ImageView) v.findViewById(R.id.image);
            name = (TextView) v.findViewById(R.id.name);
            desc = (TextView) v.findViewById(R.id.desc);
            ratingBar = (RatingBar) v.findViewById(R.id.ratingBar);



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
            if(vMenu!=null){
                vMenu.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(mitemClickListener!=null){
                            mitemClickListener.onOverflowMenuClick(v,getPosition());
                        }
                    }
                });
            }
        }

        public void bindMovieData(Map<String, ?> movie) {
            if(name!=null)
            name.setText((String) movie.get("name"));
            if(desc!=null)
            desc.setText((String) movie.get("description"));
            if(image!=null)
            image.setImageResource((Integer) movie.get("image"));
            if(ratingBar!=null)
            ratingBar.setRating((Float.parseFloat(String.valueOf(movie.get("rating")))) / 2);

        }


    }

}
