package com.example.yogesh16991.test_proj;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.SeekBar;

import java.util.Date;


public class DPActivity extends ActionBarActivity {
public static final String ARGS_STRING = "string";
    public static final String ARGS_CHECK = "check";
    public static final String ARGS_CHECK1 = "check1";
   public static final String ARGS_SEEK = "seek";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dp);
        Intent intent = getIntent();
        String s = intent.getExtras().getString(ARGS_STRING);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, new PlaceholderFragment())
                    .commit();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_master_detail, menu);
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

        return super.onOptionsItemSelected(item);
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {
        private Date mdate;
        EditText mEdit;
        CheckBox checkBox;
        CheckBox checkBox1;
        SeekBar seekBar;

        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_d, container, false);
            mEdit = (EditText)rootView.findViewById(R.id.editText);
            checkBox= (CheckBox)rootView.findViewById(R.id.checkBox);
            checkBox1= (CheckBox)rootView.findViewById(R.id.checkBox1);
            seekBar = (SeekBar)rootView.findViewById(R.id.seekBar);

            Button btn_ok = (Button)rootView.findViewById(R.id.btn_ok);
            btn_ok.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    boolean i = checkBox.isChecked();
                    String check;
                    if (i){
                        check = "CheckBox Clicked";
                    }
                    else check = "CheckBox  not Clicked";
                    boolean j = checkBox1.isChecked();
                    String check1;
                    if (j){
                        check1 = "CheckBox Clicked";
                    }
                    else check1 = "CheckBox  not Clicked";
                    Intent intent=new Intent();

                    intent.putExtra(ARGS_STRING,mEdit.getText().toString());
                    intent.putExtra(ARGS_CHECK,check);
                    intent.putExtra(ARGS_CHECK1,check1);
                    intent.putExtra(ARGS_SEEK,seekBar.getProgress());
                    getActivity().setResult(RESULT_OK, intent);
                    getActivity().finish();
                }
            });
            return rootView;
        }
    }
}
