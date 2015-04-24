package com.example.yogesh16991.test_proj;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class MapsActivity extends ActionBarActivity implements MyDialogFragment.OnFragmentInteractionListener,EventList.OnFragmentInteractionListener,
EventDetail.OnFragmentInteractionListener, AddNewEvent.OnFragmentInteractionListener, MapFragment.OnFragmentInteractionListener, FromDateTimeDilog.OnFragmentInteractionListener{

    SharedPreferences sharedpreferences;
    public static final String MyPREFERENCES = "MyPrefs";
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
        sharedpreferences = getApplicationContext().getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor= sharedpreferences.edit();
        editor.putBoolean("ultimateJugad",true).apply();
        activity = (LinearLayout) findViewById(R.id.linear);
        mtoolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mtoolbar);
        if(eventDetailsJSon==null)
            eventDetailsJSon = new EventDetailsJSon(getApplicationContext());
         if(markerData==null)
        markerData = new MarkerDataJson();


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

        if(savedInstanceState == null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.container, MapFragment.newInstance(markerData, eventDetailsJSon))
                    .commit();
        }
    }


    private void selectItem(int position) {
        switch (position){
            case 0:
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.container, MapFragment.newInstance(markerData,eventDetailsJSon))
                        .addToBackStack(null)
                        .commit();
                break;
            case 1:
               getSupportFragmentManager().beginTransaction()
                        .replace(R.id.container, EventList.newInstance(eventDetailsJSon.getEventsList(),0))
                       .addToBackStack(null)
                        .commit();
                break;
            case 2:
                getSupportFragmentManager().beginTransaction().replace(R.id.container,AddNewEvent.newInstance(eventDetailsJSon)).addToBackStack(null).commit();
                break;
            case 3:
                Intent intent;
                intent = new Intent(this,ViewPagerActivity.class);
                intent.putExtra("EVENT_LIST", (java.io.Serializable) eventDetailsJSon.getEventsList());
                startActivity(intent);                break;
            case 4:
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.container, AboutUs.newInstance("1","2"))
                        .addToBackStack(null)
                        .commit();
                //send eventDetailsJSon to this activity using intent put method and retrieve it in viewPager activity
                break;
            default:
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.container, MapFragment.newInstance(markerData,eventDetailsJSon))
                        .addToBackStack(null)
                        .commit();
                break;
        }
        mDrawerLayout.closeDrawer(mDrawer);
    }




    @Override
    protected void onResume() {
        super.onResume();
        /*
        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.container, MapFragment.newInstance(markerData, eventDetailsJSon))
                .commit();
                */
        int size = sharedpreferences.getInt("size",-1);
        if(sharedpreferences.getBoolean("ultimateJugad",true)) {
            try {
                for (int i = 0; i < size; i++) {
                    HashMap<String, String> event = loadMap("event" + i);
                    eventDetailsJSon.eventsList.add(event);
                }
                SharedPreferences.Editor editor= sharedpreferences.edit();
                editor.putBoolean("ultimateJugad",false).apply();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private HashMap<String,String> loadMap(String mapStr){
        HashMap<String,String> outputMap = new HashMap<>();
        try{
            if (sharedpreferences!= null){
                String jsonString = sharedpreferences.getString(mapStr, (new JSONObject()).toString());
                JSONObject jsonObject = new JSONObject(jsonString);
                Iterator<String> keysItr = jsonObject.keys();
                while(keysItr.hasNext()) {
                    String key = keysItr.next();
                    String value = (String) jsonObject.get(key);
                    outputMap.put(key, value);
                }
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        return outputMap;
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
