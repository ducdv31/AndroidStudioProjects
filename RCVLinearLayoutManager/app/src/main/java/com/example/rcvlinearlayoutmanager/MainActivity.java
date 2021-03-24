package com.example.rcvlinearlayoutmanager;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private UserAdapter userAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView = findViewById(R.id.rcv_user);
        userAdapter = new UserAdapter(this);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this,
                RecyclerView.VERTICAL,
                false);
        recyclerView.setLayoutManager(linearLayoutManager);

        userAdapter.setData(getListUser());
        recyclerView.setAdapter(userAdapter);
    }

    private List<User> getListUser() {
        List<User> list = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            list.add(new User(String.valueOf(i + 850), String.valueOf(i + 40)));
        }

        return list;
    }
}