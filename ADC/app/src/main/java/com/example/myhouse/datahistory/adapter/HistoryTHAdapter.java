package com.example.myhouse.datahistory.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myhouse.R;
import com.example.myhouse.datahistory.DataKKVV;

import java.util.Collections;
import java.util.List;

public class HistoryTHAdapter extends RecyclerView.Adapter<HistoryTHAdapter.HViewHolder> {
    private List<DataKKVV> dataKVList;

    public HistoryTHAdapter(Context context) {
    }

    public void setData(List<DataKKVV> listKVV) {
        dataKVList = listKVV;
        Collections.reverse(dataKVList);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public HViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_history, parent, false);
        return new HViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HViewHolder holder, int position) {
        DataKKVV dataKKVV = dataKVList.get(position);
        if (dataKKVV == null) {
            return;
        }
        holder.KEY.setText(dataKKVV.getDate());
        holder.KEY2.setText(dataKKVV.getKeyTime());
        holder.VALUE1.setText(dataKKVV.getValue1());
        holder.VALUE2.setText(dataKKVV.getValue2());
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("Card view", "Selected");
                Log.e("Position", "" + position);
            }
        });
    }

    @Override
    public int getItemCount() {
        if (dataKVList != null) {
            return dataKVList.size();
        }
        return 0;
    }


    public static class HViewHolder extends RecyclerView.ViewHolder {
        private final TextView KEY;
        private final TextView KEY2;
        private final TextView VALUE1;
        private final TextView VALUE2;
        private final CardView cardView;

        public HViewHolder(@NonNull View itemView) {
            super(itemView);
            KEY = itemView.findViewById(R.id.date_history);
            KEY2 = itemView.findViewById(R.id.Key);
            VALUE1 = itemView.findViewById(R.id.Value1);
            VALUE2 = itemView.findViewById(R.id.Value2);
            cardView = itemView.findViewById(R.id.cardView_history);
        }
    }
}
