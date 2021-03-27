package com.example.myhouse.NavFragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myhouse.MainActivity;
import com.example.myhouse.R;
import com.example.myhouse.animation.TranslateAnimationUtil;
import com.example.myhouse.datahistory.DataKKVV;
import com.example.myhouse.datahistory.THValue;
import com.example.myhouse.datahistory.adapter.HistoryTHAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class FragmentHistory extends Fragment {

    private HistoryTHAdapter historyTHAdapter;

    public FragmentHistory() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View History = inflater.inflate(R.layout.fragment_history, container, false);
        RecyclerView recyclerViewH = History.findViewById(R.id.recycler_view_humidity);
        historyTHAdapter = new HistoryTHAdapter(getActivity());
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(),
                RecyclerView.VERTICAL,
                false);
        recyclerViewH.setLayoutManager(linearLayoutManager);

        recyclerViewH.setAdapter(historyTHAdapter);

        recyclerViewH.setOnTouchListener(new TranslateAnimationUtil(getContext(), MainActivity.fab_map));

        return History;
    }

    @Override
    public void onPause() {
        super.onPause();
        MainActivity.fab_map.setVisibility(View.VISIBLE);
    }

    @Override
    public void onResume() {
        super.onResume();
        new Thread(this::GetListData).start();
//        GetListData();
    }

    private void GetListData() {    /* Key: { t: value1, h: value2 } */
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
        databaseReference.child("DHT11/History")
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {

                        if (snapshot.hasChildren()) {
                            ArrayList<DataKKVV> dataKKVVS = new ArrayList<>();
                            for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                                String date = dataSnapshot.getKey();
                                if (dataSnapshot.hasChildren()) {
                                    for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                                        THValue thValue = dataSnapshot1.getValue(THValue.class);
                                        dataKKVVS.add(new DataKKVV(date,
                                                dataSnapshot1.getKey(),
                                                thValue.getT() + " ºC",
                                                thValue.getH() + " %"));
                                    }
                                }

                            }
                            historyTHAdapter.setData(dataKKVVS);
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

    }

}