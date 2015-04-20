package com.example.yogesh16991.test_proj;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;
import java.util.Map;

/**
 * Created by HP on 07-03-2015.
 */
public class MyDrawerRecyclerViewAdapter extends RecyclerView.Adapter<MyDrawerRecyclerViewAdapter.ViewHolder> {
    Context context;
    List<Map<String,?>> mList;
    private OnItemClickListener mItemClickListener;
    int mCurrentPosition;

    public MyDrawerRecyclerViewAdapter(Context mycontext, List<Map<String, ?>> myDataset) {
        context = mycontext;
        mList = myDataset;
        mCurrentPosition = 0;

    }


    @Override
    public MyDrawerRecyclerViewAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View v;

        switch(viewType){
            case 1 :
               v = LayoutInflater.from(viewGroup.getContext())
                       .inflate(R.layout.drawerlist_type1,viewGroup,false);
                break;
            case 2:
                v = LayoutInflater.from(viewGroup.getContext())
                        .inflate(R.layout.drawerlist_type2,viewGroup,false);
                break;
            case 3:
                v = LayoutInflater.from(viewGroup.getContext())
                        .inflate(R.layout.drawerlist_type3,viewGroup,false);
                break;
             default:
                 v = LayoutInflater.from(viewGroup.getContext())
                         .inflate(R.layout.drawerlist_type1,viewGroup,false);
                 break;
        }
        ViewHolder vh = new ViewHolder(v,viewType);
        return vh;
    }

    @Override
    public void onBindViewHolder(MyDrawerRecyclerViewAdapter.ViewHolder viewHolder, int position) {
        Map<String,?> item = mList.get(position);
        viewHolder.bindData(item,position);
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public void setOnItemClickListener(final OnItemClickListener mItemClickListener) {
        this.mItemClickListener = mItemClickListener;
    }
    public interface OnItemClickListener {
        public void onItemClick(View view, int position);
        //public void onItemLongClick(View view, int position);
        //public void onOverflowMenuClick(View v, int position);
    }
    @Override
    public int getItemViewType(int position){
        Map<String, ?> item = mList.get(position);
       return (Integer) item.get("type");
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView image;
        public TextView name;

        View vView;
        int vViewType;


        public ViewHolder(View v, int viewType) {
            super(v);
            vView = v;
            image = (ImageView) v.findViewById(R.id.image);
            name = (TextView) v.findViewById(R.id.name);
            vViewType = viewType;


            v.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if (mItemClickListener != null) {
                        mItemClickListener.onItemClick(v, getPosition());

                    }
                    mCurrentPosition = getPosition();
                    notifyDataSetChanged();
                }
            });

        }


        public void bindData(Map<String, ?> item, int position) {
            if(position == mCurrentPosition)
                vView.setBackgroundColor(Color.LTGRAY);
            else
                vView.setBackgroundColor(0x00000000);
            switch (vViewType){
                case 1:
                    if (image != null)
                        image.setImageResource(((Integer)item.get("image")));
                    if(name !=null)
                        name.setText((String)item.get("name"));
                    break;
                case 3:
                    if(name !=null)
                        name.setText((String)item.get("name"));
                    break;
                case 2:
                    if(image != null)
                    image.setImageResource(((Integer)item.get("image")));
                    break;
                default:
                    break;

            }
        }
    }

}
