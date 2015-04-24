package com.example.yogesh16991.test_proj;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import org.json.JSONException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

//import android.app.Fragment;


/**
 * A simple {@link android.support.v4.app.Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link EventList.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link EventList#newInstance} factory method to
 * create an instance of this fragment.
 */
public class EventList extends Fragment {
    private static final String ARG_MOVIE = "movie";
//EventDetailsJSon eventData;
        RecyclerView recyclerView;
    OnFragmentInteractionListener mListener = null;
    public static List<Map<String, ?>> meventList;
    List<Map<String, ?>> meventList1;
        private static final String OPTION = "option";

    public static EventList newInstance(List<Map<String, ?>> eventList,int option) {
      EventList fragment = new EventList();
        Bundle args = new Bundle();
        args.putInt(OPTION, option);
        fragment.setArguments(args);
        meventList = eventList;
        fragment.setList();
        return fragment;

    }

    private void setList() {
        meventList1 = meventList;
    }

    public EventList() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //redundant code, already new Instance me eventDatilsJSon object is there
        /*
        try {
            eventData = new EventDetailsJSon(getActivity());
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        */

        setRetainInstance(true);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        int option;
        setRetainInstance(true);
        if(getArguments()!= null){ option = getArguments().getInt(OPTION);}
        else option = 0;
        Toast.makeText(getActivity(),"The option is "+option,Toast.LENGTH_SHORT).show();
        View rootView = null;
        final myRecyclerViewAdapter recyclerviewAdaptor;


        switch (option){
            case 0:
                rootView = inflater.inflate(R.layout.fragment_event_list, container, false);
                recyclerView = (RecyclerView) rootView.findViewById(R.id.listview);
                recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
                recyclerviewAdaptor = new myRecyclerViewAdapter(getActivity(),meventList);
                recyclerView.setAdapter(recyclerviewAdaptor);
                recyclerView.setHasFixedSize(true);
                Toast.makeText(getActivity(),"Swipe Right to Left to see Event Detail",Toast.LENGTH_LONG).show();
                recyclerviewAdaptor.setOnItemClickListener(new myRecyclerViewAdapter.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        HashMap<String, ?> event = (HashMap<String, ?>) meventList.get(position);
                        getActivity().getSupportFragmentManager()
                                .beginTransaction()
                                .replace(R.id.container, new EventDetail().newInstance(event))
                                .addToBackStack(null)
                                .commit();
                    }

                    @Override
                    public void onItemLongClick(View view, int position) {
                        //getActivity().startActionMode(new ActionBarCallBack(position));
                    }

                    @Override
                    public void onOverflowMenuClick(View v, int position) {

                    }

                });
                SwipeableRecyclerViewTouchListener swipeTouchListener =
                        new SwipeableRecyclerViewTouchListener(recyclerView,
                                new SwipeableRecyclerViewTouchListener.SwipeListener() {
                                    int mPosition=0;
                                    @Override
                                    public boolean canSwipe(int position) {
                                        mPosition = position;
                                        return true;
                                    }

                                    @Override
                                    public void onDismissedBySwipeLeft(RecyclerView recyclerView, int[] reverseSortedPositions) {
                                        HashMap<String, ?> event = (HashMap<String, ?>) meventList.get(mPosition);
                                        getActivity().getSupportFragmentManager()
                                                .beginTransaction()
                                                .replace(R.id.container, new EventDetail().newInstance(event))
                                                .addToBackStack(null)
                                                .commit();
                                    }

                                    @Override
                                    public void onDismissedBySwipeRight(RecyclerView recyclerView, int[] reverseSortedPositions) {
                                        //nothing
                                    }
                                });

                recyclerView.addOnItemTouchListener(swipeTouchListener);

                break;
            case 1:
                rootView = inflater.inflate(R.layout.fragment_event_list, container, false);
                recyclerView = (RecyclerView) rootView.findViewById(R.id.listview);
                recyclerView.setLayoutManager(new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL));
                recyclerviewAdaptor = new myRecyclerViewAdapter(getActivity(),meventList1);
                recyclerView.setAdapter(recyclerviewAdaptor);
                recyclerView.setHasFixedSize(true);
                recyclerviewAdaptor.setOnItemClickListener(new myRecyclerViewAdapter.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        HashMap<String, ?> event = (HashMap<String, ?>) meventList1.get(position);
                        getActivity().getSupportFragmentManager()
                                .beginTransaction()
                                .replace(R.id.container, new EventDetail().newInstance(event))
                                .addToBackStack(null)
                                .commit();
                    }

                    @Override
                    public void onItemLongClick(View view, int position) {
                        //getActivity().startActionMode(new ActionBarCallBack(position));
                    }

                    @Override
                    public void onOverflowMenuClick(View v, int position) {

                    }

                });

                break;
            default:
                rootView = inflater.inflate(R.layout.fragment_event_list, container, false);
                recyclerView = (RecyclerView) rootView.findViewById(R.id.listview);
                recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
                recyclerviewAdaptor = new myRecyclerViewAdapter(getActivity(),meventList);
                recyclerView.setAdapter(recyclerviewAdaptor);
                recyclerView.setHasFixedSize(true);
                recyclerviewAdaptor.setOnItemClickListener(new myRecyclerViewAdapter.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        HashMap<String, ?> event = (HashMap<String, ?>) meventList.get(position);
                        getActivity().getSupportFragmentManager()
                                .beginTransaction()
                                .replace(R.id.container, new EventDetail().newInstance(event))
                                .addToBackStack(null)
                                .commit();
                    }

                    @Override
                    public void onItemLongClick(View view, int position) {
                        //getActivity().startActionMode(new ActionBarCallBack(position));
                    }

                    @Override
                    public void onOverflowMenuClick(View v, int position) {

                    }

                });

                break;

        }





        return rootView;        // Inflate the layout for this fragment

    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {

        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (OnFragmentInteractionListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }


    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        public void onFragmentInteraction(Uri uri);
    }

}
