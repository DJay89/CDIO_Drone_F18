package object_recogniztion.image_recogniztion.squareDetect;

import org.opencv.core.*;
import org.opencv.imgproc.Imgproc;

import java.util.ArrayList;
import java.util.List;

public class SquareDetectTst {

    public SquareDetectTst() {

    }

    public Mat dst;

    public Mat findRectangle(Mat maskedImage, Mat src)  {
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


        for (int c = 0; c < maskedImage.channels(); c++) {
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
                        Imgproc.RETR_EXTERNAL, Imgproc.CHAIN_APPROX_SIMPLE);

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


                double aspectRatioMin = 1.2;
                double aspectRatioMax = 1.4;
                Mat frameCut;
                Point centerOfRect = new Point();
                Imgproc.circle(src, new Point(620, 140), 20, new Scalar(255, 0, 0), 8);
                Imgproc.rectangle(src, new Point(620, 500), new Point(700, 300), new Scalar(0, 0, 255), 8);

                // for (int i = 0; i < neededContours.size(); i++) {
                Rect rect = Imgproc.boundingRect(contours.get(maxId));
                //System.out.println("Aspect Ratio = " + (double) rect.height / (double) rect.width);
                //System.out.println("rect height = " + rect.height + "rect width" + rect.width);


                if ((((double) rect.height / (double) rect.width) > aspectRatioMin) && ((double) rect.height / (double) rect.width) < aspectRatioMax)

                {

                    //          if ((rect.height > 100) && (rect.width > 50)) {
                    //System.out.println("INSIDE PARAMETERS" + "rect height = " + rect.height + "rect width" + rect.width);
                    //System.out.println("Aspect Ratio INSIDE PARAMETERS = " + (double) rect.height / (double) rect.width);
                    centerOfRect.x = rect.x + rect.width / 2;
                    centerOfRect.y = rect.y + rect.height / 2;
                            /*
                           for ( int j = 0; j < neededContours.get(i).toList().size(); j++ )
                           {
                              System.out.println("CONT COORDINATE = " + neededContours.get(i).toList().get(j));
                              Imgproc.circle(src, neededContours.get(i).toList().get(j), 20, new Scalar(0, 255, 0), 8) ;
                           }
*/
                    //   System.out.print("NUMBERS OF CONTOURS FOUND = " + neededContours.size());

                    Imgproc.circle(src, centerOfRect, 20, new Scalar(255, 0, 0), 8);
                    //Imgproc.rectangle(src, new Point(rect.x, rect.y), new Point(rect.x + rect.width, rect.y + rect.height), new Scalar(255, 0, 0), 8);
                    //Imgproc.circle(src, rect.br(), 20, new Scalar(0, 0, 255), 8);
                    Imgproc.drawContours(src, contours, maxId, new Scalar(255, 0, 0,
                            .8), 8);

                    //return "QR detected";
                    return src;
                }
                else {
                    //   return "Scanning for QR";
                }
            }
        }
        return src;
        // return "Scanning for QR";
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

}
