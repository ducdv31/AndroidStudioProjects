package com.example.detectgeometry;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import org.opencv.android.OpenCVLoader;
import org.opencv.android.Utils;
import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.MatOfPoint;
import org.opencv.core.MatOfPoint2f;
import org.opencv.core.Point;
import org.opencv.core.Rect;
import org.opencv.core.RotatedRect;
import org.opencv.core.Scalar;
import org.opencv.core.Size;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.opencv.core.Core.extractChannel;

public class MainActivity extends AppCompatActivity {

    private static final int ACCESS_FILE = 10;
    private static final int PERMISSION_FILE = 20;
    private Button button;
    private ImageView imageView;
    private Mat ImageSrc, ImageDst;
    private double LPP = 0.1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        button = findViewById(R.id.SelectImg);
        imageView = findViewById(R.id.Image);
        OpenCVLoader.initDebug();
        ImageSrc = new Mat();
        ImageDst = new Mat();

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ContextCompat.checkSelfPermission(MainActivity.this,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(MainActivity.this,
                            new String[]{
                                    Manifest.permission.WRITE_EXTERNAL_STORAGE
                            },
                            PERMISSION_FILE);
                } else {
                    Intent intent = new Intent();
                    intent.setAction(Intent.ACTION_GET_CONTENT);
                    intent.setType("image/*");
                    startActivityForResult(Intent.createChooser(intent, "Image"), ACCESS_FILE);
                }
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == ACCESS_FILE && resultCode == Activity.RESULT_OK && data != null && data.getData() != null) {
            Uri uri = data.getData();
            String filePath = getRealPathFromDocumentUri(MainActivity.this, uri);// Load real path **
            ImageSrc = loadImage(filePath);
            ImageDst = ProcessMat(ImageSrc);
            ShowMatImg(imageView, ImageDst);
//            ShowImg(imageView, filePath);
//            imageView.setImageURI(uri);

        }
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

    private Mat loadImage(String path) {
        Mat originImage = Imgcodecs.imread(path);   // Image will be in BGR format
        Mat ImgSrc = new Mat();
        Mat ImgDst = new Mat();
        Imgproc.cvtColor(originImage, ImgSrc, Imgproc.COLOR_BGR2RGBA);
        if (originImage == null) {
            Log.e("Mat image", "NULL");
        } else {
            int width = originImage.width();
            Log.e("Width", String.valueOf(width));
        }
        ImgSrc.copyTo(ImgDst);
        return ImgDst;
    }

    private void ShowImg(ImageView Img, String path) { // from real path
        Bitmap bitmap = BitmapFactory.decodeFile(path);
        Img.setImageBitmap(bitmap);
    }

    private Mat ProcessMat(Mat Image) {
        Image = detectLength(Image);

        return Image;
    }

    private Mat detectLength(Mat img) {
        final DecimalFormat format = new DecimalFormat("#.0");
        Mat mGray = new Mat();
        Mat mEdge = new Mat();
        Mat kernel = Imgproc.getStructuringElement(Imgproc.MORPH_CROSS, new Size(9, 9));
        Imgproc.cvtColor(img, mGray, Imgproc.COLOR_BGR2GRAY);
        Imgproc.medianBlur(mGray, mGray, 9);
        Imgproc.Canny(mGray, mEdge, 50, 255);
//        Imgproc.morphologyEx(mEdge, mEdge, Imgproc.MORPH_OPEN, kernel);

        List<MatOfPoint> contours = new ArrayList<MatOfPoint>();
        Imgproc.findContours(mEdge,
                contours,
                new Mat(),
                Imgproc.RETR_EXTERNAL,
                Imgproc.CHAIN_APPROX_SIMPLE);
        double maxVal = 0;
        int maxValIdx = 0;
        for (int contourIdx = 0; contourIdx < contours.size(); contourIdx++) {
            double contourArea = Imgproc.contourArea(contours.get(contourIdx));
            if (maxVal < contourArea) {
                maxVal = contourArea;
                maxValIdx = contourIdx;

                MatOfPoint temp_contour = contours.get(0);
                for (int i = 0; i < contours.size(); i++) {
                    temp_contour = contours.get(i);
                    MatOfPoint2f new_mat = new MatOfPoint2f(temp_contour.toArray());
                    int contourSize = (int) temp_contour.total();
                    MatOfPoint2f approxCurve_temp = new MatOfPoint2f();
                    Imgproc.approxPolyDP(new_mat,
                            approxCurve_temp,
                            contourSize * 0.05,
                            true);
                    RotatedRect r = Imgproc.minAreaRect(approxCurve_temp);
                    Rect rect = r.boundingRect();

                    // Line Draw
                    Point[] vertices = new Point[4];
                    r.points(vertices);
                    for (int j = 0; j < 4; j++) {
                        double Lx = vertices[(j + 1) % 4].x - vertices[j].x;
                        double Ly = vertices[(j + 1) % 4].y - vertices[j].y;
                        double tbx = (vertices[(j + 1) % 4].x + vertices[j].x) / 2;
                        double tby = (vertices[(j + 1) % 4].y + vertices[j].y) / 2;
                        double Length = Math.sqrt(Math.pow(Lx, 2) + Math.pow(Ly, 2));

                        if (Length > 100) {
                            r.points(vertices);
                            List<MatOfPoint> boxContours = new ArrayList<>();
                            boxContours.add(new MatOfPoint(vertices));
                            Imgproc.drawContours(img, boxContours, 0, new Scalar(128, 128, 128), 5);
                            Imgproc.putText(img,
                                    String.valueOf(format.format(Length * LPP)),
                                    new Point(tbx, tby),
                                    Core.FONT_HERSHEY_SIMPLEX,
                                    2,
                                    new Scalar(255, 0, 0),
                                    3);
                        }
//                        Imgproc.line(img, vertices[j], vertices[(j + 1) % 4], new Scalar(0, 255, 0), 8);
                        Log.e("Length", String.valueOf(Lx) + String.valueOf(Ly));

                    }

                    // Box draw
//                    Point[] vertices = new Point[4];
//                    r.points(vertices);
//                    List<MatOfPoint> boxContours = new ArrayList<>();
//                    boxContours.add(new MatOfPoint(vertices));
//                    Imgproc.drawContours(img, boxContours, 0, new Scalar(128, 128, 128), 5);
                }
            }

//            Imgproc.drawContours(img, contours, maxValIdx, new Scalar(255, 0, 0), 5);
            MatOfPoint2f pointsMat = new MatOfPoint2f();
//        RotatedRect r = Imgproc.minAreaRect(pointsMat);
//        Rect rect = r.boundingRect();

//        MatOfPoint temp_contour = contours.get(0);
//        for (int i = 0; i < contours.size(); i++) {
//            temp_contour = contours.get(i);
//            MatOfPoint2f new_mat = new MatOfPoint2f(temp_contour.toArray());
//            int contourSize = (int) temp_contour.total();
//            MatOfPoint2f approxCurve_temp = new MatOfPoint2f();
//            Imgproc.approxPolyDP(new_mat,
//                    approxCurve_temp,
//                    contourSize * 0.05,
//                    true);
//            RotatedRect r = Imgproc.minAreaRect(approxCurve_temp);
//            Rect rect = r.boundingRect();
//            Point[] vertices = new Point[4];
//            r.points(vertices);
//            for (int j = 0; j < 4; j++){
//                Imgproc.line(img, vertices[j], vertices[(j+1)%4], new Scalar(0,255,0),8);
//            }
        }


        return img;

    }

    private void ShowMatImg(ImageView imageView, Mat img) {
        Bitmap bitmap = Bitmap.createBitmap(img.cols(), img.rows(), Bitmap.Config.ARGB_8888);
        Utils.matToBitmap(img, bitmap);// convert Mat to Bitmap
        imageView.setImageBitmap(bitmap);

    }

    private void detectGeometry(Mat Image) {
        Mat ImgGray = new Mat();
        Mat Circle = new Mat();
        Imgproc.cvtColor(Image, ImgGray, Imgproc.COLOR_BGR2GRAY);
        // Detect circle
        Imgproc.HoughCircles(ImgGray,
                Circle,
                Imgproc.CV_HOUGH_GRADIENT,
                1,
                100,
                50,
                52,
                0);
        // Draw circle
        for (int i = 0; i < Circle.cols(); i++) {
            double[] circleCoordinates = Circle.get(0, i);
            int x = (int) circleCoordinates[0], y = (int) circleCoordinates[1];
            Point center = new Point(x, y);
            int radius = (int) circleCoordinates[2];
            // Circle outline
            Imgproc.circle(Image,
                    center,
                    radius,
                    new Scalar(0, 0, 255),
                    2);
            Imgproc.putText(Image,
                    "cir",
                    new Point(x - 10, y + 5),
                    Core.FONT_HERSHEY_SIMPLEX,
                    0.5,
                    new Scalar(0, 0, 255),
                    2);
        }
        // Detect rectangle
        List<MatOfPoint> contours = new ArrayList<MatOfPoint>();
        Imgproc.findContours(ImgGray,
                contours,
                new Mat(),
                Imgproc.RETR_LIST,
                Imgproc.CHAIN_APPROX_SIMPLE);

        MatOfPoint temp_contour = contours.get(0);
        for (int i = 0; i < contours.size(); i++) {
            temp_contour = contours.get(i);
            MatOfPoint2f new_mat = new MatOfPoint2f(temp_contour.toArray());
            int contourSize = (int) temp_contour.total();
            MatOfPoint2f approxCurve_temp = new MatOfPoint2f();
            Imgproc.approxPolyDP(new_mat,
                    approxCurve_temp,
                    contourSize * 0.05,
                    true);
            if (approxCurve_temp.total() == 4) {
                MatOfPoint points = new MatOfPoint(approxCurve_temp.toArray());
                Rect rect = Imgproc.boundingRect(points);
                Imgproc.rectangle(Image,
                        new Point(rect.x, rect.y),
                        new Point(rect.x + rect.width, rect.y + rect.height),
                        new Scalar(255, 0, 255),
                        2);
                if (rect.width >= rect.height * 0.96 && rect.width <= rect.height * 1.06) {
                    Imgproc.putText(Image,
                            "squ",
                            new Point(rect.x + (rect.width >> 2), rect.y + (rect.height >> 1)),
                            Core.FONT_HERSHEY_SIMPLEX,
                            0.5,
                            new Scalar(0, 0, 255),
                            2);

                } else {
                    Imgproc.putText(Image,
                            "rect",
                            new Point(rect.x + (rect.width >> 2), rect.y + (rect.height >> 1)),
                            Core.FONT_HERSHEY_SIMPLEX,
                            0.5,
                            new Scalar(0, 0, 255),
                            2);

                }
            }


        }
        for (int i = 0; i < contours.size(); i++) {
            temp_contour = contours.get(i);
            MatOfPoint2f new_mat = new MatOfPoint2f(temp_contour.toArray());
            int contourSize = (int) temp_contour.total();
            MatOfPoint2f approxCurve_temp = new MatOfPoint2f();
            Imgproc.approxPolyDP(new_mat,
                    approxCurve_temp,
                    contourSize,
                    true);
            if (approxCurve_temp.total() == 4) {
                MatOfPoint points = new MatOfPoint(approxCurve_temp.toArray());
                Rect rect = Imgproc.boundingRect(points);
                Imgproc.rectangle(Image,
                        new Point(rect.x, rect.y),
                        new Point(rect.x + rect.width, rect.y + rect.height),
                        new Scalar(255, 0, 255),
                        2);
                Log.e("rect width", String.valueOf(rect.width));
                Log.e("rect height", String.valueOf(rect.height));
                if (rect.width >= rect.height * 0.9 && rect.width <= rect.height * 1.1) {
                    Imgproc.putText(Image,
                            "squ",
                            new Point(rect.x + (rect.width >> 2), rect.y + (rect.height >> 1)),
                            Core.FONT_HERSHEY_SIMPLEX,
                            0.5,
                            new Scalar(0, 0, 255),
                            2);

                } else {
                    Imgproc.putText(Image,
                            "rect",
                            new Point(rect.x + (rect.width >> 2), rect.y + (rect.height >> 1)),
                            Core.FONT_HERSHEY_SIMPLEX,
                            0.5,
                            new Scalar(0, 0, 255),
                            2);

                }
            }
        }
        // Detect triangle
        for (int i = 0; i < contours.size(); i++) {
            temp_contour = contours.get(i);
            MatOfPoint2f new_mat = new MatOfPoint2f(temp_contour.toArray());
            int contourSize = (int) temp_contour.total();
            MatOfPoint2f approxCurve_temp = new MatOfPoint2f();
            Imgproc.approxPolyDP(new_mat,
                    approxCurve_temp,
                    contourSize * 0.05,
                    true);
            if (approxCurve_temp.total() == 3) {
                MatOfPoint points = new MatOfPoint(approxCurve_temp.toArray());
                Rect rect = Imgproc.boundingRect(points);
                Imgproc.rectangle(Image,
                        new Point(rect.x, rect.y),
                        new Point(rect.x + rect.width, rect.y + rect.height),
                        new Scalar(255, 0, 255),
                        2);
                Imgproc.putText(Image,
                        "tri",
                        new Point(rect.x + (rect.width >> 2), rect.y + (rect.height >> 1)),
                        Core.FONT_HERSHEY_SIMPLEX,
                        0.5,
                        new Scalar(0, 0, 255),
                        2);
            }


        }
        // Detect pentagon
        for (int i = 0; i < contours.size(); i++) {
            temp_contour = contours.get(i);
            MatOfPoint2f new_mat = new MatOfPoint2f(temp_contour.toArray());
            int contourSize = (int) temp_contour.total();
            MatOfPoint2f approxCurve_temp = new MatOfPoint2f();
            Imgproc.approxPolyDP(new_mat,
                    approxCurve_temp,
                    contourSize * 0.05,
                    true);
            if (approxCurve_temp.total() == 5) {
                MatOfPoint points = new MatOfPoint(approxCurve_temp.toArray());
                Rect rect = Imgproc.boundingRect(points);
                Imgproc.rectangle(Image,
                        new Point(rect.x, rect.y),
                        new Point(rect.x + rect.width, rect.y + rect.height),
                        new Scalar(255, 0, 255),
                        2);
                Imgproc.putText(Image,
                        "penta",
                        new Point(rect.x + (rect.width >> 2), rect.y + (rect.height >> 1)),
                        Core.FONT_HERSHEY_SIMPLEX,
                        0.5,
                        new Scalar(0, 0, 255),
                        2);
            }


        }
        // Detect hexagon
        for (int i = 0; i < contours.size(); i++) {
            temp_contour = contours.get(i);
            MatOfPoint2f new_mat = new MatOfPoint2f(temp_contour.toArray());
            int contourSize = (int) temp_contour.total();
            MatOfPoint2f approxCurve_temp = new MatOfPoint2f();
            Imgproc.approxPolyDP(new_mat,
                    approxCurve_temp,
                    contourSize * 0.05,
                    true);
            if (approxCurve_temp.total() == 6) {
                MatOfPoint points = new MatOfPoint(approxCurve_temp.toArray());
                Rect rect = Imgproc.boundingRect(points);
                Imgproc.rectangle(Image,
                        new Point(rect.x, rect.y),
                        new Point(rect.x + rect.width, rect.y + rect.height),
                        new Scalar(255, 0, 255),
                        2);
                Imgproc.putText(Image,
                        "hexa",
                        new Point(rect.x + (rect.width >> 2), rect.y + (rect.height >> 1)),
                        Core.FONT_HERSHEY_SIMPLEX,
                        0.5,
                        new Scalar(0, 0, 255),
                        2);
            }


        }
    }
}