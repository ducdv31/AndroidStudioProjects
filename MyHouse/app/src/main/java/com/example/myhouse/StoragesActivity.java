package com.example.myhouse;

import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

public class StoragesActivity extends AppCompatActivity {

    private final String Img = "logo_iot_home.png";
    private final String Bucket = "gs://duc-bkhn-k62.appspot.com";
    private final String Img2 = "icons8_time_256.png";

    private ImageView imageView, imageView2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_storages);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Storage");
        actionBar.setDisplayHomeAsUpEnabled(true);
        imageView = findViewById(R.id.img_firebase);

        // Get a non-default Storage bucket
        FirebaseStorage storage = FirebaseStorage.getInstance("gs://duc-bkhn-k62.appspot.com");
        // Create a storage reference from our app
        StorageReference storageRef = storage.getReference();

        storageRef.child("Images").child(Img).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Log.e("OK", uri.getPath());
                // Got the download URL for 'users/me/profile.png'
                Picasso.with(getApplicationContext()).load(uri).into(imageView);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                // Handle any errors
                Log.e("Error", "LOL");
            }
        });

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}