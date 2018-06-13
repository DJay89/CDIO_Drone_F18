package object_recogniztion.RingFinder;

import org.opencv.core.*;
import org.opencv.imgproc.Imgproc;
import utils.Utils;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class RedRingFinder {

    /*
    TODO
    Fine tune the spectrum analysis
    Figure out how to implement found function
    Set the function up with the algorithms NOTE this should not be implemented here!
     */

    Stack<Integer> stackX = new Stack<Integer>();
    Stack<Integer> stackY = new Stack<Integer>();

    private int x = 0;
    private int y = 0;
    private int stacSize = 30;

    public int getX() {
        return this.x;
    }

    public int getY() {
        return this.y;
    }

    public void emptyStack() {
        this.stackX.empty();
        this.stackY.empty();
    }

    public Mat findRedRing(BufferedImage img) {
        Mat frame = Utils.bufferedImageToMat(img);
        Mat blurredImage = new Mat();
        Mat hsvImage = new Mat();
        Mat mask = new Mat();

        Imgproc.blur(frame, blurredImage, new Size(7, 7));
        Imgproc.cvtColor(blurredImage, hsvImage, Imgproc.COLOR_BGR2HSV);

        /*
        Setting the values to search for our colour red.
        Our Scalar ranges from 0-180, 0-255, 0-255.
         */
        Core.inRange(hsvImage, new Scalar(0,100,100), new Scalar(10, 255,255), mask);
        Core.inRange(hsvImage, new Scalar(160,100,100), new Scalar(179,255,255), mask);

        frame = drawRing(mask, frame);

        double rows;
        double cols;

        Size size = frame.size();
        rows = size.height;
        cols = size.width;
        Point screencenter = new Point(cols/2, rows/2);

        //System.out.println(rows);
        //System.out.println(cols);
        //System.out.println(screencenter);

        return frame;
    }

    public Mat drawRing(Mat maskedImage, Mat frame) {

        // init
        List<MatOfPoint> contours = new ArrayList<>();
        Mat hierarchy = new Mat();
        Mat circles = new Mat();

        // find contours
        Imgproc.findContours(maskedImage, contours, hierarchy, Imgproc.RETR_CCOMP, Imgproc.CHAIN_APPROX_SIMPLE);

        Imgproc.medianBlur(maskedImage, maskedImage, 3);

        Imgproc.HoughCircles(maskedImage, circles, Imgproc.HOUGH_GRADIENT, 1.0,
                (double) maskedImage.rows() / 20, // change this value to detect circles with different distances to each other
                200.0, 20.0, 80, 400);

        for (int i = 0; i < circles.cols(); i++) {
            double[] c = circles.get(0, i);
            Point center = new Point(Math.round(c[0]), Math.round(c[1]));
            int x = (int) Math.round(c[0]);
            int y = (int) Math.round(c[1]);
            if (stackX.size() == stacSize){
                stackX.push(x);
                stackX.remove(stackX.firstElement());
                stackY.push(y);
                stackY.remove(stackY.firstElement());

            } else {
                stackX.push(x);
                stackY.push(y);
                System.out.println(stackX.size());
            }

            //Prints center coordinates
            //System.out.println(center);
            // circle center
            //Imgproc.circle(frame, center, 1, new Scalar(0, 100, 100), 3, 8, 0);
            // circle outline
            //int radius = (int) Math.round(c[2]);
            //Imgproc.circle(frame, center, radius, new Scalar(255, 0, 255), 3, 8, 0);
        }


        getCenterAvg();

        return frame;
    }

    private void getCenterAvg(){
        int sumX = 0;
        int sumY = 0;
        int sumXavg = 0;
        int sumYavg = 0;

        for (Integer item: stackX){
            sumX = sumX + item;
            sumXavg = sumX/stackX.size();
        }


        for (Integer item: stackY){
            sumY = sumY + item;
            sumYavg = sumY/stackY.size();
        }

        //System.out.println(sumXavg);
        //System.out.println(sumYavg);

        this.x = sumXavg;
        this.y = sumYavg;
    }


    public boolean foundRing() {
        // figure out when we are certain a ring has been found
        if(stackX.size() == stacSize && stackY.size() == stacSize)
        {
            return true;
        }
        return false;

    }

}
