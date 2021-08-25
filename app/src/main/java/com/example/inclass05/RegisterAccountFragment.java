package com.example.inclass05;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


public class RegisterAccountFragment extends Fragment {

    String name, email, password;
    EditText editNameRegisterAccount, editEmailRegisterAccount, editPasswordRegisterAccount;
    registerAccountInterface mListener;

    public RegisterAccountFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_register_account, container, false);

        getActivity().setTitle(getResources().getString(R.string.registerAccountTitle));

        editEmailRegisterAccount = view.findViewById(R.id.editEmailRegisterAccount);
        editNameRegisterAccount = view.findViewById(R.id.editNameRegisterAccount);
        editPasswordRegisterAccount = view.findViewById(R.id.editPasswordRegisterAccount);

        view.findViewById(R.id.submitButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                name = editNameRegisterAccount.getText().toString();
                email = editEmailRegisterAccount.getText().toString();
                password = editPasswordRegisterAccount.getText().toString();
                String email_regex = "[a-zA-Z0-9._-]+@[a-zA-Z]+\\.+[a-zA-Z]+";

                if (name.isEmpty() || email.isEmpty() || password.isEmpty()){
                    Toast.makeText(getActivity(), getResources().getString(R.string.mandatoryFields), Toast.LENGTH_SHORT).show();
                }
                else if (!email.matches(email_regex)){
                    Toast.makeText(getActivity(), getResources().getString(R.string.invalidEmail), Toast.LENGTH_SHORT).show();
                }
                else {
                    DataServices.register(name, email, password, new DataServices.AuthResponse() {
                        @Override
                        public void onSuccess(String token) {
                            mListener.submitNewAccountDetails(token);
                        }

                        @Override
                        public void onFailure(DataServices.RequestException exception) {
                            Toast.makeText(getActivity(), exception.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });


        view.findViewById(R.id.cancelRegisterAccount).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.cancelRegistration();
            }
        });

        return view;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof registerAccountInterface){
            mListener = (registerAccountInterface) context;
        }
        else {
            throw new RuntimeException(context.toString() + " must be implemented");
        }
    }

    public interface registerAccountInterface{
        public void cancelRegistration();
        public void submitNewAccountDetails(String token);
    }

}