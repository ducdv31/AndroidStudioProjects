package com.example.idmanager.rcvadapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.idmanager.R;
import com.example.idmanager.model.WorkFlow;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class WorkTimeAdapter extends RecyclerView.Adapter<WorkTimeAdapter.WorkFlowViewHolder> {
    List<WorkFlow> workFlowList = new ArrayList<>();

    public WorkTimeAdapter(Context context) {
    }

    public void setData(List<WorkFlow> list) {
        workFlowList = list;
        Collections.reverse(workFlowList);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public WorkFlowViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_work_time, parent, false);
        return new WorkFlowViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull WorkFlowViewHolder holder, int position) {
        WorkFlow workFlow = workFlowList.get(position);
        if (workFlow == null) {
            return;
        }
        holder.day.setText(workFlow.getDay());
        holder.start.setText(workFlow.getStart());
        holder.end.setText(workFlow.getEnd());
    }

    @Override
    public int getItemCount() {
        if (workFlowList == null)
            return 0;
        else
            return workFlowList.size();
    }

    public class WorkFlowViewHolder extends RecyclerView.ViewHolder {

        private TextView day, start, end;

        public WorkFlowViewHolder(@NonNull View itemView) {
            super(itemView);
            day = itemView.findViewById(R.id.date_work);
            start = itemView.findViewById(R.id.start_work);
            end = itemView.findViewById(R.id.end_work);
        }
    }

}
