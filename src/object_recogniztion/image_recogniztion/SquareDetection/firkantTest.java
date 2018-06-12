
package object_recogniztion.image_recogniztion.SquareDetection;

import org.opencv.core.Mat;
import org.opencv.core.*;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;
import object_recogniztion.squard.Squarded;


public class firkantTest {


    public static void main(String[] args) throws Exception {

        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
        Squarded bw = new Squarded();
        squareDetection sd = new squareDetection();
        String filename = "/Users/alexandercarlsen/Desktop/qr.jpg";
        Mat hej = Imgcodecs.imread(filename);
        hej =  bw.changeTreshold(hej, 1);
        sd.findRectangle(hej);
        Imgcodecs.imwrite("/Users/alexandercarlsen/Desktop/qr2.jpg", hej);

        }
    }

