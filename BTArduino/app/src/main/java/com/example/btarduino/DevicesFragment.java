package com.example.btarduino;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.btarduino.rcvadapter.DevicesAdapter;

public class DevicesFragment extends Fragment {

    public static final String PBS_DEVICES = "Device";
    private Button back;
    private RecyclerView recyclerView;
    private MainActivity mainActivity;

    public DevicesFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View DevicesView = inflater.inflate(R.layout.fragment_devices, container, false);
        back = DevicesView.findViewById(R.id.back_devices);
        recyclerView = DevicesView.findViewById(R.id.rcv_devices);
        mainActivity = (MainActivity) getActivity();
        DevicesAdapter devicesAdapter = new DevicesAdapter(getContext(), getFragmentManager());
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(requireActivity(),
                RecyclerView.VERTICAL,
                false);

        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(devicesAdapter);
        RecyclerView.ItemDecoration itemDecoration = new DividerItemDecoration(mainActivity, DividerItemDecoration.VERTICAL);
        recyclerView.addItemDecoration(itemDecoration);

        devicesAdapter.setData(mainActivity.getListDevice());

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (getFragmentManager() != null) {
                    getFragmentManager().popBackStack();
                }
            }
        });

        return DevicesView;
    }

    @Override
    public void onPause() {
        super.onPause();
//        mainActivity.removeDevicesFragment();
    }
}