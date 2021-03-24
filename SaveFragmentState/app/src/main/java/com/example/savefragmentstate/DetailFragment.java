package com.example.savefragmentstate;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

public class DetailFragment extends Fragment {

    public static final String TAG = "Name";
    private TextView tvName;
    private Button btnBack;

    public DetailFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View detail = inflater.inflate(R.layout.fragment_detail, container, false);

        tvName = detail.findViewById(R.id.tv_name);
        btnBack = detail.findViewById(R.id.back);
        Bundle bundle = getArguments();
        if (bundle != null) {
            User user = (User) bundle.get("object_user");
            if (user != null)
                tvName.setText(user.getName());
        }

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (getFragmentManager() != null)
                    getFragmentManager().popBackStack();
            }
        });

        return detail;
    }
}