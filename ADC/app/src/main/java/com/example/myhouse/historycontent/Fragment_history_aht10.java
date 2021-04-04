package com.example.myhouse.historycontent;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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
import java.util.Objects;

public class Fragment_history_aht10 extends Fragment {

    private RecyclerView recyclerView;
    private HistoryTHAdapter historyTHAdapter;

    public Fragment_history_aht10() {
        // Required empty public constructor
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View hisAHT10 = inflater.inflate(R.layout.fragment_history_aht10, container, false);

        recyclerView = hisAHT10.findViewById(R.id.rcv_his_aht10);
         historyTHAdapter = new HistoryTHAdapter(requireActivity());
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(requireActivity(),
                RecyclerView.VERTICAL,
                false);

        recyclerView.setAdapter(historyTHAdapter);
        recyclerView.setLayoutManager(linearLayoutManager);

        recyclerView.setOnTouchListener(new TranslateAnimationUtil(getContext(), MainActivity.fab_map));
        new Thread(this::GetListData).start();
        return hisAHT10;
    }

    @Override
    public void onResume() {
        super.onResume();

    }

    private void GetListData() {    /* Key: { t: value1, h: value2 } */
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
        databaseReference.child("AHT10/History")
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
                                                Objects.requireNonNull(thValue).getT() + " ºC",
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