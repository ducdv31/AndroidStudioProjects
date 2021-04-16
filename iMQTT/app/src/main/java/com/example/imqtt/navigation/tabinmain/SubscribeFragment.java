package com.example.imqtt.navigation.tabinmain;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.imqtt.MainActivity;
import com.example.imqtt.R;
import com.example.imqtt.navigation.tabinmain.rcv_adapter.DataModel;
import com.example.imqtt.navigation.tabinmain.rcv_adapter.SubDataAdapter;
import com.example.imqtt.sharedpreference.DataLocalManager;
import com.ramotion.foldingcell.FoldingCell;

import java.util.ArrayList;
import java.util.List;

public class SubscribeFragment extends Fragment {

    private FoldingCell foldingCell;
    private EditText topic_sub;
    private EditText qos;
    private MainActivity mainActivity;
    private RecyclerView recyclerView;
    public static SubDataAdapter subDataAdapter;
    private TextView title_sub;

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
        title_sub = subView.findViewById(R.id.tv_status_sub);
        Button subscribe = subView.findViewById(R.id.subscribe_mqtt);
        Button unsubscribe = subView.findViewById(R.id.unsubscribe_mqtt);
        mainActivity = (MainActivity) getActivity();
        recyclerView = subView.findViewById(R.id.rcv_sub_received);
        subDataAdapter = new SubDataAdapter(requireContext());

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(requireContext(),
                RecyclerView.VERTICAL,
                false);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(subDataAdapter);
        RecyclerView.ItemDecoration itemDecoration = new DividerItemDecoration(requireContext(), DividerItemDecoration.VERTICAL);
        recyclerView.addItemDecoration(itemDecoration);
        unsubscribe.setBackgroundColor(Color.RED);


        foldingCell.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                foldingCell.toggle(false);
                if (foldingCell.isUnfolded()) {
                    closeKeyboard();
                }
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
                    if (foldingCell.isUnfolded()) {
                        closeKeyboard();
                    }
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
                if (foldingCell.isUnfolded()) {
                    closeKeyboard();
                }
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
        closeKeyboard();
    }

    @Override
    public void onResume() {
        super.onResume();
        restoreSubConfig();
        closeKeyboard();
    }

    @Override
    public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);
        restoreSubConfig();
    }

    private void restoreSubConfig() {
        topic_sub.setText(DataLocalManager.getTopicSubMQTT());
        qos.setText(DataLocalManager.getQosSubMQTT());
    }

    private void closeKeyboard() {
        // this will give us the view
        // which is currently focus
        // in this layout
        View view = requireActivity().getCurrentFocus();

        // if nothing is currently
        // focus then this will protect
        // the app from crash
        if (view != null) {

            // now assign the system
            // service to InputMethodManager
            InputMethodManager manager
                    = (InputMethodManager) requireActivity()
                    .getSystemService(Context.INPUT_METHOD_SERVICE);
            manager.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }
}