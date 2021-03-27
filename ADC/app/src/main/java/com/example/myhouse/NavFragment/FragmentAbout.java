package com.example.myhouse.NavFragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.example.myhouse.R;

public class FragmentAbout extends Fragment {
    private View aboutView;

    public FragmentAbout() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        aboutView = inflater.inflate(R.layout.fragment_about, container, false);
        // Inflate the layout for this fragment
        return aboutView;
    }
}