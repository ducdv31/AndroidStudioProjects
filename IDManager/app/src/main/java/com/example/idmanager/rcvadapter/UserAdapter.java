package com.example.idmanager.rcvadapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.idmanager.R;
import com.example.idmanager.model.User;

import java.util.ArrayList;
import java.util.List;


public class UserAdapter extends RecyclerView.Adapter<UserAdapter.UserViewHolder> {

    public interface IClickListener {
        void onClickUser(User user);
    }

    private IClickListener iClickListener;
    private List<User> userList = new ArrayList<>();


    public UserAdapter(Context context, IClickListener iClickListener) {
        this.iClickListener = iClickListener;
    }

    public void setData(List<User> list) {
        userList = list;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public UserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_user, parent, false);
        return new UserViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UserViewHolder holder, int position) {
        User user = userList.get(position);
        if (user == null) {
            return;
        }
        holder.name.setText(user.getName());
        holder.room.setText(user.getRoom());
        holder.id.setText(user.getID());
        holder.rank.setText(String.valueOf(user.getRank()));
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                iClickListener.onClickUser(user);
            }
        });

    }

    @Override
    public int getItemCount() {
        if (userList == null)
            return 0;
        return userList.size();
    }

    public class UserViewHolder extends RecyclerView.ViewHolder {

        private final TextView name, id, room, rank;
        private final CardView cardView;

        public UserViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.user_name);
            id = itemView.findViewById(R.id.id_user);
            room = itemView.findViewById(R.id.room_user);
            rank = itemView.findViewById(R.id.rank_user);
            cardView = itemView.findViewById(R.id.user_card);
        }
    }

}
