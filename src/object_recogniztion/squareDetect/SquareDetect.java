package object_recogniztion.squareDetect;

import org.opencv.core.Mat;
import org.opencv.core.*;
import org.opencv.imgproc.*;
import java.util.List;
import java.util.ArrayList;
import org.opencv.core.Point;
import static org.opencv.imgproc.Imgproc.drawContours;


public class SquareDetect {

    public Mat findRectangle(Mat maskedImage, Mat src) throws Exception {
        Mat blurred = maskedImage.clone();
        Imgproc.medianBlur(maskedImage, blurred, 9);

        Mat gray0 = new Mat(blurred.size(), CvType.CV_8U),  gray = new Mat();

        ArrayList<MatOfPoint> contours = new ArrayList<>();

        ArrayList<Mat> blurredChannel = new ArrayList<>();
        blurredChannel.add(blurred);
        ArrayList<Mat> gray0Channel = new ArrayList<>();
        gray0Channel.add(gray0);

        MatOfPoint2f approxCurve;

        double maxArea = 0;
        int maxId = -1;


        for (int c = 0; c < maskedImage.channels(); c++) {
            int ch[] = { c, 0 };
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

                Imgproc.findContours(gray, contours, new Mat(),
                        Imgproc.RETR_EXTERNAL, Imgproc.CHAIN_APPROX_SIMPLE);

                for (MatOfPoint contour : contours) {
                    MatOfPoint2f temp = new MatOfPoint2f(contour.toArray());

                    double area = Imgproc.contourArea(contour);
                    approxCurve = new MatOfPoint2f();
                    Imgproc.approxPolyDP(temp, approxCurve,
                            Imgproc.arcLength(temp, true) * 0.02, true);
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
                        }
                    }
                }
            }
        }


        if (maxId >= 0) {


            double aspectRatioMin = 1.25;
            double aspectRatioMax = 1.35;

            for (int i = 0; i < contours.size(); i ++ ) {

                if (Imgproc.contourArea(contours.get(i)) > 100 ) {
                    Rect rect = Imgproc.boundingRect(contours.get(i));
                    //  if ((rect.height > 35 && rect.height < 60) && (rect.width > 35 && rect.width < 60))
                    // System.out.println("rect height = " + rect.height + "rect width" + rect.width);
                    //System.out.println("Aspect Ratio = " + (double) rect.height / (double) rect.width);

                    if ( (( (double) rect.height / (double) rect.width ) > aspectRatioMin ) && ( (double) rect.height / (double) rect.width ) < aspectRatioMax )

                    {
                        //Imgproc.rectangle(src, new Point(rect.x,rect.y), new Point(rect.x+rect.width,rect.y+rect.height),new Scalar(0,0,255));
                        drawContours(src, contours,  maxId, new Scalar(0, 255, 0), 8);
                    }
                    //  System.out.print("RECT DETECTED\n");
                    //System.out.print("NUMBERS OF CONTOURS FOUND = " + contours.size());

                    //  for ( int i = 0; i < contours.size(); i++ )

                    //  System.out.print("CONTOUR " + i +  " coordinate" + contours.get(i));
                }
            }
        }
        return src;
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
