package com.example.savefragmentstate;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.UserViewholder> {

    private List<User> userList;
    private IClickItemListener iClickItemListener;

    public interface IClickItemListener {
        void onClickItemUser(User user);
    }

    public UserAdapter(List<User> userList, IClickItemListener listener) {
        this.userList = userList;
        this.iClickItemListener = listener;
    }

    @NonNull
    @Override
    public UserViewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_user, parent, false);

        return new UserViewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UserViewholder holder, int position) {
        User user = userList.get(position);
        if (user == null) {
            return;
        }
        holder.textView.setText(user.getName());
        holder.textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                iClickItemListener.onClickItemUser(user);
            }
        });
    }

    @Override
    public int getItemCount() {
        if (userList != null)
            return userList.size();
        return 0;
    }

    public class UserViewholder extends RecyclerView.ViewHolder {
        private TextView textView;

        public UserViewholder(@NonNull View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.tv_name);
        }
    }
}
