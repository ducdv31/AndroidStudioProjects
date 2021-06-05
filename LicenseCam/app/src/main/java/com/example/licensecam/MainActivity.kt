package com.example.licensecam

import android.graphics.Bitmap
import android.os.Bundle
import android.util.Log
import android.util.SparseArray
import android.view.View
import android.view.ViewTreeObserver.OnGlobalLayoutListener
import android.view.WindowManager
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.vision.Frame
import com.google.android.gms.vision.text.TextBlock
import com.google.android.gms.vision.text.TextRecognizer
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetBehavior.BottomSheetCallback
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.withContext
import org.opencv.android.*
import org.opencv.android.CameraBridgeViewBase.CvCameraViewListener2
import org.opencv.core.*
import org.opencv.imgproc.Imgproc
import java.io.IOException
import java.util.*

class MainActivity : AppCompatActivity(), CvCameraViewListener2 {

    private lateinit var javaCameraView: JavaCameraView
    private lateinit var gestureLayout: LinearLayout
    private lateinit var sheetBehavior: BottomSheetBehavior<LinearLayout>
    private lateinit var bottomSheetArrow: ImageView

    /* view */
    private lateinit var ServerStatus: TextView
    private lateinit var DataStatus: TextView
    private lateinit var licPlate: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        if (!OpenCVLoader.initDebug()) {
            Log.e("OpenCV debug", "Fail")
        } else {
            Log.i("OpenCV debug", "Success")
        }

        //        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_FULLSCREEN);
        window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
        val actionBar = supportActionBar
        actionBar!!.hide()

        /* bottom sheer */
        // Layout
        val bottomSheetLayout = findViewById<View>(R.id.infoSheetLayout) as LinearLayout
        gestureLayout = findViewById<View>(R.id.gestureLayout) as LinearLayout
        sheetBehavior = BottomSheetBehavior.from(bottomSheetLayout)
        bottomSheetArrow = findViewById(R.id.bottom_sheet_arrow)
        /* ***************** */
        //        SignOut = findViewById(R.id.button_sign_out);
        ServerStatus = findViewById<View>(R.id.ServerStatus) as TextView
        DataStatus = findViewById<TextView>(R.id.data)
        licPlate = findViewById<TextView>(R.id.lic_plate)
//        NumPlate = setDatabase("Number Plate")
//        PreProcess = setDatabase("Pre-Process")

        /* camView setup */
        javaCameraView = findViewById<View>(R.id.javaCamView) as JavaCameraView
        if (!OpenCVLoader.initDebug()) {
            OpenCVLoader.initAsync(OpenCVLoader.OPENCV_VERSION_3_0_0, this, baseCallBack)
        } else {
            try {
                baseCallBack.onManagerConnected(LoaderCallbackInterface.SUCCESS)
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
        javaCameraView.setCvCameraViewListener(this)

        /* bottom sheet setup */
        val vto = gestureLayout.viewTreeObserver
        vto.addOnGlobalLayoutListener(
            object : OnGlobalLayoutListener {
                override fun onGlobalLayout() {
                    gestureLayout.viewTreeObserver.removeOnGlobalLayoutListener(this)
                    //                int width = bottomSheetLayout.getMeasuredWidth();
                    val height = gestureLayout.measuredHeight
                    sheetBehavior.peekHeight = height
                }
            }
        )
        sheetBehavior.isHideable = false

        sheetBehavior.addBottomSheetCallback(
            object : BottomSheetCallback() {
                override fun onStateChanged(bottomSheet: View, newState: Int) {
//                    when (newState) {
//                        BottomSheetBehavior.STATE_HIDDEN -> {
//                        }
//                        BottomSheetBehavior.STATE_EXPANDED -> bottomSheetArrow.setImageResource(R.mipmap.arrow_down)
//                        BottomSheetBehavior.STATE_COLLAPSED, BottomSheetBehavior.STATE_SETTLING -> bottomSheetArrow.setImageResource(
//                            R.mipmap.arrow_up
//                        )
//                        BottomSheetBehavior.STATE_DRAGGING -> bottomSheetArrow.setImageResource(R.mipmap.line_dot)
//                    }
                }

                override fun onSlide(bottomSheet: View, slideOffset: Float) {}
            }
        )
    }

    private val baseCallBack: BaseLoaderCallback = object : BaseLoaderCallback(this) {
        @Throws(IOException::class)
        override fun onManagerConnected(status: Int) {
            when (status) {
                SUCCESS -> {
                    javaCameraView.enableView()
                }
                else -> {
                    super.onManagerConnected(status)
                }
            }
        }
    }

    override fun onCameraViewStarted(width: Int, height: Int) {

    }

    override fun onCameraViewStopped() {

    }

    override fun onCameraFrame(inputFrame: CameraBridgeViewBase.CvCameraViewFrame?): Mat {
        val mRgba = inputFrame!!.rgba()
        val mGray = inputFrame.gray()
        detectLicPlate(mRgba, mGray)

        return mRgba
    }

    private fun detectLicPlate(RGB: Mat, Gray: Mat) {
        // Detect rectangle
        val mThresh = Mat()
        val mEdge = Mat()

        Imgproc.blur(Gray, Gray, Size(5.0, 5.0))
        Imgproc.threshold(Gray, mThresh, 100.0, 255.0, Imgproc.THRESH_BINARY)
        Imgproc.Canny(mThresh, mEdge, 100.0, 255.0)
        Imgproc.dilate(mEdge, mEdge, Mat())
        findContours1(RGB, mEdge)
    }

    private fun findContours1(RGB: Mat, Edge: Mat) {
        val contours: List<MatOfPoint> = ArrayList()
        Imgproc.findContours(
            Edge,
            contours,
            Mat(),
            Imgproc.RETR_EXTERNAL,
            Imgproc.CHAIN_APPROX_SIMPLE
        )
        if (contours.size > 8) {
            var mSubmat: Mat
            var mDetect = Mat()

            var temp_contour = contours[0]
            for (i in contours.indices) {
                temp_contour = contours[i]
                val newMat = MatOfPoint2f(*temp_contour.toArray())
                val contourSize = temp_contour.total().toInt()
                val approxCurve_temp = MatOfPoint2f()
                Imgproc.approxPolyDP(
                    newMat,
                    approxCurve_temp,
                    Imgproc.arcLength(newMat, true) * 0.02,
                    true
                )
                val total = approxCurve_temp.total().toDouble()
                if (total in 4.0..5.0) {
                    val points = MatOfPoint(*approxCurve_temp.toArray())
                    val rect = Imgproc.boundingRect(points)
                    val vertices: Array<Point?> = arrayOfNulls(4)
                    mSubmat = RGB.submat(rect)
                    val boxScale: Float = mSubmat.width().toFloat() / mSubmat.height()
                    val condition = boxScale in 3.0..5.5 &&
                            mSubmat.width() > 50 && mSubmat.height() > 20
                    if (condition) {
                        mDetect = Mat(RGB, rect)
                        val r = Imgproc.minAreaRect(approxCurve_temp)
                        r.points(vertices)
                        drawBox(RGB, vertices)
                    }
                }
                if (i == contours.size - 1) {
                    val job = CoroutineScope(Dispatchers.IO).async {
                        detectContext(mDetect)
                    }
                }
            }
        }
    }

    private suspend fun detectContext(mDetect: Mat) {
        val bitmap = Bitmap.createBitmap(mDetect.width(), mDetect.height(), Bitmap.Config.ARGB_8888)
        Utils.matToBitmap(mDetect, bitmap)
        val textRecognizer: TextRecognizer = TextRecognizer.Builder(applicationContext).build()
        if (!textRecognizer.isOperational) {
            Log.e("Text reg", "Error")
        } else {
            val frame: Frame = Frame.Builder().setBitmap(bitmap).build()
            val items: SparseArray<TextBlock> = textRecognizer.detect(frame)
            val stringBuilder = StringBuilder()
            val item: TextBlock = items.valueAt(0)
            stringBuilder.append(item.value)
//            NumOfFaces.setText(stringBuilder.toString())
            val LP = "$stringBuilder "
//            sendData(PreProcess, LP)
            if ((LP.regionMatches(
                    3,
                    "21A-311.99 ",
                    3,
                    1
                ) || LP.regionMatches(
                    4,
                    "21 A-311.99 ",
                    4,
                    1
                )) && LP.regionMatches(
                    7,
                    "21A-311.99 ",
                    7,
                    1
                ) && LP.regionMatches(
                    10,
                    "21A-311.99 ",
                    10,
                    1
                )
            ) {
                withContext(Dispatchers.Main) {
                    licPlate.text = stringBuilder.toString()
                    DataStatus.text = stringBuilder.toString()
                }
            } else {
                mDetect.release()
            }
        }
    }

    private fun drawBox(Image: Mat, vertices: Array<Point?>): Mat {
        val boxContours: MutableList<MatOfPoint> = ArrayList()
        boxContours.add(MatOfPoint(*vertices))
        Imgproc.drawContours(Image, boxContours, 0, Scalar(200.0, 0.0, 0.0), 2)
        return Image
    }
}