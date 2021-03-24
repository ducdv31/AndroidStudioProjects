package com.example.iotcam;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.MenuInflater;

import org.opencv.android.BaseLoaderCallback;
import org.opencv.android.CameraBridgeViewBase;
import org.opencv.android.LoaderCallbackInterface;
import org.opencv.core.Mat;
import org.opencv.core.Scalar;
import org.opencv.objdetect.CascadeClassifier;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

class FaceDetection extends Activity implements CameraBridgeViewBase.CvCameraViewListener2 {

    private static final String TAG = "OCSample::Activity";
    private static final Scalar FACE_RECT_COLOR = new Scalar(0, 366, 0, 255);
    public static final int JAVA_DETECTOR = 0;
    public static final int NATIVE_DETECTOR = 1;

    private MenuInflater mItemFace50;
    private MenuInflater mItemFace40;
    private MenuInflater mItemFace30;
    private MenuInflater mItemFace20;
    private MenuInflater mItemType;

    private Mat mRgba;
    private Mat mGray;
    private File mCascadeFile;
    private CascadeClassifier mJavaDetector;
    private DetectionBasedTracker mNativeDetector;

    private int mDetectorType = JAVA_DETECTOR;
    private String[] mDetectorName;

    private float mRelativeFaceSize = 0.2f;
    private int mAbsoluteFaceSize = 0;

    private CameraBridgeViewBase mOpenCvCameraView;

    private BaseLoaderCallback mLoaderCallBack  = new BaseLoaderCallback(this) {
        @Override
        public void onManagerConnected(int status) throws IOException {
            switch (status)
            {
                case LoaderCallbackInterface.SUCCESS:
                {
                    Log.i(TAG,"OpenCV loaded successfully");
                    // Load native library after(!) OpenCV initialization
                    System.loadLibrary("detection_based_tracker");
                    try{
                        //Load cascade file from application resources
                        InputStream is = getResources().openRawResource(R.raw.lbpcascade_frontalface);
                        File cascadeDir = getDir("cascade", Context.MODE_PRIVATE);
                        mCascadeFile = new File(cascadeDir,"lbpcascade_frontalface.xml");
                        FileOutputStream os = new FileOutputStream(mCascadeFile);

                        byte[] buffer = new byte[4096];
                        int bytesRead;
                        while ((bytesRead = is.read(buffer))!=-1)
                        {
                            os.write(buffer,0,bytesRead);
                        }
                        is.close();
                        os.close();

                        mJavaDetector = new CascadeClassifier(mCascadeFile.getAbsolutePath());
                        if (mJavaDetector.empty())
                        {
                            Log.e(TAG,"Failed to load cascade classifier");
                            mJavaDetector = null;
                        }
                        else
                            Log.i(TAG,"Loaded cascade classifier from "+ mCascadeFile.getAbsolutePath());
                        mNativeDetector = new DetectionBasedTracker(mCascadeFile.getAbsolutePath(),0);
                        cascadeDir.delete();

                    } catch (IOException e){
                        e.printStackTrace();
                        Log.e(TAG,"Failed to load cascade. Exception thrown: "+e);
                    }
                    mOpenCvCameraView.enableView();
                } break;
                default:
                {
                    super.onManagerConnected(status);
                } break;
            }

        }
    };
    // Constructor
    public FaceDetection(){
        mDetectorName = new String[2];
        mDetectorName[JAVA_DETECTOR]="Java";
        mDetectorName[NATIVE_DETECTOR]="Native (tracking)";

        Log.i(TAG,"Instantiated new "+this.getClass());
    }

    @Override
    public void onCameraViewStarted(int width, int height) {

    }

    @Override
    public void onCameraViewStopped() {

    }

    @Override
    public Mat onCameraFrame(CameraBridgeViewBase.CvCameraViewFrame inputFrame) {
        return null;
    }
}
