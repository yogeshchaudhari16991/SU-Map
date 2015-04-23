

        package com.example.yogesh16991.test_proj;

        import android.app.Activity;
        import android.app.DatePickerDialog;
        import android.app.Dialog;
        import android.content.Intent;
        import android.net.Uri;
        import android.os.Bundle;
        import android.support.v4.app.DialogFragment;
        import android.support.v4.app.Fragment;
        import android.support.v7.internal.widget.AdapterViewCompat;
        import android.util.Log;
        import android.view.LayoutInflater;
        import android.view.View;
        import android.view.ViewGroup;
        import android.widget.AdapterView;
        import android.widget.ArrayAdapter;
        import android.widget.Button;
        import android.widget.DatePicker;
        import android.widget.EditText;
        import android.widget.ImageView;
        import android.widget.Spinner;
        import android.widget.TextView;
        import android.widget.Toast;

        import com.google.android.gms.maps.CameraUpdateFactory;
        import com.google.android.gms.maps.model.LatLng;
        import com.google.android.gms.maps.model.Marker;
        import com.google.android.gms.maps.model.MarkerOptions;

        import org.json.JSONException;

        import java.text.SimpleDateFormat;
        import java.util.ArrayList;
        import java.util.Calendar;
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
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static final int REQUEST_DATE = 0;
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    static EventDetailsJSon eventData;
    List<Map<String, ?>> EventCatList;

    static MarkerDataJson markerData;
    List<Map<String, ?>> MarkerList;
    private TextView fromDateResult;
    static Marker currentPlace;
    int currentIndex;

    private OnFragmentInteractionListener mListener;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * param param1 Parameter 1.
     * param param2 Parameter 2.
     * @return A new instance of fragment AddNewEvent.
     */
    // TODO: Rename and
    //
    //
    // change types and number of parameters
    public static AddNewEvent newInstance(EventDetailsJSon eventDetailsJSon) {
        AddNewEvent fragment = new AddNewEvent();
        Bundle args = new Bundle();
        currentPlace = null;
          /*  args.putString(ARG_PARAM1, param1);
            args.putString(ARG_PARAM2, param2);*/
        fragment.setArguments(args);
        // markerData = markerdata;
        eventData = eventDetailsJSon;
        return fragment;

    }

    public static AddNewEvent newInstance(EventDetailsJSon eventDetailsJSon, Marker current) {
        AddNewEvent fragment = new AddNewEvent();
        Bundle args = new Bundle();
        currentPlace = null;
          /*  args.putString(ARG_PARAM1, param1);
            args.putString(ARG_PARAM2, param2);*/
        fragment.setArguments(args);
        // markerData = markerdata;
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
        if (getArguments() != null) {
           /*     mParam1 = getArguments().getString(ARG_PARAM1);
                mParam2 = getArguments().getString(ARG_PARAM2);*/
        }
    }

    private List<String> readPlaceItems() throws JSONException {
        List<String> templist= new ArrayList<String>();
        MarkerList=markerData.getMarkersList();
        Log.e("sdadada",MarkerList.toString());
        currentIndex = 0;
        for(int i=0;i<MarkerList.size();i++) {
            Map<String, ?> placeMarker = markerData.getItem(i);
            String Pname = placeMarker.get("MarkerTitle").toString();
            if(currentPlace != null && (currentPlace.getTitle()).equalsIgnoreCase(Pname)){
                currentIndex = i;
                Toast.makeText(getActivity(),"in index : "+currentIndex,Toast.LENGTH_SHORT).show();
            }
            Log.e("rajas",Pname);
            templist.add(Pname);
        }
        return templist;
    }
    /*
    private List<String> readCatagoryItems() throws JSONException {
        List<String> templist= new ArrayList<String>();
        EventCatList=eventData.getEventsList();
        Log.e("sdadada",EventCatList.toString());
        for(int i=0;i<EventCatList.size();i++) {
            Map<String, ?> placeMarker = eventData.getItem(i);
            String Pname = placeMarker.get("Category").toString();
            Log.e("rajas",Pname);
            templist.add(Pname);
        }

        // List<String> al = new ArrayList<>();
        // add elements to al, including duplicates
        Set<String> hs = new HashSet<>();
        hs.addAll(templist);
        templist.clear();
        templist.addAll(hs);
        return templist;
    }
    */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View rootView = inflater.inflate(R.layout.fragment_add_new_event, container, false);
        TextView txt_dialog = (TextView) rootView.findViewById(R.id.fromDate);
        fromDateResult = (TextView) rootView.findViewById(R.id.fromDateResult);
        txt_dialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerDialog();
            }
        });
        List<String> placesSpinerList = new ArrayList<String>();
        List<String> catagorySpinerList = new ArrayList<String>();
        final Spinner placesSpiner = (Spinner) rootView.findViewById(R.id.placeSpinner);
        Spinner catagorySpiner = (Spinner) rootView.findViewById(R.id.catagoryspinner);
        final HashMap event = new HashMap();
        try {
            placesSpinerList = readPlaceItems();
           // catagorySpinerList = readCatagoryItems();
        } catch (JSONException e) {
            Toast.makeText(getActivity(), "Problem reading list of markers.", Toast.LENGTH_LONG).show();
        }
        ArrayAdapter<String> dataAdapterPlace = new ArrayAdapter<String>(this.getActivity(),
        android.R.layout.simple_spinner_item, placesSpinerList);
        dataAdapterPlace.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        placesSpiner.setAdapter(dataAdapterPlace);
        placesSpiner.setSelection(currentIndex);

        /*
        ArrayAdapter<String> dataAdapterCatagory = new ArrayAdapter<String>(this.getActivity(),
                android.R.layout.simple_spinner_item, catagorySpinerList);
        dataAdapterCatagory.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        catagorySpiner.setAdapter(dataAdapterCatagory);
        */
        final List<String> finalPlacesSpinerList = placesSpinerList;
        placesSpiner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                int index = 0;
                ImageView imageView = (ImageView) rootView.findViewById(R.id.placeImage);
                int size = markerData.getSize();
                Toast.makeText(getActivity(), "size= " + size, Toast.LENGTH_SHORT).show();
                for (int i = 0; i < size; i++) {
                    if ((markerData.getItem(i).get("MarkerTitle").toString()).equals(finalPlacesSpinerList.get(position).toString())) {
                        index = i;
                        Toast.makeText(getActivity(), "in here : " + i, Toast.LENGTH_SHORT).show();
                        break;
                    }
                }
                imageView.setImageResource((Integer) markerData.getItem(index).get("img"));// Log.e("sdadssadadsadad afagsdg sdg sdgdg","ghasdgfashgdfiuc kgficuyasfhgsgf");
                Toast.makeText(getActivity(), "in listner " + index, Toast.LENGTH_SHORT).show();
                event.put("MarkerTitle",finalPlacesSpinerList.get(position).toString());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // your code here
            }

        });

        Button addEvent = (Button) rootView.findViewById(R.id.btn_EventOk);
        final TextView eventTitle = (TextView) rootView.findViewById(R.id.eventName);
        addEvent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                event.put("EventName",eventTitle.getText().toString());
                event.put("EventDesc","test descirption");
                event.put("Category","educational");
                eventData.addEvent((String) event.get("EventName"), (String) event.get("EventDesc"), (String) event.get("MarkerTitle"), (String) event.get("Category"));
                getFragmentManager().beginTransaction().replace(R.id.container,MapFragment.newInstance(markerData,eventData)).commit();
            }
        });

        return rootView;
    }

    private void showDatePickerDialog() {
        Date date= new Date(System.currentTimeMillis());
        FromDateTimeDilog dialog= FromDateTimeDilog.newInstance(date);
        dialog.setTargetFragment(AddNewEvent.this, REQUEST_DATE);
        dialog.show(getFragmentManager(), "Datepick  er Dialog");
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        Date date=(Date)data.getSerializableExtra(FromDateTimeDilog.ARGS_DATE);
        String time = data.getExtras().get(FromDateTimeDilog.ARGS_Time).toString();
        fromDateResult.setText(date.toString() + "   " + time);


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

