package com.example.mymqtt.rcvadapter;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mymqtt.MainActivity;
import com.example.mymqtt.R;
import com.example.mymqtt.mqttconfig.MyMqtt;

import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

public class RCVLedControlAdapter extends RecyclerView.Adapter<RCVLedControlAdapter.LedControlViewHolder> {

    List<LedControlModel> ledControlModels = new ArrayList<>();
    private final MainActivity mainActivity;
    private final Context context;

    public RCVLedControlAdapter(Context context, MainActivity mainActivity) {
        this.context = context;
        this.mainActivity = mainActivity;
    }

    public void setData(List<LedControlModel> list) {
        ledControlModels = list;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public LedControlViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_led_control, parent, false);
        return new LedControlViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull LedControlViewHolder holder, int position) {

        LedControlModel ledControlModel = ledControlModels.get(position);
        if (ledControlModel == null) {
            return;
        }
        holder.led_number.setText(String.valueOf(ledControlModel.getNumber()));
        holder.topic.setText(ledControlModel.getTopic());
        holder.on.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String led_id_str = holder.led_id.getText().toString();
                String topic_led = ledControlModel.getTopic();
                Log.e("TAG", "onClick: " + topic_led + " : " + led_id_str + " " + "1");
                mainActivity.publish(topic_led,
                        led_id_str + " : " + "1",
                        false);
                holder.led_status.setImageResource(R.drawable.icons8_led_on_diode_64);
            }
        });
        holder.off.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String led_id_str = holder.led_id.getText().toString();
                String topic_led = ledControlModel.getTopic();
                Log.e("TAG", "onClick: " + topic_led + " : " + led_id_str + " " + "0");
                mainActivity.publish(topic_led,
                        led_id_str + " : " + "0",
                        false);
                holder.led_status.setImageResource(R.drawable.icons8_led_diode_64);
            }
        });
    }

    @Override
    public int getItemCount() {
        if (ledControlModels == null)
            return 0;
        return ledControlModels.size();
    }

    public class LedControlViewHolder extends RecyclerView.ViewHolder {

        private final TextView led_number;
        private final ImageView led_status;
        private final Button on, off;
        private final EditText led_id;
        private final TextView topic;

        public LedControlViewHolder(@NonNull View itemView) {
            super(itemView);
            led_number = itemView.findViewById(R.id.led_number);
            led_status = itemView.findViewById(R.id.led_status);
            on = itemView.findViewById(R.id.on);
            off = itemView.findViewById(R.id.off);
            led_id = itemView.findViewById(R.id.id_led);
            topic = itemView.findViewById(R.id.topic_led);
        }
    }
}
