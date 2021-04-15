package com.example.imqtt.navigation.tabinmain;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.imqtt.MainActivity;
import com.example.imqtt.R;
import com.example.imqtt.sharedpreference.DataLocalManager;
import com.ramotion.foldingcell.FoldingCell;

public class SubscribeFragment extends Fragment {

    private FoldingCell foldingCell;
    private EditText topic_sub;
    private EditText qos;
    private MainActivity mainActivity;

    public SubscribeFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View subView = inflater.inflate(R.layout.fragment_subscribe, container, false);
        foldingCell = subView.findViewById(R.id.folding_cell);
        topic_sub = subView.findViewById(R.id.topic_subscribe_mqtt);
        qos = subView.findViewById(R.id.qos_mqtt);
        Button subscribe = subView.findViewById(R.id.subscribe_mqtt);
        Button unsubscribe = subView.findViewById(R.id.unsubscribe_mqtt);
        mainActivity = (MainActivity) getActivity();


        foldingCell.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                foldingCell.toggle(false);
            }
        });

        subscribe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String Topic = topic_sub.getText().toString();
                String Qos = qos.getText().toString();
                if (!Topic.isEmpty() && !Qos.isEmpty()) {
                    mainActivity.subscribe_topic(Topic,
                            Integer.parseInt(Qos));
                } else {
                    Toast.makeText(mainActivity, "Topic or QoS is not valid", Toast.LENGTH_SHORT).show();
                }
            }
        });
        unsubscribe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String Topic = topic_sub.getText().toString();
                mainActivity.unsubscribe_topic(Topic);
            }
        });
        return subView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        restoreSubConfig();
    }

    @Override
    public void onPause() {
        super.onPause();
        DataLocalManager.setTopicSubMQTT(topic_sub.getText().toString());
        DataLocalManager.setQosSubMQTT(qos.getText().toString());
    }

    @Override
    public void onResume() {
        super.onResume();
        restoreSubConfig();
    }

    @Override
    public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);
        restoreSubConfig();
    }

    private void restoreSubConfig(){
        topic_sub.setText(DataLocalManager.getTopicSubMQTT());
        qos.setText(DataLocalManager.getQosSubMQTT());
    }

}