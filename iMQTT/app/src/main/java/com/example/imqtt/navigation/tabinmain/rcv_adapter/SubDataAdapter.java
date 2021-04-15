package com.example.imqtt.navigation.tabinmain.rcv_adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.imqtt.R;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class SubDataAdapter extends RecyclerView.Adapter<SubDataAdapter.SubDataViewHolder> {

    List<DataModel> dataModelList;

    public SubDataAdapter(Context context) {
    }

    public void setData(List<DataModel> list) {
        dataModelList = list;
        Collections.reverse(dataModelList);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public SubDataViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_sub_data_received, parent, false);
        return new SubDataViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SubDataViewHolder holder, int position) {
        DataModel dataModel = dataModelList.get(position);
        if (dataModel == null)
            return;
        holder.time.setText(dataModel.getTime());
        holder.topic.setText(dataModel.getTopic());
        holder.content.setText(dataModel.getContent());
    }

    @Override
    public int getItemCount() {
        if (dataModelList == null)
            return 0;
        return dataModelList.size();
    }

    public class SubDataViewHolder extends RecyclerView.ViewHolder {

        private final TextView time, topic, content;

        public SubDataViewHolder(@NonNull View itemView) {
            super(itemView);
            time = itemView.findViewById(R.id.time_received);
            topic = itemView.findViewById(R.id.topic_received);
            content = itemView.findViewById(R.id.content_received);
        }
    }
}
