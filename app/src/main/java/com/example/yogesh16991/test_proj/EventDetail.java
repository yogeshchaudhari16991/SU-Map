package com.example.yogesh16991.test_proj;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link EventDetail.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link EventDetail#newInstance} factory method to
 * create an instance of this fragment.
 */
public class EventDetail extends Fragment {
    private static final String ARG_EVENT = "event";
    private HashMap<String, ?> event;
    private OnFragmentInteractionListener mListener;
    private MarkerDataJson markerData;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     * @return A new instance of fragment EventDetail.
     */
    public static EventDetail newInstance(HashMap<String, ?> event) {
        EventDetail fragment = new EventDetail();
        Bundle args = new Bundle();
        args.putSerializable(ARG_EVENT, event);
        fragment.setArguments(args);
        return fragment;
    }

    public EventDetail() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
        if (getArguments() != null) {
            event=(HashMap<String,?>)getArguments().getSerializable(ARG_EVENT);
        }
        markerData = new MarkerDataJson();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_event_detail, container, false);
        ;
        setRetainInstance(true);
        List<Map<String, ?>> markerList;
        HashMap<String,?> marker = null;
        TextView markertitle;
        TextView eventdesc;
        TextView eventname;
        TextView sdate;
        TextView edate;
        ImageView imageView;

        markerList = markerData.markersList;
        for (int i = 0; i < markerData.getSize(); i++) {
            if (((String) markerList.get(i).get("MarkerTitle")).equals((String) event.get("MarkerTitle"))) {
                marker = (HashMap<String,?>)markerList.get(i);
                break;
            }
        }

        markertitle = (TextView) v.findViewById(R.id.markertitle);
        eventdesc = (TextView) v.findViewById(R.id.eventdesc);
        eventname = (TextView) v.findViewById(R.id.eventname);
        sdate =(TextView)v.findViewById(R.id.textViewFromDateEvent);
        edate = (TextView)v.findViewById(R.id.textViewToDateEvent);
        imageView = (ImageView)v.findViewById(R.id.imageView);

        eventname.setText((String) event.get("EventName"));
        eventdesc.setText((String) event.get("EventDesc"));
        markertitle.setText((String) event.get("MarkerTitle"));
        sdate.setText((String) event.get("Sdate"));
        edate.setText((String) event.get("Edate"));

        imageView.setImageResource((Integer)marker.get("img"));

        return v;
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
