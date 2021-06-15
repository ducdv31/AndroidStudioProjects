package com.example.mvvmjava;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.example.mvvmjava.databinding.ActivityMainBinding;
import com.example.mvvmjava.model.UserViewModel;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ActivityMainBinding activityMainBinding = DataBindingUtil.setContentView(this,
                R.layout.activity_main);
        UserViewModel userViewModel = new UserViewModel();
        activityMainBinding.setUserModel(userViewModel);
    }
}