package com.example.mymqtt.navfragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.mymqtt.MainActivity;
import com.example.mymqtt.R;
import com.ramotion.foldingcell.FoldingCell;

public class FragmentHome extends Fragment {

    private FoldingCell foldingCell;
    private FoldingCell foldingCell_pub;
    private MainActivity mainActivity;
    private View mqttConfView;
    private EditText topic_subscribe;
    private EditText qos;
    private EditText topic_pub;
    private EditText content_pub;
    private Button subscribe;
    private Button unsubscribe;
    private Button publish;
    private String topic_sub;
    private TextView tv_status_sub;

    public FragmentHome() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mqttConfView = inflater.inflate(R.layout.fragment_home, container, false);
        mainActivity = (MainActivity) getActivity();
        initVariable();

        initButton();

        return mqttConfView;
    }

    private void initVariable() {
        foldingCell = mqttConfView.findViewById(R.id.folding_cell);
        topic_subscribe = mqttConfView.findViewById(R.id.topic_subscribe_mqtt);
        qos = mqttConfView.findViewById(R.id.qos_mqtt);
        subscribe = mqttConfView.findViewById(R.id.subscribe_mqtt);
        unsubscribe = mqttConfView.findViewById(R.id.unsubscribe_mqtt);
        publish = mqttConfView.findViewById(R.id.publish_mqtt);
        topic_pub = mqttConfView.findViewById(R.id.topic_pub_mqtt);
        content_pub = mqttConfView.findViewById(R.id.content_mqtt);
        foldingCell_pub = mqttConfView.findViewById(R.id.folding_cell_publish);
        tv_status_sub = mqttConfView.findViewById(R.id.tv_status_sub);
    }

    private void initButton() {
        foldingCell.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                foldingCell.toggle(false);
            }
        });
        tv_status_sub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                foldingCell.toggle(false);
            }
        });
        foldingCell_pub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                foldingCell_pub.toggle(false);
            }
        });
        subscribe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                topic_sub = topic_subscribe.getText().toString();
                mainActivity.subscribe_topic(topic_sub,
                        Integer.parseInt(qos.getText().toString()));
            }
        });
        publish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String Topic = topic_pub.getText().toString();
                String Content = content_pub.getText().toString();
                mainActivity.publish(Topic, Content, false);
            }
        });

        unsubscribe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mainActivity.unsubscribe_topic(topic_subscribe.getText().toString());
            }
        });
    }


}