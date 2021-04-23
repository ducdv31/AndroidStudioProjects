package com.example.idmanager;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.MenuItem;

import com.example.idmanager.fragmentaddmember.ListIdFragment;
import com.example.idmanager.fragmentaddmember.SetDataUserFragment;
import com.example.idmanager.fragmentmain.DetailFragment;
import com.example.idmanager.model.User;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AddMemActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_mem);
        ActionBar actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.setTitle("Add member");
        actionBar.setDisplayHomeAsUpEnabled(true);

        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.add_member_content_frame, new ListIdFragment());
        fragmentTransaction.commit();
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }
    public void gotoSetDetailFragment(String id) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        SetDataUserFragment setDataUserFragment = new SetDataUserFragment();
        Bundle bundle = new Bundle();
        bundle.putString("ID_user", id);
        setDataUserFragment.setArguments(bundle);
        /*  Transform  */
        fragmentTransaction.replace(R.id.add_member_content_frame, setDataUserFragment);
        fragmentTransaction.addToBackStack(SetDataUserFragment.SET_DATA_TAG);
        fragmentTransaction.commit();
    }

    public void deleteUnKnowUser(String id){
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
        databaseReference.child("Deviot").child(id).setValue(null);
    }
}