package com.example.savefragmentstate;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {

    private RecyclerView rcvUser;
    private MainActivity mainActivity;

    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_home, container, false);

        mainActivity = (MainActivity) getActivity();
        rcvUser = view.findViewById(R.id.rcv_user);
        LinearLayoutManager linearLayoutManager =
                new LinearLayoutManager(requireActivity());
        rcvUser.setLayoutManager(linearLayoutManager);

        UserAdapter userAdapter = new UserAdapter(getListUser(),
                new UserAdapter.IClickItemListener() {
            @Override
            public void onClickItemUser(User user) {
                mainActivity.gotoDetailFragment(user);
            }
        });
        rcvUser.setAdapter(userAdapter);
        RecyclerView.ItemDecoration itemDecoration = new DividerItemDecoration(getContext(),
                DividerItemDecoration.VERTICAL);
        rcvUser.addItemDecoration(itemDecoration);

        return view;
    }

    private List<User> getListUser() {
        List<User> list = new ArrayList<>();
        for (int i = 0; i <= 20; i++) {
            list.add(new User("User " + i));
        }
        return list;
    }
}