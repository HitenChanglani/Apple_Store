package com.example.inclass05;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;


public class AppListFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_TOKEN = "TOKEN";
    private static final String ARG_CATEGORY_NAME = "CATEGORY_NAME";
    private String token;
    private String categoryName;
    appListInterface mListener;
    ListView listViewAppList;
    ArrayList<DataServices.App> listOfApps;
    AppAdapter adapter;

    public AppListFragment() {
        // Required empty public constructor
    }


    // TODO: Rename and change types and number of parameters
    public static AppListFragment newInstance(String token, String categoryName) {
        AppListFragment fragment = new AppListFragment();
        Bundle args = new Bundle();
        args.putString(ARG_TOKEN, token);
        args.putString(ARG_CATEGORY_NAME, categoryName);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            token = getArguments().getString(ARG_TOKEN);
            categoryName = getArguments().getString(ARG_CATEGORY_NAME);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_app_list, container, false);
        listViewAppList = view.findViewById(R.id.listViewAppList);

        getActivity().setTitle(categoryName);

        DataServices.getAppsByCategory(token, categoryName, new DataServices.DataResponse<DataServices.App>() {
            @Override
            public void onSuccess(ArrayList<DataServices.App> data) {
                listOfApps = data;
                adapter = new AppAdapter(getActivity(), R.layout.app_list_layout, listOfApps);
                listViewAppList.setAdapter(adapter);
            }

            @Override
            public void onFailure(DataServices.RequestException exception) {
                Toast.makeText(getActivity(), exception.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        listViewAppList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                DataServices.App appData = listOfApps.get(position);
                mListener.getAppDetails(appData);
            }
        });

        return view;
    }


    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof appListInterface){
            mListener = (appListInterface) context;
        }
        else {
            throw new RuntimeException(getContext().toString() + " must be implemented");
        }
    }

    public interface appListInterface{
        public void getAppDetails(DataServices.App appData);
    }

}