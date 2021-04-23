package com.example.idmanager.fragmentmain;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.idmanager.MainActivity;
import com.example.idmanager.R;
import com.example.idmanager.model.User;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class EditUserFragment extends Fragment {

    public static final String TAG_FRAG_EDIT = "Fragment edit user";

    private EditText name, rank, room;

    public EditUserFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View updateUserView = inflater
                .inflate(R.layout.fragment_edit_user, container, false);
        name = updateUserView.findViewById(R.id.name_update);
        rank = updateUserView.findViewById(R.id.rank_update);
        room = updateUserView.findViewById(R.id.room_update);
        Button update_bt = updateUserView.findViewById(R.id.update_detail_user);
        TextView id_user = updateUserView.findViewById(R.id.id_in_update_data_fragment);
        Button back = updateUserView.findViewById(R.id.back_in_edit_user);
        MainActivity mainActivity = (MainActivity) getActivity();

        Bundle bundle = getArguments();
        if (bundle != null) {
            User user = (User) bundle.get(MainActivity.TAG_UPDATE);
            if (user != null) {
                id_user.setText(user.getID());
                name.setText(user.getName());
                rank.setText(String.valueOf(user.getRank()));
                room.setText(user.getRoom());
            }
        }
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (getFragmentManager() != null) {
                    getFragmentManager().popBackStack();
                }
            }
        });
        update_bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (bundle != null) {
                    User user = (User) bundle.get(MainActivity.TAG_UPDATE);
                    String Rank = rank.getText().toString();
                    if (user != null && !Rank.isEmpty()) {
                        updateUser(user.getID(),
                                name.getText().toString(),
                                Integer.parseInt(rank.getText().toString()),
                                room.getText().toString());
                    }
                }

            }
        });

        return updateUserView;
    }

    private void updateUser(String id, String name, int rank, String room) {
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
        databaseReference.child(MainActivity.PARENT_CHILD).child(id).child("name").setValue(name);
        databaseReference.child(MainActivity.PARENT_CHILD).child(id).child("rank").setValue(rank);
        databaseReference.child(MainActivity.PARENT_CHILD).child(id).child("room").setValue(room);
    }
}