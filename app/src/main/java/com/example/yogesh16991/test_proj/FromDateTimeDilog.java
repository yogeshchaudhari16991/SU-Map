package com.example.yogesh16991.test_proj;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

import java.sql.Time;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link FromDateTimeDilog.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link FromDateTimeDilog#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FromDateTimeDilog extends android.support.v4.app.DialogFragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    public static final String ARGS_DATE = "FromDate";
    public static final String ARGS_Time = "FromTime";
    //public static final String ARGS_TIME = "FromTime";
    private Date fromdate;
   // private Time fromtime;
    private String format = "";
    private StringBuilder timestring ;


    private OnFragmentInteractionListener mListener;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment FromDateTimeDilog.
     */
    // TODO: Rename and change types and number of parameters
    public static FromDateTimeDilog newInstance(Date fromdate) {
        FromDateTimeDilog fragment = new FromDateTimeDilog();
        Bundle args = new Bundle();
        args.putSerializable(ARGS_DATE, fromdate);
       // args.putSerializable(ARGS_TIME, fromtime);
        fragment.setArguments(args);
        return fragment;
    }

    public FromDateTimeDilog() {

        // Required empty public constructor
    }


     public Dialog onCreateDialog(Bundle savedInstanceState) {
        fromdate = (Date) getArguments().getSerializable(ARGS_DATE);
      //  fromtime = (Time) getArguments().getSerializable(ARGS_TIME);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(fromdate);
        int year =calendar.get(Calendar.YEAR);
        final int month = calendar.get(Calendar.MONTH);
        final int day= calendar.get(Calendar.DAY_OF_MONTH);
        final int hour = calendar.get(Calendar.HOUR_OF_DAY);
        final int min = calendar.get(Calendar.MINUTE);
        View v = getActivity().getLayoutInflater().inflate(R.layout.fragment_from_date_time_dilog,null);
         final TimePicker timePicker = (TimePicker) v.findViewById(R.id.fomtimePicker);
        // time = (TextView) findViewById(R.id.textView1);
        final DatePicker datePicker = (DatePicker)v.findViewById(R.id.fromdatePicker);
        datePicker.init(year,month,day, new DatePicker.OnDateChangedListener() {
            @Override
            public void onDateChanged(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            }
        });
        AlertDialog.Builder alertDialogBuilder =new AlertDialog.Builder(getActivity());
        alertDialogBuilder.setView(v)
                .setTitle("From Date/From Time")
                .setMessage("Select From Date and From Time")
                .setPositiveButton(android.R.string.ok,new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if(getTargetFragment()!=null){
                            Intent i = new Intent();
                            fromdate= new GregorianCalendar(datePicker.getYear(),datePicker.getMonth(),datePicker.getDayOfMonth()).getTime();

                            getArguments().putSerializable(ARGS_DATE,fromdate);


                            i.putExtra(ARGS_DATE, fromdate);
                            i.putExtra(ARGS_Time, timePicker.getCurrentHour().toString());

                            //i.putExtra("min ", timePicker.getCurrentMinute().toString());
                          //  i.putExtra(ARGS_TIME, fromtime);
                            // Toast.makeText(getActivity(),"nothing returned"+"  " + mname +"   "+check,Toast.LENGTH_SHORT).show();
                            getTargetFragment().onActivityResult(getTargetRequestCode(),Activity.RESULT_OK,i);
                        }
                        else

                            Toast.makeText(getActivity(), "Nothing to Return", Toast.LENGTH_SHORT).show();
                    }
                });
        return alertDialogBuilder.create();
    }

    public StringBuilder showTime(int hour, int min) {
        if (hour == 0) {
            hour += 12;
            format = "AM";
        } else if (hour == 12) {
            format = "PM";
        } else if (hour > 12) {
            hour -= 12;
            format = "PM";
        } else {
            format = "AM";
        }
        timestring = (new StringBuilder().append(hour).append(" : ").append(min)
                .append(" ").append(format));
        return timestring;
    }

    /*
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_from_date_time_dilog, container, false);
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
