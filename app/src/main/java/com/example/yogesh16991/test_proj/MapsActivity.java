package com.example.yogesh16991.test_proj;

import android.net.Uri;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.Menu;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.Date;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class MapsActivity extends ActionBarActivity implements MyDialogFragment.OnFragmentInteractionListener {



    private NavigationDrawerFragment mNavigationDrawerFragment;
    Toolbar mtoolbar;
    DrawerLayout mDrawerLayout;
    ActionBarDrawerToggle mDrawerToggle;
    RecyclerView mDrawerlist;
    MyDrawerRecyclerViewAdapter myDrawerRecyclerViewAdapter;
    RelativeLayout mDrawer;
    LinearLayout activity;
    private GoogleMap mMap; // Might be null if Google Play services APK is not available.
    private ActionBarDrawerToggle actionBarDrawerToggle;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        activity = (LinearLayout) findViewById(R.id.linear);
        mtoolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mtoolbar);

        mDrawerLayout = (DrawerLayout)findViewById(R.id.drawer_layout);
        mDrawer = (RelativeLayout)findViewById(R.id.navigation_drawer);
        mDrawerlist = (RecyclerView) findViewById(R.id.drawer_list);
        mDrawerlist.setHasFixedSize(true);
        mDrawerlist.setLayoutManager(new LinearLayoutManager(this));
        myDrawerRecyclerViewAdapter = new MyDrawerRecyclerViewAdapter(this, new DrawerData_New().getList());
        mDrawerlist.setAdapter(myDrawerRecyclerViewAdapter);
        myDrawerRecyclerViewAdapter.setOnItemClickListener(new MyDrawerRecyclerViewAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {

                selectItem(position);
            }

        });

        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, mtoolbar, R.string.open_drawer, R.string.close_drawwer) {
            public void onDrawerclosed(View view) {
                super.onDrawerClosed(view);
            }

            public void onDrawerOpened(View Drawerview) {
                super.onDrawerOpened(Drawerview);

            }
        };
        mDrawerLayout.setDrawerListener(mDrawerToggle);
        mDrawerToggle.syncState();
        setUpMapIfNeeded();
    }
    private void selectItem(int position) {
        switch (position){
            case 0:
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.container, getSupportFragmentManager().findFragmentById(R.id.map))
                        .commit();
                break;
            case 1:
               /* getSupportFragmentManager().beginTransaction()
                        .replace(R.id.container,RecylerViewFragment.newInstance(0))
                        .commit();*/
                break;
            case 2:
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.container,EventList.newInstance(0))
                        .commit();
                break;
            case 4:
                /*getSupportFragmentManager().beginTransaction()
                        .replace(R.id.container, new about_me())
                        .commit();*/
                break;
            case 5:
               /* getSupportFragmentManager().beginTransaction()
                        .replace(R.id.container, new settingsfrag())
                        .commit();*/
                break;
            case 6:
               /* getSupportFragmentManager().beginTransaction()
                        .replace(R.id.container, new ExitFrag())
                        .commit();*/
                break;
            default:
                break;
        }
        mDrawerLayout.closeDrawer(mDrawer);
    }


    @Override
    protected void onResume() {
        super.onResume();
        setUpMapIfNeeded();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_master_detail, menu);
        return true;
    }

    /**
     * Sets up the map if it is possible to do so (i.e., the Google Play services APK is correctly
     * installed) and the map has not already been instantiated.. This will ensure that we only ever
     * call {@link #setUpMap()} once when {@link #mMap} is not null.
     * <p/>
     * If it isn't installed {@link SupportMapFragment} (and
     * {@link com.google.android.gms.maps.MapView MapView}) will show a prompt for the user to
     * install/update the Google Play services APK on their device.
     * <p/>
     * A user can return to this FragmentActivity after following the prompt and correctly
     * installing/updating/enabling the Google Play services. Since the FragmentActivity may not
     * have been completely destroyed during this process (it is likely that it would only be
     * stopped or paused), {@link #onCreate(Bundle)} may not be called again so we should call this
     * method in {@link #onResume()} to guarantee that it will be called.
     */
    private void setUpMapIfNeeded() {
        // Do a null check to confirm that we have not already instantiated the map.
        if (mMap == null) {
            // Try to obtain the map from the SupportMapFragment.
            mMap = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map)).getMap();

            // Check if we were successful in obtaining the map.
            if (mMap != null) {
                setUpMap();
            }
        }
    }

    /**
     * This is where we can add markers or lines, add listeners or move the camera. In this case, we
     * just add a marker near Africa.
     * <p/>
     * This should only be called once and when we are sure that {@link #mMap} is not null.
     */
    private void setUpMap() {
        try {
            readItems();
        } catch (JSONException e) {
            Toast.makeText(this, "Problem reading list of markers.", Toast.LENGTH_LONG).show();
        }
        mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener()
        {

            @Override
            public boolean onMarkerClick(Marker arg0) {
                /*
                if(arg0.getTitle().equals("Syracuse University")) // if marker source is clicked
                    Toast.makeText(MapsActivity.this, arg0.getTitle(), Toast.LENGTH_SHORT).show();// display toast
                */
                /*
                Date date=new Date(System.currentTimeMillis());
                MyDialogFragment dialog= MyDialogFragment.newInstance(date);
                dialog.setTargetFragment(DialogFragment.newInstance(), 0);
                dialog.show(getSupportFragmentManager(),"Datepicker Dialog");
                */
                final Marker arg01 = arg0;

                ImageView imageView = (ImageView)activity.findViewById(R.id.tool_btn1);
                imageView.setVisibility(View.VISIBLE);
                imageView.setOnClickListener(new View.OnClickListener()
                {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(getApplicationContext(), arg01.getTitle(), Toast.LENGTH_SHORT).show();
                        Date date=new Date(System.currentTimeMillis());
                        MyDialogFragment dialog= MyDialogFragment.newInstance(date,arg01);
                        dialog.show(getSupportFragmentManager(),"Datepicker Dialog");
                    }
                });
                ImageView imageView2 = (ImageView)activity.findViewById(R.id.tool_btn2);
                imageView2.setVisibility(View.VISIBLE);
                imageView2.setOnClickListener(new View.OnClickListener()
                {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(getApplicationContext(),"'list' icon selected", Toast.LENGTH_SHORT).show();
                    }
                });
                ImageView imageView3 = (ImageView)activity.findViewById(R.id.tool_btn3);
                imageView3.setVisibility(View.VISIBLE);
                imageView3.setOnClickListener(new View.OnClickListener()
                {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(getApplicationContext(),"'plus' icon selected", Toast.LENGTH_SHORT).show();
                    }
                });
                
                return true;
            }

        });
    }

    @Override
    public void onFragmentInteraction(Uri uri) {
        //nothing
    }

    private void readItems()
            throws JSONException {
        InputStream inputStream = getResources().openRawResource(R.raw.radar_search);
        read(inputStream);
       // List<JsonReader.MyItem> items = new JsonReader().read(inputStream);

    }


    private static final String REGEX_INPUT_BOUNDARY_BEGINNING = "\\A";

    public void read(InputStream inputStream) throws JSONException {
        String json = new Scanner(inputStream).useDelimiter(REGEX_INPUT_BOUNDARY_BEGINNING).next();
        JSONArray array = new JSONArray(json);
        for (int i = 0; i < array.length(); i++) {
            JSONObject object = array.getJSONObject(i);
            double lat = object.getDouble("lat");
            double lng = object.getDouble("lng");
            String Pname = object.getString("title");
            mMap.addMarker(new MarkerOptions().position(new LatLng(lat,lng)).title(Pname));
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(lat,lng), 17));
        }
    }


}
