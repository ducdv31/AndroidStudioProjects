package com.example.mpu6050c;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Icon;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.DecimalFormat;
import java.util.Random;

import static android.widget.Toast.LENGTH_LONG;
import static com.example.mpu6050c.R.mipmap.ic_launcher;
import static com.example.mpu6050c.R.mipmap.iot_logo;

public class MainActivity extends AppCompatActivity {
    private TextView acx, acy, acz, gyx, gyy, gyz, temperature, humidity, NameAcc, Status;
    private Button webDatabt, setDatabasebt;
    private GoogleSignInClient mGoogleSignInClient;
    private ImageView ImgAcc;
    
    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ActionBar actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setTitle("IoT Central");
        actionBar.setLogo(R.mipmap.iot_solution_central);
        actionBar.setDisplayUseLogoEnabled(true);
        //        actionBar.hide();
        webDatabt = (Button) findViewById(R.id.webData);
        acx = (TextView) findViewById(R.id.acx);
        acy = (TextView) findViewById(R.id.acy);
        acz = (TextView) findViewById(R.id.acz);
        gyx = (TextView) findViewById(R.id.gyx);
        gyy = (TextView) findViewById(R.id.gyy);
        gyz = (TextView) findViewById(R.id.gyz);
        temperature = (TextView) findViewById(R.id.temp);
        humidity = (TextView) findViewById(R.id.humi);
        setDatabasebt = (Button) findViewById(R.id.setDatabt);
        NameAcc = findViewById(R.id.NameAcc);
        ImgAcc = findViewById(R.id.ImgAcc);
        Status = findViewById(R.id.Status);

        //
        createRequest();
        updateUI();
        checkNet();
        setDatabasebt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent openSetDatabase = new Intent(MainActivity.this, SetDatabaseActivities.class);
                startActivity(openSetDatabase);
            }
        });
        webDatabt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent openWeb = new Intent(Intent.ACTION_VIEW, Uri.parse("https://duc-bkhn-k62.web.app/"));
                startActivity(openWeb);
            }
        });
        getDatabase AcX = new getDatabase(acx, "MPU6050/AcX", " º");
        getDatabase AcY = new getDatabase(acy, "MPU6050/AcY", " º");
        getDatabase AcZ = new getDatabase(acz, "MPU6050/AcZ", " º");
        getDatabase GyX = new getDatabase(gyx, "MPU6050/GyX", " º/s");
        getDatabase GyY = new getDatabase(gyy, "MPU6050/GyY", " º/s");
        getDatabase GyZ = new getDatabase(gyz, "MPU6050/GyZ", " º/s");
        getDatabase Temp = new getDatabase(temperature, "DHT11/t", " ºC");
        getDatabase Hum = new getDatabase(humidity, "DHT11/h", " %");
        AcX.getData();
        AcY.getData();
        AcZ.getData();
        GyX.getData();
        GyY.getData();
        GyZ.getData();
        Temp.getDataDec1();
        Hum.getData();


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.mymenu, menu);
        return true;
        // Tao menu
    }

    public void About() {
        Intent openAbout = new Intent(this, AboutActivities.class);
        startActivity(openAbout);
    }

    public void Setting() {
        Intent openSetting = new Intent(this, Setting.class);
        startActivity(openSetting);
    }

    public void ESP_Menu() {
        Intent openESP_Infor = new Intent(this, ESP_Information.class);
        startActivity(openESP_Infor);
    }

    public void List_Database() {
        Intent openList_DB = new Intent(this, List_Database.class);
        startActivity(openList_DB);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                break;
            case R.id.Setting:
                Setting();
                break;
            case R.id.ESP_Infor_Menu:
                ESP_Menu();
                break;
            case R.id.About:
                About();
                break;
            case R.id.Database_Data:
                List_Database();
                break;
            case R.id.SignOut:
                signOut();
                break;
        }
        return super.onOptionsItemSelected(item);
        // Danh sach menu
    }

    private void signOut() {
        FirebaseAuth.getInstance().signOut();
        revokeAccess();
    }

    private void revokeAccess() {
        mGoogleSignInClient.revokeAccess()
                .addOnCompleteListener(this, new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        // ...
                        NameAcc.setText("Please Sign In");
                        Glide.with(ImgAcc).clear(ImgAcc);
                        Toast.makeText(MainActivity.this, "Signed out", Toast.LENGTH_SHORT).show();
                        openSignIn();
                    }
                });
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        switch (keyCode) {
            case KeyEvent.KEYCODE_BACK:
                Intent intent = new Intent(Intent.ACTION_MAIN);
                intent.addCategory(Intent.CATEGORY_HOME);
                startActivity(intent);
                finish();
                break;
            default:
                break;
        }
        return super.onKeyDown(keyCode, event);
    }

    private void createRequest() {
        // Configure Google Sign In
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        // Build a GoogleSignInClient with the options specified by gso.
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
    }

    private void openSignIn() {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }

    private void updateUI() {
        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(this);
        if (account != null) {
            String personName = account.getDisplayName();
            String personEmail = account.getEmail();
            String personId = account.getId();
            Uri personPhoto = account.getPhotoUrl();
            NameAcc.setText(personName);
            Glide.with(this).load(personPhoto).into(ImgAcc);
        }
    }
    private void checkNet() {
        DatabaseReference connectedRef = FirebaseDatabase.getInstance().getReference(".info/connected");
        connectedRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                boolean connected = snapshot.getValue(Boolean.class);
                if (connected) {
                    Status.setText("Connected");
                    Status.setTextColor(Color.BLUE);
                } else {
                    Status.setText("Disconnected");
                    Status.setTextColor(Color.RED);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Status.setText("Cancelled");
                Status.setTextColor(Color.RED);
            }
        });
    }
}
