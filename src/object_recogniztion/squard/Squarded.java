package object_recogniztion.squard;

import org.opencv.core.*;
import org.opencv.imgproc.Imgproc;

public class Squarded {
    public static final int HVS = 1;
    public static final int LAB = 2;
    public static final int YCrCb = 3;
    private Scalar minValues;
    private Scalar maxValues;

    public Mat changeTreshold(Mat img, int type){
        Mat blurred = new Mat();
        Mat ret = new Mat();
        Mat mask = new Mat();
        Mat morphOutput = new Mat();

        Imgproc.blur(img, blurred, new Size(10,10));

        if(type == HVS){
            Imgproc.cvtColor(blurred, ret, Imgproc.COLOR_BGR2HSV);
            minValues = new Scalar(0,0,153);
            maxValues = new Scalar(165,255,255);
        }

        if(type == LAB){
            Imgproc.cvtColor(blurred, ret, Imgproc.COLOR_BGR2Lab);
            minValues = new Scalar(80,9,188);
            maxValues = new Scalar(101,125,255);
        }

        if(type == YCrCb){
            Imgproc.cvtColor(blurred, ret, Imgproc.COLOR_BGR2YCrCb);
            minValues = new Scalar(0,107,113);
            maxValues = new Scalar(180,168,159);
            }

        // threshold
        Core.inRange(ret, minValues, maxValues, mask);

        /*
        // morphological operators
        // dilate with large element, erode with small ones
        Mat dilateElement = Imgproc.getStructuringElement(Imgproc.MORPH_RECT, new Size(24, 24));
        Mat erodeElement = Imgproc.getStructuringElement(Imgproc.MORPH_RECT, new Size(12, 12));

        Imgproc.erode(mask, morphOutput, erodeElement);
        Imgproc.erode(morphOutput, morphOutput, erodeElement);

        Imgproc.dilate(morphOutput, morphOutput, dilateElement);
        Imgproc.dilate(morphOutput, morphOutput, dilateElement);
*/
        return mask;
    }
}
