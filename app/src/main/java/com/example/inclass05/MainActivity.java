package com.example.inclass05;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class MainActivity extends AppCompatActivity implements LoginFragment.loginInterface, RegisterAccountFragment.registerAccountInterface, AppCategoriesFragment.appCategoriesInterface, AppListFragment.appListInterface {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportFragmentManager().beginTransaction()
                .add(R.id.containerView, new LoginFragment(), getResources().getString(R.string.loginTitle))
                .commit();

    }

    @Override
    public void goToRegisterAccount() {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.containerView, new RegisterAccountFragment(), getResources().getString(R.string.registerAccountTitle))
                .commit();
    }

    @Override
    public void loginAction(String token) {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.containerView, AppCategoriesFragment.newInstance(token), getResources().getString(R.string.appCategoriesTitle))
                .commit();
    }

    @Override
    public void cancelRegistration() {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.containerView, new LoginFragment(), getResources().getString(R.string.loginTitle))
                .commit();
    }

    @Override
    public void submitNewAccountDetails(String token) {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.containerView, AppCategoriesFragment.newInstance(token), getResources().getString(R.string.appCategoriesTitle))
                .commit();
    }

    @Override
    public void goToAppList(String token, String categoryName) {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.containerView, AppListFragment.newInstance(token, categoryName), categoryName)
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void logout() {
        getSupportFragmentManager().popBackStack();
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.containerView, new LoginFragment(), getResources().getString(R.string.loginTitle))
                .commit();
    }

    @Override
    public void getAppDetails(DataServices.App appData) {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.containerView, AppDetailsFragment.newInstance(appData))
                .addToBackStack(null)
                .commit();
    }
}