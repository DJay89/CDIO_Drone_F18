package object_recogniztion.misc;

import org.opencv.core.Mat;
import org.opencv.core.MatOfFloat;
import org.opencv.core.MatOfInt;
import org.opencv.imgproc.Imgproc;

import java.awt.image.BufferedImage;
import java.util.Arrays;

public class OpenCVCompareImg {

    ImageConverter IC;

    public void compareImages (BufferedImage img1, BufferedImage img2) {

        Mat mat_img1 = IC.convertImage2Mat(img1);
        Mat mat_img2 = IC.convertImage2Mat(img2);

        Mat hist_1 = new Mat();
        Mat hist_2 = new Mat();

        MatOfFloat ranges = new MatOfFloat(0f,256f);
        MatOfInt histSize = new MatOfInt(25);

        Imgproc.calcHist(Arrays.asList(mat_img1), new MatOfInt(0),
                new Mat(), hist_1, histSize, ranges);
        Imgproc.calcHist(Arrays.asList(mat_img2), new MatOfInt(0),
                new Mat(), hist_2, histSize, ranges);

        double res = Imgproc.compareHist(hist_1, hist_2, Imgproc.CV_COMP_CORREL);
        Double d = new Double(res*100);
        System.out.println(d.intValue());
    }

}
