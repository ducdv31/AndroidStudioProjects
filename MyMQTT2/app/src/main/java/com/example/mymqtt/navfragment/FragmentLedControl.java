package com.example.mymqtt.navfragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.example.mymqtt.MainActivity;
import com.example.mymqtt.R;
import com.example.mymqtt.rcvadapter.LedControlModel;
import com.example.mymqtt.rcvadapter.RCVLedControlAdapter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class FragmentLedControl extends Fragment {

    private EditText topic_led_all;
    private RecyclerView recyclerView;
    private RCVLedControlAdapter rcvLedControlAdapter;
    private MainActivity mainActivity;
    private int led_total = 0;

    public FragmentLedControl() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View ledControlView = inflater.inflate(R.layout.fragment_led_control, container, false);
        mainActivity = (MainActivity) getActivity();
        topic_led_all = ledControlView.findViewById(R.id.topic_list_led);
        recyclerView = ledControlView.findViewById(R.id.rcv_list_led_control);
        FloatingActionButton fab_add = ledControlView.findViewById(R.id.add_led);
        FloatingActionButton fab_minus = ledControlView.findViewById(R.id.minus_led);
        FloatingActionButton fab_save = ledControlView.findViewById(R.id.save_led);

        rcvLedControlAdapter = new RCVLedControlAdapter(requireActivity(), mainActivity);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(requireActivity(),
                RecyclerView.VERTICAL,
                false);
        recyclerView.setAdapter(rcvLedControlAdapter);
        recyclerView.setLayoutManager(linearLayoutManager);

        fab_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                led_total++;
                rcvLedControlAdapter.setData(getData(led_total, topic_led_all.getText().toString()));
            }
        });
        fab_minus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                led_total--;
                if (led_total < 0) {
                    led_total = 0;
                }
                rcvLedControlAdapter.setData(getData(led_total, topic_led_all.getText().toString()));
            }
        });
        fab_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rcvLedControlAdapter.setData(getData(led_total, topic_led_all.getText().toString()));
            }
        });

        return ledControlView;
    }

    private List<LedControlModel> getData(int led_num, String topic) {
        List<LedControlModel> modelList = new ArrayList<>();
        for (int i = 1; i <= led_num; i++) {
            modelList.add(new LedControlModel(i, topic));
        }

        return modelList;
    }
}