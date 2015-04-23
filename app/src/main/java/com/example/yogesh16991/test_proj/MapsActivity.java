package com.example.yogesh16991.test_proj;

import android.content.Intent;
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
import android.util.Log;
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
import java.util.Map;
import java.util.Scanner;

public class MapsActivity extends ActionBarActivity implements MyDialogFragment.OnFragmentInteractionListener,EventList.OnFragmentInteractionListener,
EventDetail.OnFragmentInteractionListener, AddNewEvent.OnFragmentInteractionListener, MapFragment.OnFragmentInteractionListener, FromDateTimeDilog.OnFragmentInteractionListener{



    private NavigationDrawerFragment mNavigationDrawerFragment;
    Toolbar mtoolbar;
    MarkerDataJson markerData;
    List<Map<String, ?>> MarkerList;
    DrawerLayout mDrawerLayout;
    ActionBarDrawerToggle mDrawerToggle;
    RecyclerView mDrawerlist;
    MyDrawerRecyclerViewAdapter myDrawerRecyclerViewAdapter;
    RelativeLayout mDrawer;
    LinearLayout activity;
    EventDetailsJSon eventDetailsJSon;
    private GoogleMap mMap; // Might be null if Google Play services APK is not available.
    private ActionBarDrawerToggle actionBarDrawerToggle;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        activity = (LinearLayout) findViewById(R.id.linear);
        mtoolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mtoolbar);
        try {
            eventDetailsJSon = new EventDetailsJSon(this);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        try {
            markerData = new MarkerDataJson(this);
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }

        mDrawerLayout = (DrawerLayout)findViewById(R.id.container1);
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

        /*
        GoogleMapOptions mapOptions = new GoogleMapOptions();
        mMapFragment = SupportMapFragment.newInstance(mapOptions);
        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.map, mMapFragment)
                .commit();
        mMap = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map)).getMap();
        setUpMap();
        //setUpMapIfNeeded();
        */


        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.container, MapFragment.newInstance("1","2",markerData))
                .commit();

    }


    private void selectItem(int position) {
        switch (position){
            case 0:
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.container, MapFragment.newInstance("1", "2", markerData))
                        .addToBackStack(null)
                        .commit();
                break;
            case 1:
               getSupportFragmentManager().beginTransaction()
                        .replace(R.id.container, EventList.newInstance(eventDetailsJSon.getEventsList(),1))
                       .addToBackStack(null)
                        .commit();
                break;
            case 2:
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.container,EventList.newInstance(eventDetailsJSon.getEventsList(),0))
                        .addToBackStack(null)
                        .commit();
                break;
            case 4:
                Intent intent;
                intent = new Intent(this,ViewPagerActivity.class);
                startActivity(intent);
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
        //MapsetUpMapIfNeeded();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_master_detail, menu);
        return true;
    }

    @Override
    public void onFragmentInteraction(Uri uri) {
        //nothing
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



    /**
     * This is where we can add markers or lines, add listeners or move the camera. In this case, we
     * just add a marker near Africa.
     * <p/>
     * This should only be called once and when we are sure that {@link #mMap} is not null.
     */


}
