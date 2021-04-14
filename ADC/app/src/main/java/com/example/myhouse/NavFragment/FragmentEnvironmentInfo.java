package com.example.myhouse.NavFragment;

import android.content.Intent;
import android.net.Uri;
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

import com.example.myhouse.NavFragment.EnviInfoAdapter.EnviromentAdapter;
import com.example.myhouse.NavFragment.Model.EnviInfoModel;
import com.example.myhouse.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class FragmentEnvironmentInfo extends Fragment {

    public FragmentEnvironmentInfo() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View EnInfo = inflater.inflate(R.layout.fragment_environment_info, container, false);
        RecyclerView recyclerView = EnInfo.findViewById(R.id.rcv_environment_info);
        EnviromentAdapter enviromentAdapter = new EnviromentAdapter(getContext());
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(enviromentAdapter);

        DividerItemDecoration itemDecoration = new DividerItemDecoration(requireContext(), DividerItemDecoration.VERTICAL);
        recyclerView.addItemDecoration(itemDecoration);

        enviromentAdapter.setData(getListData());

        return EnInfo;
    }

    private List<EnviInfoModel> getListData() {
        List<EnviInfoModel> enviInfoModelList = new ArrayList<>();
        enviInfoModelList.add(new EnviInfoModel("Bụi PM là gì?",
                "https://www.vinmec.com/vi/tin-tuc/thong-tin-suc-khoe/tim-hieu-bui-min-pm-10-va-pm25-trong-khong-khi-o-nhiem/#:~:text=Ngo%C3%A0i%20b%E1%BB%87nh%20kh%E1%BA%A3%20n%C4%83ng%20g%C3%A2y,v%C3%A0%20gi%E1%BA%A3m%20tr%C3%AD%20nh%E1%BB%9B%20nghi%C3%AAm"));
        enviInfoModelList.add(new EnviInfoModel("Tác hại của bụi pm 1.0",
                "https://www.dienmayxanh.com/kinh-nghiem-hay/bui-min-pm-1-0-la-gi-co-nguy-hiem-khong-anh-huong-1238368"));
        enviInfoModelList.add(new EnviInfoModel("Tại sao bụi mịn PM1.0 và PM2.5 là sát thủ của sức khỏe con người?",
                "https://mutosi.com/bi-quyet/tai-sao-bui-min-pm10-va-pm25-la-sat-thu-cua-suc-khoe-con-nguoi/"));


        return enviInfoModelList;
    }

    public void openUrl(String link) {
        Intent myIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(link));
        startActivity(myIntent);
    }
}