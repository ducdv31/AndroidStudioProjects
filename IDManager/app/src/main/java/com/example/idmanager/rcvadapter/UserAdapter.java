package com.example.idmanager.rcvadapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.chauthai.swipereveallayout.SwipeRevealLayout;
import com.chauthai.swipereveallayout.ViewBinderHelper;
import com.example.idmanager.R;
import com.example.idmanager.model.User;

import java.util.ArrayList;
import java.util.List;


public class UserAdapter extends RecyclerView.Adapter<UserAdapter.UserViewHolder> {

    private final ViewBinderHelper viewBinderHelper = new ViewBinderHelper();
    private final Context context;

    public interface IClickListener {
        void onClickUser(User user);

        void onClickUpdateUser(User user);

        void onClickDeleteUser(String id);
    }

    private final IClickListener iClickListener;
    private List<User> userList = new ArrayList<>();


    public UserAdapter(Context context, IClickListener iClickListener) {
        this.iClickListener = iClickListener;
        this.context = context;
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
        viewBinderHelper.bind(holder.swipeRevealLayout, user.getID());
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
        holder.delete_user.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                iClickListener.onClickDeleteUser(user.getID());
            }
        });
        holder.edit_user.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                iClickListener.onClickUpdateUser(user);
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
        private final ImageView delete_user;
        private final ImageView edit_user;
        private final SwipeRevealLayout swipeRevealLayout;

        public UserViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.user_name);
            id = itemView.findViewById(R.id.id_user);
            room = itemView.findViewById(R.id.room_user);
            rank = itemView.findViewById(R.id.rank_user);
            cardView = itemView.findViewById(R.id.user_card);
            delete_user = itemView.findViewById(R.id.delete__user);
            swipeRevealLayout = itemView.findViewById(R.id.swipeReveal_list_user);
            edit_user = itemView.findViewById(R.id.edit__user);
        }
    }

}
