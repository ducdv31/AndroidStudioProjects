package com.example.myhouse.NavFragment.EnviInfoAdapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myhouse.MainActivity;
import com.example.myhouse.NavFragment.FragmentEnvironmentInfo;
import com.example.myhouse.NavFragment.Model.EnviInfoModel;
import com.example.myhouse.R;

import java.util.List;

public class EnviromentAdapter extends RecyclerView.Adapter<EnviromentAdapter.EnvironmentViewHolder> {
    List<EnviInfoModel> list;
    private Context context;
    public EnviromentAdapter(Context context) {
        this.context = context;
    }

    public void setData(List<EnviInfoModel> modelList) {
        list = modelList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public EnvironmentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_enviroment_info, parent, false);

        return new EnvironmentViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull EnvironmentViewHolder holder, int position) {
        EnviInfoModel enviInfoModel = list.get(position);
        if (enviInfoModel == null)
            return;
        holder.Title.setText(enviInfoModel.getTitle());
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent myIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(enviInfoModel.getUrl()));
                context.startActivity(myIntent);
            }
        });
    }

    @Override
    public int getItemCount() {
        if (list == null)
            return 0;
        return list.size();
    }

    public class EnvironmentViewHolder extends RecyclerView.ViewHolder {

        private final TextView Title;
        private final CardView cardView;

        public EnvironmentViewHolder(@NonNull View itemView) {
            super(itemView);
            Title = itemView.findViewById(R.id.title_environment_info);
            cardView = itemView.findViewById(R.id.cardView_environment_info);
        }
    }
}
