package com.example.imageprocess;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Point;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Bundle;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.mlkit.common.model.DownloadConditions;
import com.google.mlkit.common.model.RemoteModelManager;
import com.google.mlkit.vision.common.InputImage;
import com.google.mlkit.vision.label.ImageLabel;
import com.google.mlkit.vision.label.ImageLabeler;
import com.google.mlkit.vision.label.ImageLabeling;
import com.google.mlkit.vision.label.automl.AutoMLImageLabelerLocalModel;
import com.google.mlkit.vision.label.automl.AutoMLImageLabelerOptions;
import com.google.mlkit.vision.label.automl.AutoMLImageLabelerRemoteModel;

import org.opencv.android.OpenCVLoader;
import org.opencv.android.Utils;
import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.Scalar;
import org.opencv.core.Size;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;

import java.io.IOException;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static android.Manifest.*;

public class MainActivity extends AppCompatActivity {
    // Opencv

    // ML
    private ImageView imageView;
    private Button button;
    private TextView textView;
    private ProgressDialog dialog;
    private static final int ACCESS_FILE = 10;
    private static final int PERMISSION_FILE = 20;

    // Load the model
    //Create LocalModel object, specifying the path to the model manifest file:
    AutoMLImageLabelerLocalModel localModel =
            new AutoMLImageLabelerLocalModel.Builder()
                    .setAssetFilePath("model/manifest.json")
                    // or .setAbsoluteFilePath(absolute file path to manifest file)
                    .build();

    // Configure a Firebase-hosted model source
    // Specify the name you assigned in the Firebase console.
    AutoMLImageLabelerRemoteModel remoteModel =
            new AutoMLImageLabelerRemoteModel.Builder("fruit_2020111223325").build();
    // Download model if it isn't on the device
    DownloadConditions downloadConditions = new DownloadConditions.Builder()
            .requireWifi()
            .build();

    // OpenCv
    private Mat ImageSrc;
    private Mat ImageDst;
    private Mat mGray;
    private Mat Grad_x, Grad_y, Abs_Grad_x, Abs_Grad_y;
    private Mat cannyEdge;
    private Mat erodeImg, dilateImg;
    private Mat mHSV;
    private Mat originImage;
    private ClassifyFruit classifyFruit;
    private TextView classifyTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        imageView = findViewById(R.id.image);
        button = findViewById(R.id.button);
        textView = findViewById(R.id.textView);
        classifyTextView = (TextView) findViewById(R.id.classify9);
        OpenCVLoader.initDebug();
        dialog = new ProgressDialog(this);
        ImageDst = new Mat();
        mGray = new Mat();
        Grad_x = new Mat();
        Grad_y = new Mat();
        Abs_Grad_x = new Mat();
        Abs_Grad_y = new Mat();
        cannyEdge = new Mat();
        erodeImg = new Mat();
        dilateImg = new Mat();
        mHSV = new Mat();
        originImage = new Mat();
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ContextCompat.checkSelfPermission(MainActivity.this, permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(MainActivity.this, new String[]{permission.WRITE_EXTERNAL_STORAGE}, PERMISSION_FILE);
                } else {
                    Intent intent = new Intent();   // Open file manager
                    intent.setAction(Intent.ACTION_GET_CONTENT);
                    intent.setType("image/*");
                    startActivityForResult(Intent.createChooser(intent, "Fruit"), ACCESS_FILE);
                }
            }
        });


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == ACCESS_FILE && resultCode == Activity.RESULT_OK && data != null && data.getData() != null) {
            Uri uri = data.getData();   // Lay anh vua chon

            RemoteModelManager.getInstance().download(remoteModel, downloadConditions);
            // get Path from uri
            String Path = getPath(uri);
            Log.e("Path", Path);
            // Real path for OpenCv
            String filePath = getRealPathFromDocumentUri(MainActivity.this, uri);// Load real path **
            Log.e("File Path", filePath);

            // Load image and show
            loadImage(filePath); // imageDst != null for OpenCV
//            displayImage(ImageDst);

            setLabelerFromUri(uri); // function user - ML show detail
            textView.setText("");
            classifyTextView.setText("");
            imageView.setImageURI(uri); //Hien anh ra ImageView use uri

        }
    }

    private String getPath(Uri uri) {
        if (uri == null) {
            return null;
        } else {
            String[] projection = {MediaStore.Images.Media.DATA};
            Cursor cursor = getContentResolver().query(uri, projection, null, null, null);
            if (cursor == null) {
                int col_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
                cursor.moveToFirst();

                return cursor.getString(col_index);
            }
            return uri.getPath();
        }
    }


    private void loadImage(String path) {
        originImage = Imgcodecs.imread(path);   // Image will be in BGR format
        ImageSrc = new Mat();
        Imgproc.cvtColor(originImage, ImageSrc, Imgproc.COLOR_BGR2RGBA);
        if (originImage == null) {
            Log.e("Mat image", "NULL");
        } else {
            int width = originImage.width();
            Log.e("Width", String.valueOf(width));
        }

        ImageSrc.copyTo(ImageDst);


    }

    public static String getRealPathFromDocumentUri(Context context, Uri uri) {
        String filePath = "";

        Pattern p = Pattern.compile("(\\d+)$");
        Matcher m = p.matcher(uri.toString());
        if (!m.find()) {
            Log.e("No", "ID for requested image not found: " + uri.toString());
            return filePath;
        }
        String imgId = m.group();

        String[] column = {MediaStore.Images.Media.DATA};
        String sel = MediaStore.Images.Media._ID + "=?";

        Cursor cursor = context.getContentResolver().query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                column, sel, new String[]{imgId}, null);

        int columnIndex = cursor.getColumnIndex(column[0]);

        if (cursor.moveToFirst()) {
            filePath = cursor.getString(columnIndex);
        }
        cursor.close();

        return filePath;
    }

    private void setLabelerFromUri(Uri uri) {   // User
        showProgressDialog();
        //After you configure your model sources, create a ImageLabeler object from one of them.
        AutoMLImageLabelerOptions options =
                new AutoMLImageLabelerOptions.Builder(localModel)
                        .setConfidenceThreshold(0.0f) // Evaluate your model in the Firebase console
                        // to determine an appropriate value.
                        .build();

        ImageLabeler labeler = ImageLabeling.getClient(options);
        //Input Image
        InputImage image = null;
        try {
            image = InputImage.fromFilePath(MainActivity.this, uri);

        } catch (IOException e) {
            e.printStackTrace();
        }

        // Progress to show label
        progressMageLabeler(labeler, image);
    }

    private void progressMageLabeler(ImageLabeler labeler, InputImage image) {
        labeler.process(image).addOnSuccessListener(new OnSuccessListener<List<ImageLabel>>() {
            @Override
            public void onSuccess(List<ImageLabel> imageLabels) {
                dialog.dismiss();

                String eachLabel = imageLabels.get(0).getText().toUpperCase();
                Float confidence = imageLabels.get(0).getConfidence();
                textView.append(eachLabel + " : " + ("" + confidence * 100).subSequence(0, 4) + "%" + "\n");
                classifyFruit = new ClassifyFruit(ImageDst, eachLabel, classifyTextView);
                classifyFruit.classify();


            }
        })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(MainActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }


    private void showProgressDialog() {
        dialog.setMessage("Loading.....");
        dialog.setCancelable(false);
        dialog.show();
    }
}