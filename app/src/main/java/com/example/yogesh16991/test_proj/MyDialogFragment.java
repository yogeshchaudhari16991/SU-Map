package com.example.yogesh16991.test_proj;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.net.Uri;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.model.Marker;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;


/**
 * A simple {@link android.app.Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link MyDialogFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link MyDialogFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MyDialogFragment extends android.support.v4.app.DialogFragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
   public static final String ARGS_DATE = "date";
    public static final String ARGS_NAME = "name";
    public static final String ARGS_CHECK = "check";

    private static Marker marker;
    private Date mdate;
    private String mname;
     private String check;
    private static String value;
    static Context mContext;
    static EventDetailsJSon eventDetailsJSon;
    private DisplayMetrics metrics;
    // TODO: Rename and change types of parameters

    private OnFragmentInteractionListener mListener;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param date Parameter 1.
     *
     * @return A new instance of fragment MyDialogFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static MyDialogFragment newInstance(EventDetailsJSon date, Marker mark, String type, Context context) throws JSONException {
        MyDialogFragment fragment = new MyDialogFragment();
        mContext = context;

        Bundle args = new Bundle();
        //args.putSerializable(ARGS_DATE, date);
        eventDetailsJSon = date;
        marker = mark;
        value = type;
        fragment.setArguments(args);
        return fragment;
    }

    public MyDialogFragment() {
        // Required empty public constructor
    }



    public Dialog onCreateDialog(Bundle savedInstanceState) {
        //mdate = (Date) getArguments().getSerializable(ARGS_DATE);
        metrics = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(metrics);
        AlertDialog.Builder alertDialogBuilder =new AlertDialog.Builder(getActivity());
        View v;
        switch(value) {
            case "info":
                MarkerDataJson mdj = new MarkerDataJson();
                v = getActivity().getLayoutInflater().inflate(R.layout.fragment_my_dialog, null);
                TextView descView = (TextView) v.findViewById(R.id.desc);

                ImageView placeImg = (ImageView) v.findViewById(R.id.placeImg);
                for(int i=0;i<mdj.markersList.size();i++){
                    if((mdj.getItem(i).get("MarkerTitle").toString()).equals(marker.getTitle().toString())){
                        placeImg.setImageResource((Integer) mdj.getItem(i).get("img"));
                        descView.setText(mdj.getItem(i).get("MarkerDescription").toString());
                    }
                }

                alertDialogBuilder.setView(v)
                        .setTitle("Place Info")
                        .setMessage("Place Name: " + marker.getTitle())
                        .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                //Toast.makeText(getActivity(), marker.getTitle(), Toast.LENGTH_SHORT).show();
                            }
                        });


                break;

            case "list":
                v = getActivity().getLayoutInflater().inflate(R.layout.fragment_list_view, null);
                ListView listView = (ListView) v.findViewById(R.id.list_view);

                List<Map<String, ?>> placeEventList = eventDetailsJSon.getEventsList();

                List<Map<String, ?>> tempList = new ArrayList<Map<String,?>>();
                for(int i=0; i< placeEventList.size(); i++)
                {
                    Map<String, ?> placeEvent = eventDetailsJSon.getItem(i);
                    //Toast.makeText(getActivity(),"in list "+placeEvent.get("MarkerTitle").toString(),Toast.LENGTH_SHORT).show();
                    if((placeEvent.get("MarkerTitle").toString()).equals(marker.getTitle().toString()) ) {
                        tempList.add(placeEvent);
                        //Toast.makeText(getActivity(),"in list",Toast.LENGTH_SHORT).show();
                    }
                }
                //Toast.makeText(getActivity(),placeEventList.size(),Toast.LENGTH_SHORT).show();

                MyBaseAdapter_List myBaseAdapterList = new MyBaseAdapter_List(getActivity(), tempList,metrics);
                listView.setAdapter(myBaseAdapterList);

                //listView.setAdapter(simpleAdapter);
                alertDialogBuilder.setView(v)
                        .setTitle("List of Events")
                        .setMessage("Place Name: " + marker.getTitle())
                        .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                //Toast.makeText(getActivity(), marker.getTitle(), Toast.LENGTH_SHORT).show();
                                //Toast.makeText(getActivity(), "in here", Toast.LENGTH_SHORT).show();
                            }
                        });
                break;

            case "plus":
                /*
                v = getActivity().getLayoutInflater().inflate(R.layout.fragment_add_new_event, null);

                alertDialogBuilder.setView(v)
                        .setTitle("Add Event")
                        .setMessage("Place Name: " + marker.getTitle())
                        .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Toast.makeText(getActivity(), marker.getTitle(), Toast.LENGTH_SHORT).show();
                            }
                        });
                */
                getFragmentManager().beginTransaction().replace(R.id.container,AddNewEvent.newInstance(eventDetailsJSon)).addToBackStack(null).commit();
                break;
        }

        return alertDialogBuilder.create();
    }
/*

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_my_dialog, container, false);
    }
*/

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
