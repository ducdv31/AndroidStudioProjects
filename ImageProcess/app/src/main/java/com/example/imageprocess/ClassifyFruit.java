package com.example.imageprocess;

import android.util.Log;
import android.widget.TextView;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.Size;
import org.opencv.imgproc.Imgproc;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

class ClassifyFruit {
    Mat InputMat = new Mat();
    Mat hsvImage = new Mat();
    List<Mat> img_hsv = new ArrayList<>();
    String label;
    double[] Image;
    private TextView textView;

    ClassifyFruit(Mat InputMat, String label, TextView textView) {
        this.InputMat = InputMat;
        this.label = label;
        this.textView = textView;

    }

    public void classify() {
        switch (label) {
            case "APPLE":
                appleClassify();
                break;
            case "BERRY":
                berryClassify();
                break;
            case "PAPAYA":
                papayaClassify();
                break;

            case "TOMATO":
                tomatoClassify();
                break;
            default:
                break;
        }
    }

    private void convert2hsv() {
        Imgproc.resize(InputMat, InputMat, new Size(InputMat.rows() / 3, InputMat.rows() / 3), 0, 0, Imgproc.INTER_AREA);
        Imgproc.cvtColor(InputMat, hsvImage, Imgproc.COLOR_BGR2HSV);
        Core.split(hsvImage, img_hsv);
    }

    private void tomatoClassify() {
        Log.e("Tomato", "classify");
        convert2hsv();
        int ripenTomato = countPixel(hsvImage, 0, 15, 0, 100, 0, 100)
                + countPixel(hsvImage, 340, 360, 0, 100, 0, 100);
        int greenTomato = countPixel(hsvImage, 95, 135, 0, 100, 70, 100);
        int ripen = ripenTomato * 100 / (ripenTomato + greenTomato);
        int green = greenTomato * 100 / (ripenTomato + greenTomato);
        Log.e("Count", String.valueOf(ripenTomato));
        Log.e("Count", String.valueOf(greenTomato));
        textView.setText("Qua chin: " + String.valueOf(ripen) + " % - Qua xanh: " + String.valueOf(green) + " %");
    }

    private void papayaClassify() {
        convert2hsv();
        Log.e("Papaya", "classify");
        int greenPapaya = countPixel(hsvImage, 25, 60, 0, 100, 80, 100);
        int ripenPapaya = countPixel(hsvImage, 95, 135, 80, 100, 30, 80);
        int ripen = ripenPapaya * 100 / (ripenPapaya + greenPapaya);
        int green = greenPapaya * 100 / (ripenPapaya + greenPapaya);
        textView.setText("Qua chin: " + String.valueOf(ripen) + " % - Qua xanh: " + String.valueOf(green) + " %");
    }

    private void berryClassify() {
        convert2hsv();
        Log.e("Berry", "classify");
        int greenBerry = countPixel(hsvImage, 25, 60, 80, 100, 80, 100);
        int ripenBerry = countPixel(hsvImage, 95, 135, 0, 100, 45, 70);
        int ripen = ripenBerry * 100 / (ripenBerry + greenBerry);
        int green = greenBerry * 100 / (ripenBerry + greenBerry);
        textView.setText("Qua chin: " + String.valueOf(ripen) + " % - Qua xanh: " + String.valueOf(green) + " %");
    }

    private void appleClassify() {
        Log.e("Apple", "classify");
        convert2hsv();
        int ripenApple = countPixel(hsvImage, 0, 360, 0, 200, 0, 100);
        int greenApple = countPixel(hsvImage, 80, 135, 60, 100, 60, 100);
        int ripen = ripenApple * 100 / (ripenApple + greenApple);
        int green = greenApple * 100 / (ripenApple + greenApple);
        Log.e("Apple ripen", String.valueOf(ripenApple));
        Log.e("Apple Green", String.valueOf(greenApple));
        textView.setText("Qua chin: " + String.valueOf(ripen) + " % - Qua xanh: " + String.valueOf(green) + " %");
    }

    private int countPixel(Mat inputHsv, int minH, int maxH, int minS, int maxS, int minV, int maxV) {
        int count = 0;
        for (int i = 0; i < inputHsv.width(); i++) {
            for (int j = 0; j < inputHsv.height(); j++) {
                double[] pixel = inputHsv.get(i, j);
                if ((pixel[0] > minH && pixel[0] <= maxH) && (pixel[1] > minS && pixel[1] <= maxS) && (pixel[2] > minV && pixel[2] < maxV)) {
                    count++;
                }
            }
        }
        return count;
    }
}
