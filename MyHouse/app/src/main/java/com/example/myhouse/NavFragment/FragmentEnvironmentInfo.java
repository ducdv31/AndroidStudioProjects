package com.example.myhouse.NavFragment;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.myhouse.NavFragment.EnviInfoAdapter.EnviromentAdapter;
import com.example.myhouse.NavFragment.Model.EnviInfoModel;
import com.example.myhouse.R;

import java.util.ArrayList;
import java.util.List;

public class FragmentEnvironmentInfo extends Fragment {

    private RecyclerView recyclerView;

    public FragmentEnvironmentInfo() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View EnInfo = inflater.inflate(R.layout.fragment_environment_info, container, false);
        recyclerView = EnInfo.findViewById(R.id.rcv_environment_info);
        EnviromentAdapter enviromentAdapter = new EnviromentAdapter(getContext());
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(enviromentAdapter);

        enviromentAdapter.setData(getListData());

        return EnInfo;
    }

    private List<EnviInfoModel> getListData() {
        List<EnviInfoModel> enviInfoModelList = new ArrayList<>();
        enviInfoModelList.add(new EnviInfoModel("OK", "https://duc-bkhn-k62.web.app/"));
        return enviInfoModelList;
    }
    public void openUrl(String link) {
        Intent myIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(link));
        startActivity(myIntent);
    }
}