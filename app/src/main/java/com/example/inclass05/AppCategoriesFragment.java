package com.example.inclass05;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;


public class AppCategoriesFragment extends Fragment {


    private static final String ARG_TOKEN = "TOKEN";
    private String token;
    ListView listViewAppCategories;
    ArrayList<String> appCategories;
    ArrayAdapter<String> adapter;
    DataServices.Account accountValue;
    TextView welcomeLabel;
    appCategoriesInterface mListener;

    public AppCategoriesFragment() {
        // Required empty public constructor
    }


    // TODO: Rename and change types and number of parameters
    public static AppCategoriesFragment newInstance(String token) {
        AppCategoriesFragment fragment = new AppCategoriesFragment();
        Bundle args = new Bundle();
        args.putString(ARG_TOKEN, token);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            this.token = getArguments().getString(ARG_TOKEN);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_app_categories, container, false);
        getActivity().setTitle(getResources().getString(R.string.appCategoriesTitle));

        listViewAppCategories = view.findViewById(R.id.listViewAppCategories);
        welcomeLabel = view.findViewById(R.id.welcomeLabel);

        DataServices.getAccount(token, new DataServices.AccountResponse() {
            @Override
            public void onSuccess(DataServices.Account account) {
                accountValue = account;
            }

            @Override
            public void onFailure(DataServices.RequestException exception) {
                Toast.makeText(getActivity(), exception.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        if (accountValue != null){
            welcomeLabel.setText("Welcome " + accountValue.getName());
        }

        DataServices.getAppCategories(token, new DataServices.DataResponse<String>() {
            @Override
            public void onSuccess(ArrayList<String> data) {
                appCategories = data;
                adapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_1, android.R.id.text1, appCategories);
                listViewAppCategories.setAdapter(adapter);
            }

            @Override
            public void onFailure(DataServices.RequestException exception) {
                Toast.makeText(getActivity(), exception.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });




        listViewAppCategories.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                mListener.goToAppList(token, appCategories.get(position));
            }
        });


        view.findViewById(R.id.logoutButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.logout();
            }
        });

        return view;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof appCategoriesInterface){
            mListener = (appCategoriesInterface) context;
        }
        else {
            throw new RuntimeException(getContext() + " must be implemented");
        }
    }


    public interface appCategoriesInterface{
        public void goToAppList(String token, String categoryName);
        public void logout();
    }

}