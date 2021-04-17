package com.example.idmanager;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.idmanager.fragmentmain.DetailFragment;
import com.example.idmanager.fragmentmain.ListUsersFragment;
import com.example.idmanager.model.User;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.content_frame, new ListUsersFragment());
        fragmentTransaction.commit();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.add_member:
                Intent intent = new Intent(this, AddMemActivity.class);
                startActivity(intent);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    public void gotoDetailFragment(User user) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        DetailFragment detailFragment = new DetailFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable("object_user", user);
        detailFragment.setArguments(bundle);
        /*  Transform  */
        fragmentTransaction.replace(R.id.content_frame, detailFragment);
        fragmentTransaction.addToBackStack(DetailFragment.TAG);
        fragmentTransaction.commit();
    }

}