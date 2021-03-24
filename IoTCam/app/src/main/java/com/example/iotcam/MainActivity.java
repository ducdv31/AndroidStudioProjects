package com.example.iotcam;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.util.SparseArray;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.vision.Frame;
import com.google.android.gms.vision.text.TextBlock;
import com.google.android.gms.vision.text.TextRecognizer;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.opencv.android.BaseLoaderCallback;
import org.opencv.android.CameraBridgeViewBase;
import org.opencv.android.JavaCameraView;
import org.opencv.android.LoaderCallbackInterface;
import org.opencv.android.OpenCVLoader;
import org.opencv.android.Utils;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.MatOfPoint;
import org.opencv.core.MatOfPoint2f;
import org.opencv.core.MatOfRect;
import org.opencv.core.Point;
import org.opencv.core.Rect;
import org.opencv.core.RotatedRect;
import org.opencv.core.Scalar;
import org.opencv.core.Size;
import org.opencv.imgproc.Imgproc;
import org.opencv.objdetect.CascadeClassifier;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements
        CameraBridgeViewBase.CvCameraViewListener2 {
    private JavaCameraView javaCameraView;
    private CascadeClassifier faceDetector;
    private Mat mRgba;
    private Mat mGray;
    private Mat mBin;
    private Mat kernel;
    private Mat mHist;
    private Mat mMorph;
    private Mat mSub;
    private Mat mThresh;
    private Mat mEdge;
    private Mat mDetect;
    private Mat mSubmat;
    private String NoF;
    private TextView licPlate;
    private setDatabase NumPlate;
    private setDatabase PreProcess;
    // Server
    private setDatabase setData;
    private LinearLayout gestureLayout;
    private BottomSheetBehavior<LinearLayout> sheetBehavior;
    private TextView NumOfFaces;
    protected ImageView bottomSheetArrow;
    private TextView ServerStatus;
    private TextView DataStatus;
    //    private Button SignOut;
//    private Button BackLogIn;
    private GoogleSignInClient mGoogleSignInClient;
    private Mat Points, QRcode;

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser == null) {
            BackLogIn();
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.HONEYCOMB)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (!OpenCVLoader.initDebug()) {
            Log.e("OpenCV debug", "Fail");
        } else {
            Log.i("OpenCV debug", "Success");
        }

        //        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_FULLSCREEN);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
        NumOfFaces = (TextView) findViewById(R.id.NumOfFaces);
        createRequest();
        // Layout
        LinearLayout bottomSheetLayout = (LinearLayout) findViewById(R.id.infoSheetLayout);
        gestureLayout = (LinearLayout) findViewById(R.id.gestureLayout);
        sheetBehavior = BottomSheetBehavior.from(bottomSheetLayout);
        bottomSheetArrow = findViewById(R.id.bottom_sheet_arrow);
//        SignOut = findViewById(R.id.button_sign_out);
        ServerStatus = (TextView) findViewById(R.id.ServerStatus);
        DataStatus = findViewById(R.id.data);
        licPlate = findViewById(R.id.lic_plate);
        NumPlate = new setDatabase("Number Plate");
        PreProcess = new setDatabase("Pre-Process");
//        SignOut.setBackgroundColor(Color.RED);
//        SignOut.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                signOut();
//            }
//        });
//        BackLogIn = findViewById(R.id.BackToLogIn);
//        BackLogIn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                BackLogIn();
//            }
//        });
        // Check net
        CheckNet();
        updateUI();
        javaCameraView = (JavaCameraView) findViewById(R.id.javaCamView);
        if (!OpenCVLoader.initDebug()) {
            OpenCVLoader.initAsync(OpenCVLoader.OPENCV_VERSION_3_0_0, this, baseCallBack);

        } else {
            try {
                baseCallBack.onManagerConnected(LoaderCallbackInterface.SUCCESS);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        javaCameraView.setCvCameraViewListener(this);
        ViewTreeObserver vto = gestureLayout.getViewTreeObserver();
        vto.addOnGlobalLayoutListener(
                new ViewTreeObserver.OnGlobalLayoutListener() {
                    @Override
                    public void onGlobalLayout() {
                        gestureLayout.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                        //                int width = bottomSheetLayout.getMeasuredWidth();
                        int height = gestureLayout.getMeasuredHeight();
                        sheetBehavior.setPeekHeight(height);
                    }
                }
        );
        sheetBehavior.setHideable(false);

        sheetBehavior.addBottomSheetCallback(
                new BottomSheetBehavior.BottomSheetCallback() {
                    @Override
                    public void onStateChanged(@NonNull View bottomSheet, int newState) {
                        switch (newState) {
                            case BottomSheetBehavior.STATE_HIDDEN:
                                break;
                            case BottomSheetBehavior.STATE_EXPANDED:
                                bottomSheetArrow.setImageResource(R.mipmap.arrow_down);
                                break;
                            case BottomSheetBehavior.STATE_COLLAPSED:
                            case BottomSheetBehavior.STATE_SETTLING:
                                bottomSheetArrow.setImageResource(R.mipmap.arrow_up);
                                break;
                            case BottomSheetBehavior.STATE_DRAGGING:
                                bottomSheetArrow.setImageResource(R.mipmap.line_dot);
                                break;
                        }
                    }

                    @Override
                    public void onSlide(@NonNull View bottomSheet, float slideOffset) {

                    }
                }
        );
    }

    @Override
    public void onCameraViewStarted(int width, int height) {
        mRgba = new Mat();
        mGray = new Mat();
        mBin = new Mat();
        kernel = new Mat();
        mHist = new Mat();
        mMorph = new Mat();
        mSub = new Mat();
        mThresh = new Mat();
        mEdge = new Mat();
        mDetect = new Mat();
        Points = new Mat();
        QRcode = new Mat();
        mSubmat = new Mat();
    }

    @Override
    public void onCameraViewStopped() {
        mRgba.release();
        mGray.release();
        mBin.release();
        mHist.release();
        mMorph.release();
        mSub.release();
        mThresh.release();
        mEdge.release();
        mDetect.release();
        Points.release();
        QRcode.release();
        mSubmat.release();
        sendData(NumPlate, "Closed Camera");

    }

    @Override
    public Mat onCameraFrame(CameraBridgeViewBase.CvCameraViewFrame inputFrame) {

        mRgba = inputFrame.rgba();
        mGray = inputFrame.gray();
//        detectFaces();
        detectLicPlate(mGray);

        return mRgba;
    }


    private void detectLicPlate(Mat Gray) {
        // Detect rectangle
        Imgproc.blur(Gray, Gray, new Size(5, 5));
        // Morphology open
//        kernel = Imgproc.getStructuringElement(Imgproc.MORPH_CROSS, new Size(21, 21));
//        Imgproc.morphologyEx(Gray, mMorph, Imgproc.MORPH_OPEN, kernel);
        //delete background with subtract
//        Core.subtract(mGray, mMorph, mSub);
        //threshold otsu
        Imgproc.threshold(Gray, mThresh, 100, 255, Imgproc.THRESH_BINARY);
        // Detect edge by Canny
        Imgproc.Canny(mThresh, mEdge, 100, 255);
        // dilate
        Imgproc.dilate(mEdge, mEdge, new Mat());
        // find contours
//        mEdge.copyTo(mRgba);
        findContours1(mRgba, mEdge);
//        Core.subtract(mRgba, mDetect, mRgba);
    }


    private void findContours1(Mat RGB, Mat Edge) {
        List<MatOfPoint> contours = new ArrayList<MatOfPoint>();
        Imgproc.findContours(Edge,
                contours,
                new Mat(),
                Imgproc.RETR_EXTERNAL,
                Imgproc.CHAIN_APPROX_SIMPLE);
        if (contours.size() > 8) {
            MatOfPoint temp_contour = contours.get(0);
            for (int i = 0; i < contours.size(); i++) {
                temp_contour = contours.get(i);
                MatOfPoint2f new_mat = new MatOfPoint2f(temp_contour.toArray());
                int contourSize = (int) temp_contour.total();
                MatOfPoint2f approxCurve_temp = new MatOfPoint2f();
                Imgproc.approxPolyDP(new_mat,
                        approxCurve_temp,
                        Imgproc.arcLength(new_mat, true) * 0.02,
                        true);
                double total = approxCurve_temp.total();
                if (total >= 4 && total <= 5) {
                    MatOfPoint points = new MatOfPoint(approxCurve_temp.toArray());
                    Rect rect = Imgproc.boundingRect(points);
                    Point[] vertices = new Point[4];
                    mSubmat = RGB.submat(rect);
                    float boxScale = (float) mSubmat.width() / mSubmat.height();
                    boolean condition = (boxScale >= 3 && boxScale <= 5.5) &&
                            (mSubmat.width() > 50 && mSubmat.height() > 20);
                    if (condition) {
                        mDetect = new Mat(RGB, rect);
                        RotatedRect r = Imgproc.minAreaRect(approxCurve_temp);
                        r.points(vertices);
                        drawBox(mRgba, vertices);
                    }
                }

                if (i == contours.size() - 1) {
                    try {
                        detectContext(mDetect);
                        wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } finally {
                        break;
                    }

                }
            }
        }

    }

    private void detectContext(Mat mat) {
        Bitmap bitmap = Bitmap.createBitmap(mat.width(), mat.height(), Bitmap.Config.ARGB_8888);
        Utils.matToBitmap(mat, bitmap);
        TextRecognizer textRecognizer = new TextRecognizer.Builder(getApplicationContext()).build();
        if (!textRecognizer.isOperational()) {
            Log.e("Text reg", "Error");
        } else {
            Frame frame = new Frame.Builder().setBitmap(bitmap).build();
            SparseArray<TextBlock> items = textRecognizer.detect(frame);
            StringBuilder stringBuilder = new StringBuilder();

            TextBlock item = items.valueAt(0);
            stringBuilder.append(item.getValue());
            NumOfFaces.setText(stringBuilder.toString());
            String LP = stringBuilder.toString() + " ";
            sendData(PreProcess, LP);
            if ((LP.regionMatches(3,
                    "21A-311.99 ",
                    3,
                    1) || LP.regionMatches(4,
                    "21 A-311.99 ",
                    4,
                    1)) && LP.regionMatches(7,
                    "21A-311.99 ",
                    7,
                    1) && LP.regionMatches(10,
                    "21A-311.99 ",
                    10,
                    1)) {
                licPlate.setText(stringBuilder.toString());
                DataStatus.setText(stringBuilder.toString());
                sendData(NumPlate, LP);
            } else {
                mDetect.release();
            }
        }


    }


    private void detectFaces() {
        // Detect Face
        Imgproc.equalizeHist(mGray, mGray);
        MatOfRect faceDetections = new MatOfRect();
        faceDetector.detectMultiScale(mGray,
                faceDetections,
                1.2,
                3,
                0,
                new Size(50, 50),
                new Size(mGray.width(), mGray.height()));
        //Count faces
        int nof = faceDetections.toArray().length;
        setData = new setDatabase("NoF", nof);
        setData.set();
        NoF = Integer.toString(nof);
        setNoF(NoF);
        for (Rect rect : faceDetections.toArray()) {
            Imgproc.rectangle(mRgba,
                    new Point(rect.x, rect.y),
                    new Point(rect.x + rect.width, rect.y + rect.height),
                    new Scalar(0, 255, 0),
                    2);

        }

    }

    private void setNoF(String noF) {
        NumOfFaces.setText(NoF);
    }


    private final BaseLoaderCallback baseCallBack = new BaseLoaderCallback(this) {
        @Override
        public void onManagerConnected(int status) throws IOException {

            switch (status) {
                case LoaderCallbackInterface.SUCCESS: {
//                    InputStream is = getResources().openRawResource(R.raw.haarcascade_frontalface_alt2);
//                    File cascadeDir = getDir("cascade", MODE_PRIVATE);
//                    File cascaFile = new File(cascadeDir, "haarcascade_frontalface_alt2.xml");
//                    FileOutputStream fos = new FileOutputStream(cascaFile);
//
//                    byte[] buffer = new byte[4096];
//                    int byteRead;
//                    while ((byteRead = is.read(buffer)) != -1) {
//
//                        fos.write(buffer, 0, byteRead);
//                    }
//                    is.close();
//                    fos.close();
//                    faceDetector = new CascadeClassifier(cascaFile.getAbsolutePath());
//
//                    if (faceDetector.empty()) {
//                        faceDetector = null;
//
//                    } else {
//                        cascadeDir.delete();
//                    }

                    javaCameraView.enableView();
                }
                break;
                default: {
                    super.onManagerConnected(status);
                }
                break;

            }

        }


    };

    private void sendData(setDatabase SetDatabase, String data) {
        SetDatabase.setStringData(data);
    }

    private Mat drawBox(Mat Image, Point[] vertices) {
        List<MatOfPoint> boxContours = new ArrayList<>();
        boxContours.add(new MatOfPoint(vertices));
        Imgproc.drawContours(Image, boxContours, 0, new Scalar(200, 0, 0), 2);

        // Box draw
//                    Point[] vertices = new Point[4];
//                    r.points(vertices);
//                    List<MatOfPoint> boxContours = new ArrayList<>();
//                    boxContours.add(new MatOfPoint(vertices));
//                    Imgproc.drawContours(img, boxContours, 0, new Scalar(128, 128, 128), 5);
        // Line Draw

//            for (int j = 0; j < 4; j++) {
//                double Lx1 = vertices[(j + 1) % 4].x - vertices[j].x;
//                double Ly1 = vertices[(j + 1) % 4].y - vertices[j].y;
//                double Lx2 = vertices[(j + 2) % 4].x - vertices[j + 1].x;
//                double Ly2 = vertices[(j + 2) % 4].y - vertices[j + 1].y;
//                if (Lx1 > 50 && Ly1 > 20) {
//                    drawBox(vertices);
//                }

//                Imgproc.line(mRgba,
//                        vertices[j],
//                        vertices[(j + 1) % 4],
//                        new Scalar(0, 255, 0),
//                        8);
        return Image;
    }

    private void updateUI() {
        GoogleSignInAccount acct = GoogleSignIn.getLastSignedInAccount(this);
        if (acct != null) {
            String personName = acct.getDisplayName();
            String personEmail = acct.getEmail();
            String personId = acct.getId();
            Uri personPhoto = acct.getPhotoUrl();
            Toast.makeText(this, personName, Toast.LENGTH_SHORT).show();
        }
    }

    private void signOut() {
        FirebaseAuth.getInstance().signOut();
        revokeAccess();
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        switch (keyCode) {
            case KeyEvent.KEYCODE_BACK:
//                Intent startMain = new Intent(Intent.ACTION_MAIN);
//                startMain.addCategory(Intent.CATEGORY_HOME);
//                startActivity(startMain);
//                finish();
                BackLogIn();
                break;
        }
        return super.onKeyDown(keyCode, event);
    }


    private void revokeAccess() {
        mGoogleSignInClient.revokeAccess()
                .addOnCompleteListener(this, new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        // ...
                        Toast.makeText(MainActivity.this, "Logged out", Toast.LENGTH_SHORT).show();
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

    private void BackLogIn() {
        Intent back2LogIn = new Intent(MainActivity.this, sign_in.class);
        startActivity(back2LogIn);
    }

    private void CheckNet() {
        DatabaseReference connectedRef = FirebaseDatabase.getInstance().getReference(".info/connected");
        connectedRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                boolean connected = snapshot.getValue(Boolean.class);
                if (connected) {
                    ServerStatus.setText("Connected");
                    ServerStatus.setTextColor(Color.BLUE);
                } else {
                    ServerStatus.setText("Disconnected");
                    ServerStatus.setTextColor(Color.RED);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                ServerStatus.setText("Cancelled");
                ServerStatus.setTextColor(Color.RED);
            }
        });
    }
}

