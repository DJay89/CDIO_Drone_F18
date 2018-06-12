package object_recogniztion.RingFinder;

import object_recogniztion.image_recogniztion.ImageRecognition;
import org.opencv.core.*;
import org.opencv.imgproc.Imgproc;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class RedRingFinder {

    private ImageRecognition image;

    Stack<Integer> stackX = new Stack<Integer>();
    Stack<Integer> stackY = new Stack<Integer>();

    public RedRingFinder (ImageRecognition image) {
        this.image = image;
    }

    public Mat findRedRing(Mat frame) {

        System.out.println("Ring");
        Mat blurredImage = new Mat();
        Mat hsvImage = new Mat();
        Mat mask = new Mat();

        Imgproc.blur(frame, blurredImage, new Size(7, 7));
        Imgproc.cvtColor(blurredImage, hsvImage, Imgproc.COLOR_BGR2HSV);

        /*
        Setting the values to search for our colour red.
        Our Scalar ranges from 0-180, 0-255, 0-255.
         */
        Scalar minValues = new Scalar(0, 144, 124);
        Scalar maxValues = new Scalar(18, 255, 255);

        Core.inRange(hsvImage, minValues, maxValues, mask); //Doesn't recognise this method.

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
                100.0, 30.0, searchSpectrumMIN(), searchSpectrumMAX());

        for (int i = 0; i < circles.cols(); i++) {
            double[] c = circles.get(0, i);
            Point center = new Point(Math.round(c[0]), Math.round(c[1]));
            int x = (int) Math.round(c[0]);
            int y = (int) Math.round(c[1]);
            if (stackX.size() == 2){
                stackX.push(x);
                stackX.remove(stackX.firstElement());
                stackY.push(y);
                stackY.remove(stackY.firstElement());

            } else {
                stackX.push(x);
                stackY.push(y);
            }

            //Prints center coordinates
            System.out.println(center);
            // circle center
            Imgproc.circle(frame, center, 1, new Scalar(0, 100, 100), 3, 8, 0);
            // circle outline
            int radius = (int) Math.round(c[2]);
            Imgproc.circle(frame, center, radius, new Scalar(255, 0, 255), 3, 8, 0);
        }

        System.out.println("DrawRing Object text");

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

        System.out.println("sumX: "+sumX);
        System.out.println("avgX: "+sumXavg);

        for (Integer item: stackY){
            sumY = sumY + item;
            sumYavg = sumY/stackY.size();
        }

        System.out.println("sumY: "+sumY);
        System.out.println("avgy: "+sumYavg);

        image.setRingAvg(sumXavg, sumYavg);
    }

    public void emptyRingPoint(){
        stackX.empty();
        stackY.empty();
    }

    private int searchSpectrumMIN () {
        return 120;
    }

    private int searchSpectrumMAX () {
        return 160;
    }

}
