package com.example.yogesh16991.test_proj;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.Date;


/**
 * A simple {@link android.support.v4.app.Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link DialogFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link DialogFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DialogFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER

    private static final int REQUEST_DATE = 0;
    private static final int REQUEST_STRING = 1;
    private static final int PICK_CONTACT_REQUEST= 2;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment DialogFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static DialogFragment newInstance(){//String param1, String param2) {
        DialogFragment fragment = new DialogFragment();
        Bundle args = new Bundle();
       // args.putString(ARG_PARAM1, param1);
        //args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public DialogFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {


        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_dialog, container, false);
        Button btn_dialog =(Button)rootView.findViewById(R.id.btn_dialog);
        result1 = (TextView) rootView.findViewById(R.id.result);
        result2 = (TextView) rootView.findViewById(R.id.result2);
        result3 = (TextView) rootView.findViewById(R.id.result3);

        btn_dialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                showDatePickerDialog();
            }
        });

        Button btn_activity = (Button)rootView.findViewById(R.id.btn_activity);
        btn_activity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String s = "asd";
                Intent i = new Intent(getActivity(),DPActivity.class);
                i.putExtra(DPActivity.ARGS_STRING,s);
                startActivityForResult(i,REQUEST_STRING);


            }
        });

        Button btn_contact = (Button)rootView.findViewById(R.id.btn_contact);
        btn_contact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Intent.ACTION_PICK, Uri.parse("content://contacts"));
                intent.setType(ContactsContract.CommonDataKinds.Phone.CONTENT_TYPE);
                startActivityForResult(intent,PICK_CONTACT_REQUEST);
            }
        });
        // Inflate the layout for this fragment
        return rootView;
    }

    private void showDatePickerDialog() {
        Date date=new Date(System.currentTimeMillis());
        MyDialogFragment dialog= MyDialogFragment.newInstance(date);
        dialog.setTargetFragment(DialogFragment.this,REQUEST_DATE);
        dialog.show(getFragmentManager(),"Datepicker Dialog");
    }
    private TextView result1;
    private TextView result2;
    private TextView result3;
    @Override
    public void onActivityResult(int requestCode, int resultCode,Intent data){
        if(resultCode != Activity.RESULT_OK)return;
        String s;
        if(requestCode == REQUEST_DATE){
            Date date=(Date)data.getSerializableExtra(MyDialogFragment.ARGS_DATE);
            String name = (String)data.getExtras().get(MyDialogFragment.ARGS_NAME);
            s = (String)data.getExtras().get(MyDialogFragment.ARGS_CHECK);
            if(date != null)
            result1.setText(date.toString());
            if(name != null)
                result2.setText(name);
            if(s != null)
                result3.setText(s);
        }
        if(requestCode == REQUEST_STRING) {
            s =(String) data.getExtras().get(DPActivity.ARGS_STRING);
            if(s != null)
            result1.setText(s);
            s = (String)data.getExtras().get(DPActivity.ARGS_CHECK);
            if(s != null)
            result2.setText(s);
            String progress = (String)data.getExtras().get(DPActivity.ARGS_CHECK1);
            if(s != null)
                result3.setText(progress);
        }
        if(requestCode == PICK_CONTACT_REQUEST){
            Uri contactUri =data.getData();
            String[] projection = {
                    ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME,
                    ContactsContract.CommonDataKinds.Phone.NUMBER};
            Cursor cursor =getActivity().getContentResolver()
                    .query(contactUri,projection,null,null,null);
            cursor.moveToFirst();

            int column = cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER);
            String number = cursor.getString(column);
            column = cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME);
            String name = cursor.getString(column);

            result1.setText(("NAME: "+ name +"\nNUMBER : " + number));

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

}
