package com.example.yogesh16991.test_proj;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.model.Marker;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link AddNewEvent.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link AddNewEvent#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AddNewEvent extends Fragment {

    private static final int FROM_DATE = 0;
    private static final int TO_DATE = 1;
    static EventDetailsJSon eventData;
    List<Map<String, ?>> EventCatList;
    static MarkerDataJson markerData;
    List<Map<String, ?>> MarkerList;
    private TextView fromDateResult;
    private TextView toDateResult;
    static Marker currentPlace;
    int currentIndex;
    private OnFragmentInteractionListener mListener;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     * @return A new instance of fragment AddNewEvent.
     */
    public static AddNewEvent newInstance(EventDetailsJSon eventDetailsJSon) {
        AddNewEvent fragment = new AddNewEvent();
        Bundle args = new Bundle();
        currentPlace = null;
        fragment.setArguments(args);
        eventData = eventDetailsJSon;
        return fragment;
    }

    public static AddNewEvent newInstance(EventDetailsJSon eventDetailsJSon, Marker current) {
        AddNewEvent fragment = new AddNewEvent();
        Bundle args = new Bundle();
        currentPlace = null;
        fragment.setArguments(args);
        currentPlace = current;
        eventData = eventDetailsJSon;
        return fragment;

    }

    public AddNewEvent() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        markerData = new MarkerDataJson();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        setRetainInstance(true);
        final View rootView = inflater.inflate(R.layout.fragment_add_new_event, container, false);
        List<String> placesSpinerList = new ArrayList<String>();
        List<String> catagorySpinerList = new ArrayList<String>();
        final Spinner placesSpiner = (Spinner) rootView.findViewById(R.id.placeSpinner);
        final Spinner catagorySpiner = (Spinner) rootView.findViewById(R.id.catagoryspinner);
        final HashMap event = new HashMap();
        Button addEvent = (Button) rootView.findViewById(R.id.btn_EventOk);
        final TextView eventTitle = (TextView) rootView.findViewById(R.id.eventName);
        final TextView eventDesc = (TextView) rootView.findViewById(R.id.editText2);
        final TextView sdate = (TextView)rootView.findViewById(R.id.fromDateResult);
        final TextView edate = (TextView)rootView.findViewById(R.id.toDateResult);
        TextView fromdate = (TextView) rootView.findViewById(R.id.fromDate);
        TextView todate = (TextView)rootView.findViewById(R.id.toDate);
        fromDateResult = (TextView) rootView.findViewById(R.id.fromDateResult);
        toDateResult = (TextView)rootView.findViewById(R.id.toDateResult);

        fromdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               showDatePickerDialog(0);
            }
        });
        todate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDatePickerDialog(1);
            }
        });

        try {
            placesSpinerList = readPlaceItems();
            catagorySpinerList = readCatagoryItems();
        } catch (JSONException e) {
            Toast.makeText(getActivity(), "Problem reading list of markers.", Toast.LENGTH_LONG).show();
        }
        ArrayAdapter<String> dataAdapterPlace = new ArrayAdapter<String>(this.getActivity(),
        android.R.layout.simple_spinner_item, placesSpinerList);
        dataAdapterPlace.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        placesSpiner.setAdapter(dataAdapterPlace);
        placesSpiner.setSelection(currentIndex);

        ArrayAdapter<String> dataAdapterCatagory = new ArrayAdapter<String>(this.getActivity(),
        android.R.layout.simple_spinner_item, catagorySpinerList);
        dataAdapterCatagory.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        catagorySpiner.setAdapter(dataAdapterCatagory);

        final List<String> finalPlacesSpinerList = placesSpinerList;
        placesSpiner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                int index = 0;
                ImageView imageView = (ImageView) rootView.findViewById(R.id.placeImage);
                int size = markerData.getSize();
                for (int i = 0; i < size; i++) {
                    if ((markerData.getItem(i).get("MarkerTitle").toString()).equals(finalPlacesSpinerList.get(position).toString())) {
                        index = i;
                        break;
                    }
                }
                imageView.setImageResource((Integer) markerData.getItem(index).get("img"));
                event.put("MarkerTitle",finalPlacesSpinerList.get(position).toString());
            }
            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // your code here
            }
        });

        final List<String> finalCategorySpinerList = catagorySpinerList;
        catagorySpiner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                event.put("Category",finalCategorySpinerList.get(i).toString());
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });

        addEvent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                event.put("EventName",eventTitle.getText().toString());
                event.put("EventDesc",eventDesc.getText().toString());
                event.put("Sdate",sdate.getText().toString());
                event.put("Edate",edate.getText().toString());
                eventData.addEvent((String) event.get("EventName"),
                        (String) event.get("EventDesc"),
                        (String) event.get("MarkerTitle"),
                        (String) event.get("Category"),
                        (String)event.get("Sdate"),
                        (String)event.get("Edate"));
                getFragmentManager().beginTransaction().replace(R.id.container,MapFragment.newInstance(markerData,eventData)).commit();
            }
        });
        return rootView;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        Date date=(Date)data.getSerializableExtra(FromDateTimeDilog.ARGS_DATE);
        String time = data.getExtras().get(FromDateTimeDilog.ARGS_Time).toString();
        switch(requestCode){
           case 0:
               fromDateResult.setText(date.toString() + "   " + time);
               break;
           case 1:
               toDateResult.setText(date.toString() + "   " + time);
               break;
        }
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
    private List<String> readPlaceItems() throws JSONException {
        List<String> tempMarkerList= new ArrayList<String>();
        MarkerList=markerData.getMarkersList();
        currentIndex = 0;
        for(int i=0;i<MarkerList.size();i++) {
            Map<String, ?> placeMarker = markerData.getItem(i);
            String Pname = placeMarker.get("MarkerTitle").toString();
            if(currentPlace != null && (currentPlace.getTitle()).equalsIgnoreCase(Pname)){
                currentIndex = i;
            }
            tempMarkerList.add(Pname);
        }
        return tempMarkerList;
    }

    private List<String> readCatagoryItems() throws JSONException {
        List<String> tempCategoryList= new ArrayList<String>();
        tempCategoryList.add("educational");
        tempCategoryList.add("food");
        tempCategoryList.add("sports");
        tempCategoryList.add("entertainment");
        tempCategoryList.add("others");
        Set<String> hs = new HashSet<>();
        hs.addAll(tempCategoryList);
        tempCategoryList.clear();
        tempCategoryList.addAll(hs);
        return tempCategoryList;
    }

    private void showDatePickerDialog(int option) {
        Date date= new Date(System.currentTimeMillis());
        FromDateTimeDilog dialog= FromDateTimeDilog.newInstance(date);
        if(option==0)
            dialog.setTargetFragment(AddNewEvent.this, FROM_DATE);
        else if (option == 1)
            dialog.setTargetFragment(AddNewEvent.this, TO_DATE);
        dialog.show(getFragmentManager(), "Datepicker Dialog");
    }

}

