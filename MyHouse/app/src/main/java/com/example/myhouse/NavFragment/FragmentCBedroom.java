package com.example.myhouse.NavFragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.myhouse.R;

public class FragmentCBedroom extends Fragment {

    public FragmentCBedroom() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View CBedroom = inflater.inflate(R.layout.fragment_c_bedroom, container, false);

        return CBedroom;
    }
}