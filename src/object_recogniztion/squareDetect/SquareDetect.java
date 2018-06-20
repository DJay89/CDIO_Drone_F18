package object_recogniztion.squareDetect;


import org.opencv.core.Mat;
import org.opencv.core.*;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.*;

import java.util.*;

import org.opencv.core.Point;

import static org.opencv.imgproc.Imgproc.contourArea;
import static org.opencv.imgproc.Imgproc.drawContours;


public class SquareDetect {

    public Mat dst;
    public final boolean QR_FOUND = true;
    public final boolean NO_QR_FOUND = false;
    Point centerOfRect = new Point();



    public double knownQrWidth = 30;
    // distance in centimeter
    public double knownDistance = 96;
    public double markerWidth = 150;
    public double markerHeight = 210;
    public double focalLength;
    public double distanceToQr;

    public int x = 0;
    public int y = 0;

    private int distance;
    private boolean distanceFound;

    public boolean getFound() {return this.distanceFound;}
    public int getDistance() {return this.distance;}

    public boolean findQrCenter(Mat maskedImage) {
        Mat blurred = maskedImage.clone();
        Imgproc.medianBlur(maskedImage, blurred, 9);

        Mat gray0 = new Mat(blurred.size(), CvType.CV_8U), gray = new Mat();

        ArrayList<MatOfPoint> contours = new ArrayList<>();

        ArrayList<Mat> blurredChannel = new ArrayList<>();
        blurredChannel.add(blurred);
        ArrayList<Mat> gray0Channel = new ArrayList<>();
        gray0Channel.add(gray0);

        MatOfPoint2f approxCurve;

        ArrayList<MatOfPoint> neededContours = new ArrayList<>();

        double maxArea = 0;
        int maxId = -1;


        for (int c = 0; c < 3; c++) {
            int ch[] = {c, 0};
            Core.mixChannels(blurredChannel, gray0Channel, new MatOfInt(ch));

            int thresholdLevel = 1;
            for (int t = 0; t < thresholdLevel; t++) {
                if (t == 0) {
                    Imgproc.Canny(gray0, gray, 10, 20, 3, true); // true ?
                    Imgproc.dilate(gray, gray, new Mat(), new Point(-1, -1), 1); // 1
                    // Imgproc.HoughLines(gray0, gray, 1, Math.PI/180, 100);

                } else {
                    Imgproc.adaptiveThreshold(gray0, gray, thresholdLevel,
                            Imgproc.ADAPTIVE_THRESH_GAUSSIAN_C,
                            Imgproc.THRESH_BINARY,
                            (maskedImage.width() + maskedImage.height()) / 200, t);
                }

                dst = gray;

                Imgproc.findContours(gray, contours, new Mat(),
                        Imgproc.RETR_LIST, Imgproc.CHAIN_APPROX_SIMPLE);

                for (MatOfPoint contour : contours) {
                    MatOfPoint2f temp = new MatOfPoint2f(contour.toArray());

                    double area = Imgproc.contourArea(contour);
                    approxCurve = new MatOfPoint2f();
                    Imgproc.approxPolyDP(temp, approxCurve,
                            Imgproc.arcLength(temp, true) * 0.02, true);

                    // JAJAJA //
                    if (approxCurve.total() == 4 && area >= maxArea) {
                        double maxCosine = 0;
                        //System.out.print("CONTOUR LENGTH = " + Imgproc.arcLength(temp, true));
                        List<Point> curves = approxCurve.toList();
                        for (int j = 2; j < 5; j++) {

                            double cosine = Math.abs(angle(curves.get(j % 4),
                                    curves.get(j - 2), curves.get(j - 1)));
                            maxCosine = Math.max(maxCosine, cosine);
                        }

                        if (maxCosine < 0.3) {
                            maxArea = area;
                            maxId = contours.indexOf(contour);
                            // neededContours.add(contours.get(contours.indexOf(contour)));
                        }
                    }
                }
            }


            if (maxId >= 0) {


                Rect rect = Imgproc.boundingRect(contours.get(maxId));
                Point centerOfRect = new Point();


                double aspectRatioMin = 1.3;
                double aspectRatioMax = 1.6;
                 if ((rect.height > 210*0.05 && rect.width > 150 * 0.05) && (rect.height < 210 * 1.2 && rect.width < 150 * 1.2))
                {
                    if ((((double) rect.height / (double) rect.width) > aspectRatioMin) && (((double) rect.height / (double) rect.width) < aspectRatioMax)) {

                        this.x = rect.x + rect.width / 2;
                        this.y = rect.y + rect.height / 2;

                                        //Imgproc.circle(src, centerOfRect, 20, new Scalar(255, 0, 0), 8);

                        //Imgproc.rectangle(src, rect.tl(), rect.br(), new Scalar(0, 255, 0, .8)/*😎*/, 2);

                        focalLength = focalLength(markerWidth, knownDistance, knownQrWidth);
                        distanceToQr = distanceToQr(knownQrWidth, focalLength, rect.width);
                        System.out.println("distance " + distanceToQr);
                        this.distance = (int) distanceToQr;
                        this.distanceFound = QR_FOUND;
                        return QR_FOUND;
                    }
                }
                else {
                        //System.out.println("NO QR FOUND");
                       return NO_QR_FOUND;
                }
            }
        }
        System.out.println("NO QR FOUND");
        return NO_QR_FOUND;
    }

    private double angle(Point p1, Point p2, Point p0) {
        double dx1 = p1.x - p0.x;
        double dy1 = p1.y - p0.y;
        double dx2 = p2.x - p0.x;
        double dy2 = p2.y - p0.y;
        return (dx1 * dx2 + dy1 * dy2)
                / Math.sqrt((dx1 * dx1 + dy1 * dy1) * (dx2 * dx2 + dy2 * dy2)
                + 1e-10);
    }

    public int getCenterOfRectX(){

        return this.x;
    }

    public int getCenterOfRectY(){

        return this.y;
    }


    private double distanceToQr( double knownWidth, double focalLength, double perceivedWidth ){
        return ( knownWidth * focalLength ) / perceivedWidth;
    }

    private double focalLength(double marker, double knownDistance, double knownQrWidth ) {
        String filename = "";
        Mat imageWithMarker = Imgcodecs.imread(filename);
        //double marker = findMarker( imageWithMarker );

        return ( marker * knownDistance ) / knownQrWidth;
    }

    public double findMarker( Mat image ) {
        ///// find bredden af qr koden på en afstand af 2 meter vha. tests derefter fastsættes værdien ///
        findQrCenter( image );

        return 0.0;
    }

    public int getX() {
        return this.x;
    }

    public int gety() {
        return this.y;
    }
}

