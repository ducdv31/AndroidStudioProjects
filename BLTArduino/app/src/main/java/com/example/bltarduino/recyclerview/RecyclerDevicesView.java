package com.example.bltarduino.recyclerview;

import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bltarduino.R;

import java.util.List;
import java.util.Set;

public class RecyclerDevicesView extends RecyclerView.Adapter<RecyclerDevicesView.DeviceViewHolder> {
    private List<BluetoothDevice> devices;
    private Context context;

    public RecyclerDevicesView(Context context) {
        this.context = context;
    }

    public void setData(List<BluetoothDevice> devices) {
        this.devices = devices;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public DeviceViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.device_layout, parent, false);
        return new DeviceViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DeviceViewHolder holder, int position) {
        BluetoothDevice bluetoothDevice = devices.get(position);
        if (bluetoothDevice == null){
            return;
        }
        holder.Name.setText(bluetoothDevice.getName());
        holder.Mac.setText(bluetoothDevice.toString());
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, bluetoothDevice.getName() + "\n" + bluetoothDevice, Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        if (this.devices == null) {
            return 0;
        }
        return this.devices.size();
    }

    public class DeviceViewHolder extends RecyclerView.ViewHolder {
        private final TextView Name;
        private final TextView Mac;
        private final CardView cardView;

        public DeviceViewHolder(@NonNull View itemView) {
            super(itemView);
            Name = itemView.findViewById(R.id.name_device);
            Mac = itemView.findViewById(R.id.mac_device);
            cardView = itemView.findViewById(R.id.cardView);
        }
    }
}
