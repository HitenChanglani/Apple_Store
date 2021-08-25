package com.example.inclass05;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;


public class LoginFragment extends Fragment {

    String email, password;
    EditText editEmailLogin, editPasswordLogin;
    loginInterface mListener;

    public LoginFragment() {
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
        View view = inflater.inflate(R.layout.fragment_login, container, false);

        getActivity().setTitle(getResources().getString(R.string.loginTitle));

        editEmailLogin = view.findViewById(R.id.editEmailLogin);
        editPasswordLogin = view.findViewById(R.id.editPasswordLogin);

        view.findViewById(R.id.loginButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                email = editEmailLogin.getText().toString();
                password = editPasswordLogin.getText().toString();

                if (email.isEmpty() || password.isEmpty()){
                    Toast.makeText(getActivity(), getResources().getString(R.string.mandatoryFields), Toast.LENGTH_SHORT).show();
                }
                else{
                    DataServices.login(email, password, new DataServices.AuthResponse() {
                        @Override
                        public void onSuccess(String token) {
                            mListener.loginAction(token);
                        }

                        @Override
                        public void onFailure(DataServices.RequestException exception) {
                            Toast.makeText(getActivity(), exception.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });


        view.findViewById(R.id.createNewAccount).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.goToRegisterAccount();
            }
        });

        return view;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof loginInterface){
            mListener = (loginInterface) context;
        }
        else{
            throw new RuntimeException(context.toString() + " must be implemented");
        }
    }

    public interface loginInterface{
        public void goToRegisterAccount();
        public void loginAction(String token);
    }

}