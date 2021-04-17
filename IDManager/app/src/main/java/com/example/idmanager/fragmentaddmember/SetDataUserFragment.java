package com.example.idmanager.fragmentaddmember;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.idmanager.AddMemActivity;
import com.example.idmanager.R;
import com.example.idmanager.model.User;
import com.example.idmanager.model.addmember.DetailAddMem;
import com.example.idmanager.model.addmember.IDCard;
import com.example.idmanager.rcvadapter.IDRCVAdapter.IDRcvAdapter;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class SetDataUserFragment extends Fragment {

    public final static String SET_DATA_TAG = "Fragment Set Data User";
    private AddMemActivity addMemActivity;
    private EditText name, rank, room;
    private Button Set;
    private DatabaseReference databaseReference;
    private TextView Id;

    public SetDataUserFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View setUserView = inflater.inflate(R.layout.fragment_set_data_user, container, false);
        addMemActivity = (AddMemActivity) getActivity();
        databaseReference = FirebaseDatabase.getInstance().getReference();
        name = setUserView.findViewById(R.id.name_set);
        rank = setUserView.findViewById(R.id.rank_set);
        room = setUserView.findViewById(R.id.room_set);
        Set = setUserView.findViewById(R.id.set_detail_user);
        Id = setUserView.findViewById(R.id.id_in_set_data_fragment);


        Bundle bundle = getArguments();
        if (bundle != null) {
            String id = (String) bundle.get("ID_user");
            Id.setText(id);
        }

        Set.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String Name = name.getText().toString();
                String Rank = rank.getText().toString();
                String Room = room.getText().toString();
                /* Set data to firebase */
                if (bundle != null && !Rank.isEmpty()) {
                    String id = (String) bundle.get("ID_user");
                    DetailAddMem detailAddMem = new DetailAddMem(Name,
                            Room,
                            Integer.parseInt(Rank));
                    databaseReference.child("Deviot").child(id).setValue(detailAddMem);
                }

            }
        });


        return setUserView;
    }
}