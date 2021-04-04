package com.example.myhouse.datahistory.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myhouse.R;
import com.example.myhouse.datahistory.Data5V;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Pms7003HisAdapter extends RecyclerView.Adapter<Pms7003HisAdapter.Pms7003ViewHolder> {

    List<Data5V> data5VS = new ArrayList<>();

    public Pms7003HisAdapter(Context context) {
    }

    public void setData(List<Data5V> list) {
        data5VS = list;
        Collections.reverse(list);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public Pms7003ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_pms, parent, false);
        return new Pms7003ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Pms7003ViewHolder holder, int position) {
        Data5V data5V = data5VS.get(position);
        if (data5V == null)
            return;
        holder.date.setText(data5V.getDate());
        holder.time.setText(data5V.getTime());
        holder.pm1.setText(data5V.getPm1());
        holder.pm25.setText(data5V.getPm25());
        holder.pm10.setText(data5V.getPm10());
    }

    @Override
    public int getItemCount() {
        if (data5VS != null)
            return data5VS.size();
        return 0;
    }

    public class Pms7003ViewHolder extends RecyclerView.ViewHolder {

        private final TextView date;
        private final TextView time;
        private final TextView pm1;
        private final TextView pm25;
        private final TextView pm10;

        public Pms7003ViewHolder(@NonNull View itemView) {
            super(itemView);
            date = itemView.findViewById(R.id.date_history_pms7003);
            time = itemView.findViewById(R.id.time_his_pms7003);
            pm1 = itemView.findViewById(R.id.pm1);
            pm25 = itemView.findViewById(R.id.pm25);
            pm10 = itemView.findViewById(R.id.pm10);
        }
    }
}
