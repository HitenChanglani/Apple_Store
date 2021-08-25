package com.example.inclass05;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;


public class AppDetailsFragment extends Fragment {


    private static final String ARG_APP_DATA = "APP_DATA";
    DataServices.App app;
    TextView appNameDetails, artistNameDetails, releaseDateDetails;
    ListView listViewGenreDetails;
    ArrayAdapter<String> adapter;


    public AppDetailsFragment() {
        // Required empty public constructor
    }


    // TODO: Rename and change types and number of parameters
    public static AppDetailsFragment newInstance(DataServices.App appData) {
        AppDetailsFragment fragment = new AppDetailsFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_APP_DATA, appData);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            app = (DataServices.App) getArguments().getSerializable(ARG_APP_DATA);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_app_details, container, false);
        getActivity().setTitle(getResources().getString(R.string.appDetailsTitle));

        appNameDetails = view.findViewById(R.id.appNameDetails);
        artistNameDetails = view.findViewById(R.id.artistNameDetails);
        releaseDateDetails = view.findViewById(R.id.releaseDateDetails);
        listViewGenreDetails = view.findViewById(R.id.listViewGenreDetails);

        appNameDetails.setText(app.name);
        artistNameDetails.setText(app.artistName);
        releaseDateDetails.setText(app.releaseDate);

        adapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_1, android.R.id.text1, app.genres);
        listViewGenreDetails.setAdapter(adapter);

        return view;
    }
}