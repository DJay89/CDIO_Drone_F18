package object_recogniztion.misc;

import org.opencv.core.*;
import org.opencv.imgproc.Imgproc;

import java.util.ArrayList;
import java.util.List;

public class RedRingFinder {

    public Mat findRedRing(Mat frame) {

        Mat hsvImage = new Mat();
        Mat mask = new Mat();

        /*
        Setting the values to search for our colour red.
        Our Scalar ranges from 0-180, 0-255, 0-255.
         */
        Scalar minValues = new Scalar(0, 144, 124);
        Scalar maxValues = new Scalar(18, 255, 255);
        Core.inRange(hsvImage, minValues, maxValues, mask); //Doesn't recognise this method.

        frame = drawRing(mask, frame);

        return frame;
    }

    public Mat drawRing(Mat maskedImage, Mat frame) {

        // init
        List<MatOfPoint> contours = new ArrayList<>();
        Mat hierarchy = new Mat();
        Mat circles = new Mat();

        // find contours
        Imgproc.findContours(maskedImage, contours, hierarchy, Imgproc.RETR_CCOMP, Imgproc.CHAIN_APPROX_SIMPLE);

        Imgproc.medianBlur(maskedImage, maskedImage, 5);

        Imgproc.HoughCircles(maskedImage, circles, Imgproc.HOUGH_GRADIENT, 1.0,
                (double) maskedImage.rows() / 20, // change this value to detect circles with different distances to each other
                100.0, 30.0, searchSpectrumMIN(), searchSpectrumMAX());

        for (int x = 0; x < circles.cols(); x++) {
            double[] c = circles.get(0, x);
            Point center = new Point(Math.round(c[0]), Math.round(c[1]));
            // circle center
            Imgproc.circle(frame, center, 1, new Scalar(0, 100, 100), 3, 8, 0);
            // circle outline
            int radius = (int) Math.round(c[2]);
            Imgproc.circle(frame, center, radius, new Scalar(255, 0, 255), 3, 8, 0);
        }

        System.out.println("DrawRing Object text");

        return frame;
    }

    private int searchSpectrumMIN () {
        return 100;
    }

    private int searchSpectrumMAX () {
        return 100;
    }

}
