package object_recogniztion.misc;

import org.opencv.core.*;
import org.opencv.imgproc.Imgproc;

import java.util.ArrayList;
import java.util.List;

public class RedRingFinder {

    public Mat findRedRing(Mat frame) {
    	System.out.println(" red ring of death debug 1");


        Mat hsvImage = new Mat();
    	System.out.println(" red ring of death debug 2");
        Mat mask = new Mat();
    	System.out.println(" red ring of death debug 3");

        /*
        Setting the values to search for our colour red.
        Our Scalar ranges from 0-180, 0-255, 0-255.
         */
    	System.out.println(" red ring of death debug 4");
        Scalar minValues = new Scalar(0, 144, 124);
    	System.out.println(" red ring of death debug 5");
        Scalar maxValues = new Scalar(18, 255, 255);
    	System.out.println(" red ring of death debug 6");
        //Core.inRange(hsvImage, minValues, maxValues, mask); //Doesn't recognise this method.
    	System.out.println(" red ring of death debug 7");

        frame = drawRing(mask, frame);
    	System.out.println(" red ring of death debug 8");

        return frame;
    }

    public Mat drawRing(Mat maskedImage, Mat frame) {

    	System.out.println(" drw ring debug 1");

        // init
        List<MatOfPoint> contours = new ArrayList<>();
    	System.out.println(" drw ring debug 2");
        Mat hierarchy = new Mat();
    	System.out.println(" drw ring debug 3");
        Mat circles = new Mat();
    	System.out.println(" drw ring debug 4");

        // find contours
        Imgproc.findContours(maskedImage, contours, hierarchy, Imgproc.RETR_CCOMP, Imgproc.CHAIN_APPROX_SIMPLE);
    	System.out.println(" drw ring debug 5");

        Imgproc.medianBlur(maskedImage, maskedImage, 5);
    	System.out.println(" drw ring debug 6");

        Imgproc.HoughCircles(maskedImage, circles, Imgproc.HOUGH_GRADIENT, 1.0,
                (double) maskedImage.rows() / 20, // change this value to detect circles with different distances to each other
                100.0, 30.0, searchSpectrumMIN(), searchSpectrumMAX());

    	System.out.println(" drw ring debug 7");
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
