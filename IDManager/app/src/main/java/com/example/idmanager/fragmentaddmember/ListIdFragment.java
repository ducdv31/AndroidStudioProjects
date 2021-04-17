package com.example.idmanager.fragmentaddmember;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.idmanager.AddMemActivity;
import com.example.idmanager.R;
import com.example.idmanager.model.addmember.IDCard;
import com.example.idmanager.rcvadapter.IDRCVAdapter.IDRcvAdapter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ListIdFragment extends Fragment {

    private IDRcvAdapter idRcvAdapter;
    private AddMemActivity addMemActivity;
    private FloatingActionButton fab_clear_all;

    public ListIdFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View idView = inflater.inflate(R.layout.fragment_list_id, container, false);
        addMemActivity = (AddMemActivity) getActivity();
        RecyclerView recyclerView = idView.findViewById(R.id.rcv_list_id_member);
        fab_clear_all = idView.findViewById(R.id.bt_clear_list_add_user);
        idRcvAdapter = new IDRcvAdapter(addMemActivity,
                new IDRcvAdapter.IClickListenerSetDataUser() {
                    @Override
                    public void onClickIdUser(String id) {
                        addMemActivity.gotoSetDetailFragment(id);
                    }
                });
        fab_clear_all.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(addMemActivity, "Clear all", Toast.LENGTH_SHORT).show();
            }
        });
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(addMemActivity,
                RecyclerView.VERTICAL,
                false);
        recyclerView.setAdapter(idRcvAdapter);
        recyclerView.setLayoutManager(linearLayoutManager);
        RecyclerView.ItemDecoration itemDecoration = new DividerItemDecoration(addMemActivity,
                DividerItemDecoration.VERTICAL);
        recyclerView.addItemDecoration(itemDecoration);

        getListIDMember();

        return idView;
    }

    private void getListIDMember() {
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
        databaseReference.child("Deviot").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.hasChildren()) {
                    List<IDCard> list = new ArrayList<>();
                    for (DataSnapshot dataSnapshot : snapshot.getChildren()) { // dataSnapshot = ID
                        if (!dataSnapshot.hasChild("name") ||
                                !dataSnapshot.hasChild("rank") ||
                                !dataSnapshot.hasChild("room")) {
                            list.add(new IDCard(dataSnapshot.getKey()));
                        }

                    }
                    idRcvAdapter.setData(list);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}