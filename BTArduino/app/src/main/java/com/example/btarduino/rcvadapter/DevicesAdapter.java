package com.example.btarduino.rcvadapter;

import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.btarduino.R;
import com.example.btarduino.initbluetooth.IBluetoothListener;

import java.util.ArrayList;

public class DevicesAdapter extends RecyclerView.Adapter<DevicesAdapter.DevicesViewHolder> {
    private IBluetoothListener iBluetoothListener;
    private ArrayList<BluetoothDevice> bluetoothDeviceList;

    private FragmentManager fragmentManager;

    public DevicesAdapter(Context context, FragmentManager fragmentManager) {
        this.fragmentManager = fragmentManager;
        iBluetoothListener = (IBluetoothListener) context;
    }

    public void setData(ArrayList<BluetoothDevice> list) {
        bluetoothDeviceList = list;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public DevicesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_devices, parent, false);
        return new DevicesViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DevicesViewHolder holder, int position) {
        BluetoothDevice bluetoothDevice = bluetoothDeviceList.get(position);
        if (bluetoothDevice == null) {
            return;
        }
        holder.Name.setText(bluetoothDevice.getName());
        holder.Mac.setText(bluetoothDevice.toString());
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                iBluetoothListener.SelectedDevice(bluetoothDevice);
                fragmentManager.popBackStack();
            }
        });
    }

    @Override
    public int getItemCount() {
        if (bluetoothDeviceList == null)
            return 0;
        return bluetoothDeviceList.size();
    }

    public class DevicesViewHolder extends RecyclerView.ViewHolder {

        private final TextView Name;
        private final TextView Mac;
        private final CardView cardView;

        public DevicesViewHolder(@NonNull View itemView) {
            super(itemView);
            Name = itemView.findViewById(R.id.name);
            Mac = itemView.findViewById(R.id.mac);
            cardView = itemView.findViewById(R.id.cardView);
        }
    }
}
