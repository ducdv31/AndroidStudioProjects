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
import android.widget.LinearLayout;

import com.example.myhouse.MainActivity;
import com.example.myhouse.R;
import com.example.myhouse.animation.TranslateAnimationUtil;
import com.example.myhouse.datahistory.Data5V;
import com.example.myhouse.datahistory.DataKKVV;
import com.example.myhouse.datahistory.THValue;
import com.example.myhouse.datahistory.adapter.Pms7003HisAdapter;
import com.example.myhouse.model.PmsModel;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Objects;

public class Fragment_history_pms7003 extends Fragment {

    private RecyclerView recyclerView;
    private Pms7003HisAdapter pms7003HisAdapter;

    public Fragment_history_pms7003() {
        // Required empty public constructor
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View hisPms7003 = inflater.inflate(R.layout.fragment_history_pms7003, container, false);

        recyclerView = hisPms7003.findViewById(R.id.rcv_his_pms7003);
        pms7003HisAdapter = new Pms7003HisAdapter(requireContext());
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(requireContext(),
                RecyclerView.VERTICAL,
                false);

        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(pms7003HisAdapter);

        recyclerView.setOnTouchListener(new TranslateAnimationUtil(getContext(), MainActivity.fab_map));

        new Thread(this::GetListData).start();

        return hisPms7003;
    }

    private void GetListData() {    /* Key: { t: value1, h: value2 } */
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
        databaseReference.child("PMS7003/History")
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {

                        if (snapshot.hasChildren()) {
                            ArrayList<Data5V> data5VS = new ArrayList<>();
                            for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                                String date = dataSnapshot.getKey();
                                if (dataSnapshot.hasChildren()) {
                                    for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                                        PmsModel pmsModel = dataSnapshot1.getValue(PmsModel.class);
                                        data5VS.add(new Data5V(date,
                                                dataSnapshot1.getKey(),
                                                Objects.requireNonNull(pmsModel).getPm1() + "",
                                                pmsModel.getPm25() + "",
                                                pmsModel.getPm10() + ""));
                                    }
                                }

                            }
                            pms7003HisAdapter.setData(data5VS);
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

    }

}