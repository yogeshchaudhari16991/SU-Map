package com.example.yogesh16991.test_proj;

import android.app.Activity;
import android.support.v4.app.FragmentManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.support.v4.app.Fragment;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONException;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link MapFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link MapFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MapFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment MapFragment.
     */
    // TODO: Rename and change types and number of parameters

    MapView mMapView;
    private GoogleMap googleMap;
    RelativeLayout activity;
    FragmentManager fm;
    static MarkerDataJson markerData;
    List<Map<String, ?>> MarkerList;

    public static MapFragment newInstance(MarkerDataJson markerdata) {
        MapFragment fragment = new MapFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        markerData = markerdata;
        return fragment;
    }



    public MapFragment() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            // inflate and return the layout
            View v = inflater.inflate(R.layout.fragment_map, container,
                    false);
            mMapView = (MapView) v.findViewById(R.id.mapView);
            mMapView.onCreate(savedInstanceState);
            activity = (RelativeLayout) v.findViewById(R.id.mainfrag);
            mMapView.onResume();// needed to get the map to display immediately
            fm = getFragmentManager();
            try {
                MapsInitializer.initialize(getActivity().getApplicationContext());
            } catch (Exception e) {
                e.printStackTrace();
            }
            setUpMap();
            // Perform any camera updates here
            return v;
        }

    private void setUpMap() {
        googleMap = mMapView.getMap();
        try {
            readItems();
        } catch (JSONException e) {
            Toast.makeText(getActivity(), "Problem reading list of markers.", Toast.LENGTH_LONG).show();
        }

        final ImageView imageView = (ImageView)activity.findViewById(R.id.tool_btn1);
        final ImageView imageView2 = (ImageView)activity.findViewById(R.id.tool_btn2);
        final ImageView imageView3 = (ImageView)activity.findViewById(R.id.tool_btn3);

        googleMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {
                imageView.setVisibility(View.INVISIBLE);
                imageView2.setVisibility(View.INVISIBLE);
                imageView3.setVisibility(View.INVISIBLE);
            }
        });
        googleMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener()
        {

            @Override
            public boolean onMarkerClick(Marker arg0) {


                final Marker arg01 = arg0;
                arg01.showInfoWindow();
                //set Snippet to display number of events
                imageView.setVisibility(View.VISIBLE);
                imageView.setOnClickListener(new View.OnClickListener()
                {
                    @Override
                    public void onClick(View v) {
                        //Toast.makeText(getApplicationContext(), arg01.getTitle(), Toast.LENGTH_SHORT).show();
                        Date date=new Date(System.currentTimeMillis());
                        MyDialogFragment dialog= null;
                        try {
                            dialog = MyDialogFragment.newInstance(date, arg01, "info", getActivity());
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        dialog.show(fm, "Place Information Dialog");
                    }
                });
                imageView2.setVisibility(View.VISIBLE);
                imageView2.setOnClickListener(new View.OnClickListener()
                {
                    @Override
                    public void onClick(View v) {
                        MyDialogFragment dialog= null;
                        try {
                            dialog = MyDialogFragment.newInstance(null, arg01, "list", getActivity());
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        dialog.show(fm, "List of Events Dialog");
                        //Toast.makeText(getApplicationContext(),"'list' icon selected", Toast.LENGTH_SHORT).show();
                    }
                });
                imageView3.setVisibility(View.VISIBLE);
                imageView3.setOnClickListener(new View.OnClickListener()
                {
                    @Override
                    public void onClick(View v) {
                        /*
                        MyDialogFragment dialog= null;
                        try {
                            dialog = MyDialogFragment.newInstance(null, arg01, "plus", getActivity());
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        dialog.show(fm, "Add Events Dialog");
                        */
                        fm.beginTransaction().replace(R.id.container,AddNewEvent.newInstance()).addToBackStack(null).commit();
                    }
                });

                return true;
            }

        });
    }



    private void setUpMapIfNeeded() {
        // Do a null check to confirm that we have not already instantiated the map.
        if (googleMap == null) {
            // Try to obtain the map from the SupportMapFragment.
            googleMap = mMapView.getMap();
        }
        setUpMap();
    }

    private void readItems()
            throws JSONException {
        MarkerList=markerData.getMarkersList();
        for(int i=0;i<MarkerList.size();i++) {
            Map<String, ?> placeMarker = markerData.getItem(i);
            String latstring = placeMarker.get("lat").toString();
            double lat = Double.parseDouble(latstring);
            String lgnstring = placeMarker.get("lng").toString();
            double lng = Double.parseDouble(lgnstring);
            String Pname = placeMarker.get("MarkerTitle").toString();

            googleMap.addMarker(new MarkerOptions().position(new LatLng(lat,lng)).title(Pname));
            googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(lat,lng), 17));

        }
        //InputStream inputStream = getResources().openRawResource(R.raw.radar_search);
        //read(inputStream);
        // List<MyItem> items = new JsonHandler().read(inputStream);
//      Toast.makeText(getApplicationContext(), MarkerList.size(), Toast.LENGTH_SHORT).show();

    }

        @Override
        public void onResume() {
            super.onResume();
            mMapView.onResume();
        }

        @Override
        public void onPause() {
            super.onPause();
            mMapView.onPause();
        }

        @Override
        public void onDestroy() {
            super.onDestroy();
            mMapView.onDestroy();
        }

        @Override
        public void onLowMemory() {
            super.onLowMemory();
            mMapView.onLowMemory();
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
