package com.example.recyclerviewgridcard;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        RecyclerView recyclerView = findViewById(R.id.recyclerview);
        UserAdapter userAdapter = new UserAdapter(this);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 1);
        recyclerView.setLayoutManager(gridLayoutManager);
        userAdapter.setData(getListData());
        recyclerView.setAdapter(userAdapter);
    }

    private List<User> getListData() {
        List<User> dataList = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            dataList.add(new User("Dang Duc ", 1999 + i));
        }
        return dataList;
    }
}