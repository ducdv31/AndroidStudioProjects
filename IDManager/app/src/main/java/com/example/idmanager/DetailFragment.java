package com.example.idmanager;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.idmanager.model.User;

public class DetailFragment extends Fragment {

    public static final String TAG = "Fragment Detail";

    public DetailFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View detailView = inflater.inflate(R.layout.fragment_detail, container, false);
        getDataFromUserFrag();

        return detailView;
    }

    private void getDataFromUserFrag() {
        Bundle bundle = getArguments();
        if (bundle != null) {
            User user = (User) bundle.get("object_user");
            if (user != null){
                getTimeWorker(user.getID());
            }
        }
    }

    private void getTimeWorker(String id) {

    }
}