package com.example.myhouse.NavFragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myhouse.R;
import com.example.myhouse.datadapter.DataDatabase;
import com.example.myhouse.datadapter.DataDatabaseAdapter;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class FragmentDatabase extends Fragment {
    private final DatabaseReference databaseReference = FirebaseDatabase
            .getInstance()
            .getReference();
    private DataDatabaseAdapter dataDatabaseAdapter;

    public FragmentDatabase() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View databaseView =
                inflater.inflate(R.layout.fragment_database, container, false);
        RecyclerView recyclerView = databaseView.findViewById(R.id.recycleView_database);
        dataDatabaseAdapter = new DataDatabaseAdapter(getActivity());
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(), 1);
        recyclerView.setLayoutManager(gridLayoutManager);
//        userDataAdapter.setData(getListData());
        recyclerView.setAdapter(dataDatabaseAdapter);

//        GetListData();

        return databaseView;
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onResume() {
        super.onResume();
        GetListData();
    }

    private void GetListData() {
        ArrayList<DataDatabase> dataDatabases = new ArrayList<>();

        databaseReference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

                dataDatabases.add(new DataDatabase(snapshot.getKey()));

                dataDatabaseAdapter.setData(dataDatabases);
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }
}