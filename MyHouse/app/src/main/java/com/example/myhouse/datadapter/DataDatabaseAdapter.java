package com.example.myhouse.datadapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myhouse.R;

import java.util.List;

public class DataDatabaseAdapter extends RecyclerView.Adapter<DataDatabaseAdapter.UserViewHolder> {
    private final Context context;
    private List<DataDatabase> listUser;

    public DataDatabaseAdapter(Context context) {
        this.context = context;
    }

    public void setData(List<DataDatabase> listUser) {
        this.listUser = listUser;
        notifyDataSetChanged(); // very Importance

    }

    @NonNull
    @Override
    public UserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_database,
                        parent,
                        false);
        return new UserViewHolder(view);
    }

    @Override
    public int getItemCount() {
        if (listUser != null) {
            return listUser.size();
        }
        return 0;
    }

    @Override
    public void onBindViewHolder(@NonNull UserViewHolder holder, int position) {
        DataDatabase dataDatabase = listUser.get(position);
        if (dataDatabase == null) {
            return;
        }
        holder.Key.setText(dataDatabase.getKey());
    }

    public static class UserViewHolder extends RecyclerView.ViewHolder {

        private final TextView Key;

        public UserViewHolder(@NonNull View itemView) {
            super(itemView);
            Key = itemView.findViewById(R.id.key_database);
        }
    }
}
