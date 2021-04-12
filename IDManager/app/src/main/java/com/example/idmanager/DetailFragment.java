package com.example.idmanager;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.idmanager.model.User;
import com.example.idmanager.model.WorkFlow;
import com.example.idmanager.rcvadapter.WorkTimeAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class DetailFragment extends Fragment {

    public static final String TAG = "Fragment Detail";
    private TextView name;
    private TextView rank;
    private TextView room;
    private WorkTimeAdapter workTimeAdapter;
    private MainActivity mainActivity;

    public DetailFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View detailView = inflater.inflate(R.layout.fragment_detail, container, false);
        Button back = detailView.findViewById(R.id.back_to_list_user);
        RecyclerView recyclerView = detailView.findViewById(R.id.rcv_user_work_time);
        name = detailView.findViewById(R.id.name_user_detail);
        rank = detailView.findViewById(R.id.rank_user_detail);
        room = detailView.findViewById(R.id.room_user_detail);
        mainActivity = (MainActivity) getActivity();
        workTimeAdapter = new WorkTimeAdapter(requireContext());
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(requireContext(),
                RecyclerView.VERTICAL, false);

        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(workTimeAdapter);


        getDataFromUserFrag();

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (getFragmentManager() != null) {
                    getFragmentManager().popBackStack();
                }
            }
        });

        return detailView;
    }

    private void getDataFromUserFrag() {
        Bundle bundle = getArguments();
        if (bundle != null) {
            User user = (User) bundle.get("object_user");
            if (user != null) {
                getTimeWorker(user.getID());
                name.setText(user.getName());
                rank.setText(String.valueOf(user.getRank()));
                room.setText(user.getRoom());
            }
        }
    }

    private void getTimeWorker(String id) {
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
        databaseReference.child("Deviot").child(id).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                if (snapshot.hasChild("Work-Time")) {
                    databaseReference.child("Deviot").child(id).child("Work-Time").addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            if (snapshot.hasChildren()) {
                                List<WorkFlow> workFlows = new ArrayList<>();
                                for (DataSnapshot dataSnapshot : snapshot.getChildren()) { //dataSnapshot = day
                                    if (dataSnapshot.hasChildren()) {
                                        List<Integer> time_line = new ArrayList<>();
                                        for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) { // dataSnapshot1 = minutes
                                            time_line.add(Integer.valueOf(Objects.requireNonNull(dataSnapshot1.getKey())));
                                        }
                                        workFlows.add(new WorkFlow(dataSnapshot.getKey(),
                                                time_line.get(0).toString(),
                                                time_line.get(time_line.size() - 1).toString()));
                                    }
                                }
                                workTimeAdapter.setData(workFlows);
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                } else {
                    Toast.makeText(mainActivity, "No Data", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}