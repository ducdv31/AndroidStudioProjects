package com.example.imqtt.navigation.tabinmain;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.example.imqtt.MainActivity;
import com.example.imqtt.R;
import com.example.imqtt.sharedpreference.DataLocalManager;

public class PublishFragment extends Fragment {

    private EditText topic_pub;
    private EditText content;
    private Button publish;
    private MainActivity mainActivity;

    public PublishFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View pubView = inflater.inflate(R.layout.fragment_publish, container, false);
        topic_pub = pubView.findViewById(R.id.topic_pub_mqtt);
        content = pubView.findViewById(R.id.content_mqtt);
        publish = pubView.findViewById(R.id.publish_mqtt);
        mainActivity = (MainActivity) getActivity();


        publish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String Topic = topic_pub.getText().toString();
                String Content = content.getText().toString();
                if (!Topic.isEmpty()){
                    mainActivity.publish(Topic, Content, false);
                }

            }
        });

        return pubView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        restoreSubConfig();
    }

    @Override
    public void onPause() {
        super.onPause();
        DataLocalManager.setTopicPubMQTT(topic_pub.getText().toString());
        DataLocalManager.setContentPubMQTT(content.getText().toString());
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
        topic_pub.setText(DataLocalManager.getTopicPubMQTT());
        content.setText(DataLocalManager.getContentPubMQTT());
    }

}