package com.example.myhouse.NavFragment;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.fragment.app.Fragment;

import com.example.myhouse.R;

public class FragmentMore extends Fragment {

    public FragmentMore() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View moreView = inflater.inflate(R.layout.fragment_more, container, false);
        Button webbtn = moreView.findViewById(R.id.web_btn);
        webbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent openWeb = new Intent(Intent.ACTION_VIEW, Uri.parse("https://duc-bkhn-k62.web.app/"));
                startActivity(openWeb);
            }
        });
        return moreView;
    }
}