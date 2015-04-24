package com.example.yogesh16991.test_proj;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.HashMap;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link EventDetail.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link EventDetail#newInstance} factory method to
 * create an instance of this fragment.
 */
public class EventDetail extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    /*private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
*/
    private static final String ARG_EVENT = "event";
    // TODO: Rename and change types of parameters
 /*   private String mParam1;
    private String mParam2;*/

    private HashMap<String, ?> event;
    private OnFragmentInteractionListener mListener;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * param param1 Parameter 1.
     * param param2 Parameter 2.
     * @return A new instance of fragment EventDetail.
     */
    // TODO: Rename and change types and number of parameters
    public static EventDetail newInstance(HashMap<String, ?> event) {
        EventDetail fragment = new EventDetail();
        Bundle args = new Bundle();
/*        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);*/
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
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_event_detail, container, false);;
        setRetainInstance(true);
        TextView markertitle;
        TextView eventdesc;
        TextView eventname;
       markertitle = (TextView) v.findViewById(R.id.markertitle);
        eventdesc = (TextView) v.findViewById(R.id.eventdesc);
        eventname = (TextView) v.findViewById(R.id.eventname);
        TextView sdate =(TextView)v.findViewById(R.id.textViewFromDateEvent);
        TextView edate = (TextView)v.findViewById(R.id.textViewToDateEvent);
        eventname.setText((String) event.get("EventName"));
        eventdesc.setText((String) event.get("EventDesc"));
        markertitle.setText((String) event.get("MarkerTitle"));
        sdate.setText((String) event.get("Sdate"));
        edate.setText((String) event.get("Edate"));
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
