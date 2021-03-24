package com.example.iothome;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

public class MoreFragment extends Fragment {
    private Button webbtn, setData;
    public MoreFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View moreView = inflater.inflate(R.layout.fragment_more,
                container,
                false);
        webbtn = moreView.findViewById(R.id.web_btn);
        setData = moreView.findViewById(R.id.setData);
        webbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent openWeb = new Intent(Intent.ACTION_VIEW, Uri.parse("https://duc-bkhn-k62.web.app/"));
                startActivity(openWeb);
            }
        });
        setData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(),setDataActivity.class);
                startActivity(intent);
            }
        });
        // Inflate the layout for this fragment
        return moreView;
    }
}