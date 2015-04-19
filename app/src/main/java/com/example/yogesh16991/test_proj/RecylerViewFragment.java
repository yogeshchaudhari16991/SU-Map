package com.example.yogesh16991.test_proj;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.json.JSONException;

//import android.app.Fragment;


/**
 * A simple {@link android.support.v4.app.Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link RecylerViewFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link RecylerViewFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class RecylerViewFragment extends Fragment {
    private static final String ARG_MOVIE = "movie";
    MovieDataJson movieData;
        RecyclerView recyclerView;
    OnFragmentInteractionListener mListener = null;

        private static final String OPTION = "option";

    public static RecylerViewFragment newInstance(int option) {
      RecylerViewFragment fragment = new RecylerViewFragment();
        Bundle args = new Bundle();
        args.putInt(OPTION, option);
        fragment.setArguments(args);
        return fragment;
    }
    public RecylerViewFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setHasOptionsMenu(true);
        setRetainInstance(true);
        try {
            movieData = new MovieDataJson(getActivity());
        }
        catch(JSONException e)
        {
            e.printStackTrace();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        int option;
        if(getArguments()!= null){ option = getArguments().getInt(OPTION);}
        else option = 0;
        View rootView = null;
        final myRecyclerViewAdapter recyclerviewAdaptor;


        switch (option){
            case 0:
                rootView = inflater.inflate(R.layout.fragment_recycler_view, container, false);
                recyclerView = (RecyclerView) rootView.findViewById(R.id.listview);
                recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
                recyclerviewAdaptor = new myRecyclerViewAdapter(getActivity(), movieData.getMoviesList(),0);
                recyclerView.setAdapter(recyclerviewAdaptor);
                recyclerView.setHasFixedSize(true);
                break;
            case 1:
                rootView = inflater.inflate(R.layout.fragment_recycler_view, container, false);
                recyclerView = (RecyclerView) rootView.findViewById(R.id.listview);
                recyclerView.setLayoutManager(new GridLayoutManager(getActivity(),4));
                recyclerviewAdaptor = new myRecyclerViewAdapter(getActivity(), movieData.getMoviesList(),1);
                recyclerView.setAdapter(recyclerviewAdaptor);
                recyclerView.setHasFixedSize(true);
                break;
            default:
                rootView = inflater.inflate(R.layout.fragment_recycler_view, container, false);
                recyclerView = (RecyclerView) rootView.findViewById(R.id.listview);
                recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
                recyclerviewAdaptor = new myRecyclerViewAdapter(getActivity(), movieData.getMoviesList(),0);
                recyclerView.setAdapter(recyclerviewAdaptor);
                recyclerView.setHasFixedSize(true);
                break;

        }



       /* recyclerviewAdaptor.setOnItemClickListener(new myRecyclerViewAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                HashMap<String, ?> movie = (HashMap<String, ?>) movieData.getItem(position);
                Intent intent;
                intent = new Intent(getActivity(),ActivityActionBar.class);
                intent.putExtra(ARG_MOVIE,movie);
                startActivity(intent);
                *//*getActivity().getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.itemlist, new DetailView().newInstance(movie))
                        .addToBackStack(null)
                        .commit();*//*

            }

            @Override
            public void onItemLongClick(View view, int position) {
                //getActivity().startActionMode(new ActionBarCallBack(position));
            }

            @Override
            public void onOverflowMenuClick(View v, int position) {

//            }

        });

*/
        return rootView;        // Inflate the layout for this fragment
       // return inflater.inflate(R.layout.fragment_recyler_view, container, false);
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