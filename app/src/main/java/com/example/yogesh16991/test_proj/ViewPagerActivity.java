package com.example.yogesh16991.test_proj;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;
import java.util.Locale;
import java.util.Map;


public class ViewPagerActivity extends ActionBarActivity implements EventList.OnFragmentInteractionListener,EventDetail.OnFragmentInteractionListener {
    MyFragmentStatePagerAdapter myPagerAdapter;

    ViewPager mViewPager;
    EventDetailsJSon eventData;
    Context mcontext;
    List<Map<String,?>> eventsList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Intent intent = getIntent();
        eventsList = (List<Map<String,?>>)intent.getSerializableExtra("EVENT_LIST");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_pager);
        try {
            eventData = new EventDetailsJSon(this);
        } catch (Exception e) {
            e.printStackTrace();
        }
        myPagerAdapter = new MyFragmentStatePagerAdapter(getSupportFragmentManager(), eventData.getSize());
        mViewPager = (ViewPager) findViewById(R.id.pager);
        mViewPager.setAdapter(myPagerAdapter);
        mViewPager.setCurrentItem(0);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, new PlaceholderFragment())
                    .commit();
        }
       mcontext = getApplicationContext();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_view_pager, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        if(id == R.id.bckToMapView){
           finish();
        }


        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {

        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            setRetainInstance(true);
            View rootView = inflater.inflate(R.layout.fragment_view_pager, container, false);

            return rootView;
        }
    }

    public class MyFragmentStatePagerAdapter extends FragmentStatePagerAdapter {

        int count;

        public MyFragmentStatePagerAdapter(FragmentManager fm, int size) {
            super(fm);
            count = size;
        }

        @Override
        public Fragment getItem(int position) {
            ViewPagerUtilities vpu = new ViewPagerUtilities(mcontext);
            return vpu.createHashmap(eventsList,position);
        }

        @Override
        public int getCount() {
            return 5;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            Locale l = Locale.getDefault();
            String name;
            switch (position) {
                case 0:
                    name = "EDUCATIONAL";
                    break;
                case 1:
                    name = "FOOD";
                    break;
                case 2:
                    name = "SPORTS";
                    break;
                case 3:
                    name = "ENTERTAINMENT";
                    break;
                case 4:
                    name = "OTHERS";
                    break;
                default:
                    name = "EDUCATIONAL";
                    break;
            }
            return name.toUpperCase(l);
        }
    }
}