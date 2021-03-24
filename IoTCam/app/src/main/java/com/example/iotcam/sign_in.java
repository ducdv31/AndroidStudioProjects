package com.example.iotcam;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

import java.util.jar.Attributes;

public class sign_in extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private static final int RC_SIGN_IN = 123;
    private Button Next;
    private SignInButton signInButton;
    private GoogleSignInClient mGoogleSignInClient;
    private TextView Email;
    private TextView Status;
    private Button logOut;
    private final int PERMISSION_CAM = 10;
    private ImageView IntroImg;
    private String personName;
    private String personEmail;
    private String personId;
    private Uri personPhoto;
    private long backPressTime;
    private Toast mToast;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        // ...
        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();
        signInButton = (SignInButton) findViewById(R.id.sign_in_button);
        Next = findViewById(R.id.OpenCam);
        Status = findViewById(R.id.Status);
        logOut = findViewById(R.id.LogOut);
        Email = findViewById(R.id.email);
        IntroImg = findViewById(R.id.IntroImg);
        logOut.setBackgroundColor(Color.RED);
        logOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signOut();
            }
        });
        Next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseUser currentUser = mAuth.getCurrentUser();
                if (currentUser != null) {
                    openCam();
                } else {
                    Toast.makeText(sign_in.this,
                            "Please sign in",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });
        createRequest();
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Sign in to IoT Camera");
        signInButton.setSize(SignInButton.SIZE_STANDARD);
        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.sign_in_button:
                        signIn();
                        break;
                }

            }
        });

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


    private void signIn() {

        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                // Google Sign In was successful, authenticate with Firebase
                GoogleSignInAccount account = task.getResult(ApiException.class);
                firebaseAuthWithGoogle(account.getIdToken());
                Log.e("Log in", "Success");
            } catch (ApiException e) {
                // Google Sign In failed, update UI appropriately
                updateLogOut();
                Log.e("Not ", "Log in");
            }
        }
    }

    private void firebaseAuthWithGoogle(String idToken) {
        AuthCredential credential = GoogleAuthProvider.getCredential(idToken, null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            FirebaseUser user = mAuth.getCurrentUser();
                            // Open camera
//                            Status.setText("Hi " + personName);
//                            Status.setTextColor(Color.BLUE);
                            updateUI();
                            Next.setBackgroundColor(Color.BLUE);
                            openCam();
                        } else {
                            // If sign in fails, display a message to the user.
                            Toast.makeText(sign_in.this,
                                    "Error firebase Auth with google",
                                    Toast.LENGTH_SHORT).show();
                        }
                        // ...
                    }
                });
    }

    private void openCam() {
        Intent openCam = new Intent(sign_in.this,
                MainActivity.class);
        startActivity(openCam);
    }

    @Override
    public void onStart() {
        super.onStart();
        while (ContextCompat.checkSelfPermission(sign_in.this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(sign_in.this, new String[]{Manifest.permission.CAMERA}, PERMISSION_CAM);
        }
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser == null) {
            updateLogOut();
        } else {
            updateUI();
        }
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
                        updateLogOut();
                        Toast.makeText(sign_in.this,
                                "Signed out",
                                Toast.LENGTH_SHORT).show();
                    }
                });
    }

//    @Override
//    public boolean onKeyDown(int keyCode, KeyEvent event) {
//        switch (keyCode) {
//            case KeyEvent.KEYCODE_BACK:
//                Intent startMain = new Intent(Intent.ACTION_MAIN);
//                startMain.addCategory(Intent.CATEGORY_HOME);
//                startActivity(startMain);
//                finish();
//                break;
//            default:
//                break;
//        }
//        return super.onKeyDown(keyCode, event);
//    }


    @Override
    public void onBackPressed() {

        if (backPressTime + 2000 > System.currentTimeMillis()) {
            mToast.cancel();
            Intent startMain = new Intent(Intent.ACTION_MAIN);
            startMain.addCategory(Intent.CATEGORY_HOME);
            startActivity(startMain);
            finish();
            return;
        } else {
            mToast = Toast.makeText(this, "Press Back again to exit", Toast.LENGTH_SHORT);
            mToast.show();
        }
        backPressTime = System.currentTimeMillis();
    }

    private void updateUI() {
        GoogleSignInAccount acct = GoogleSignIn.getLastSignedInAccount(this);
        if (acct != null) {
            personName = acct.getDisplayName();
            personEmail = acct.getEmail();
            personId = acct.getId();
            personPhoto = acct.getPhotoUrl();
            Status.setText("Hi " + personName);
            Email.setText(personEmail);
//            Glide.with(this).load(String.valueOf(personPhoto)).into(IntroImg);
//            Toast.makeText(this, personName, Toast.LENGTH_SHORT).show();
        }
    }

    private void updateLogOut() {
        Email.setText("");
        Status.setText("Welcome to IoT Cam");
        Next.setBackgroundColor(Color.RED);
    }
}