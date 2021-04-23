package com.example.idmanager.rcvadapter.IDRCVAdapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.chauthai.swipereveallayout.SwipeRevealLayout;
import com.chauthai.swipereveallayout.ViewBinderHelper;
import com.example.idmanager.R;
import com.example.idmanager.model.addmember.IDCard;

import java.util.List;

public class IDRcvAdapter extends RecyclerView.Adapter<IDRcvAdapter.IDViewHolder> {

    private final ViewBinderHelper viewBinderHelper = new ViewBinderHelper();
    public interface IClickListenerSetDataUser{
        void onClickIdUser(String id);
        void onClickDeleteUnKowUser(String id);
    }
    private final IClickListenerSetDataUser iClickListenerSetDataUser;
    List<IDCard> idCardList;

    public IDRcvAdapter(Context context, IClickListenerSetDataUser listenerSetDataUser) {
        iClickListenerSetDataUser = listenerSetDataUser;
    }

    public void setData(List<IDCard> list) {
        idCardList = list;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public IDViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_id_activity_add,
                parent, false);
        return new IDViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull IDViewHolder holder, int position) {
        IDCard idCard = idCardList.get(position);
        if (idCard == null)
            return;
        holder.id_card.setText(idCard.getId());
        viewBinderHelper.bind(holder.swipeRevealLayout, idCard.getId());
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /* Open set Data Member */
                iClickListenerSetDataUser.onClickIdUser(idCard.getId());
            }
        });
        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                iClickListenerSetDataUser.onClickDeleteUnKowUser(idCard.getId());
            }
        });
    }

    @Override
    public int getItemCount() {
        if (idCardList == null)
            return 0;
        return idCardList.size();
    }

    public class IDViewHolder extends RecyclerView.ViewHolder {

        private final CardView cardView;
        private final TextView id_card;
        private final SwipeRevealLayout swipeRevealLayout;
        private final ImageView delete;

        public IDViewHolder(@NonNull View itemView) {
            super(itemView);
            cardView = itemView.findViewById(R.id.card_id_add_member);
            id_card = itemView.findViewById(R.id.id_card_add_member);
            swipeRevealLayout = itemView.findViewById(R.id.swipeReveal);
            delete = itemView.findViewById(R.id.delete_unKnown_user);
        }
    }
}
