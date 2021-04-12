package com.example.idmanager;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.idmanager.model.User;
import com.example.idmanager.model.UserNID;
import com.example.idmanager.rcvadapter.UserAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ListUsersFragment extends Fragment {

    private UserAdapter userAdapter;

    public ListUsersFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View usersView = inflater.inflate(R.layout.fragment_list_users, container, false);
        MainActivity mainActivity = (MainActivity) getActivity();
        RecyclerView recyclerView = usersView.findViewById(R.id.rcv_list_user);
        userAdapter = new UserAdapter(requireContext(), new UserAdapter.IClickListener() {
            @Override
            public void onClickUser(User user) {
                assert mainActivity != null;
                mainActivity.gotoDetailFragment(user);
            }
        });

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(requireContext(),
                RecyclerView.VERTICAL,
                false);

        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(userAdapter);

        RecyclerView.ItemDecoration itemDecoration = new DividerItemDecoration(requireContext(),
                DividerItemDecoration.VERTICAL);

        recyclerView.addItemDecoration(itemDecoration);

        getListUser();

        return usersView;
    }

    private void getListUser() {
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
        databaseReference.child("Deviot").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                List<User> userList = new ArrayList<>();
                if (snapshot.hasChildren()) {
                    for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                        if (dataSnapshot.hasChildren()) {
                            UserNID userNID = dataSnapshot.getValue(UserNID.class);
                            assert userNID != null;
                            userList.add(new User(dataSnapshot.getKey(),
                                    userNID.getName(),
                                    userNID.getRank(),
                                    userNID.getRoom()));
                        }
                    }

                }
                userAdapter.setData(userList);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}