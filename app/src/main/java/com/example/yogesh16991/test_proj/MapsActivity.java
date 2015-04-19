package com.example.yogesh16991.test_proj;

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
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapsActivity extends ActionBarActivity {



    private NavigationDrawerFragment mNavigationDrawerFragment;
    Toolbar mtoolbar;
    DrawerLayout mDrawerLayout;
    ActionBarDrawerToggle mDrawerToggle;
    RecyclerView mDrawerlist;
    MyDrawerRecyclerViewAdapter myDrawerRecyclerViewAdapter;
    RelativeLayout mDrawer;
    private GoogleMap mMap; // Might be null if Google Play services APK is not available.
    private ActionBarDrawerToggle actionBarDrawerToggle;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
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

               // selectItem(position);
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
        mMap.addMarker(new MarkerOptions().position(new LatLng(43.039, -76.135)).title("Syracuse University"));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(43.039,-76.135), 17));
    }
}