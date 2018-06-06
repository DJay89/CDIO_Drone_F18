package object_recogniztion.image_recogniztion;

import org.opencv.core.Mat;

import java.awt.image.BufferedImage;

public interface IImageRecognition {

    BufferedImage convertMat2BufferedImage(Mat frame);
    Mat convertImage2Mat(BufferedImage Bi);

    Mat getFrame();
}
