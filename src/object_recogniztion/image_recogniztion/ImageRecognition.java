package object_recogniztion.image_recogniztion;

import object_recogniztion.misc.ImageConverter;
import object_recogniztion.misc.RedRingFinder;
import object_recogniztion.video_test.VideoTest;
import org.opencv.core.Mat;

import java.awt.image.BufferedImage;

public class ImageRecognition implements IImageRecognition, Runnable {

    /*
    TESTING
     */
    private VideoTest controller;
    //

    private ImageManipulation imageManipulation;

    private ImageConverter imageConverter;
    private RedRingFinder ring;

    private Mat frame;

    //Change VideoTest input to DroneController once its made.
    public ImageRecognition(VideoTest droneController) {
        this.controller = droneController;
        this.imageManipulation = new ImageManipulation(droneController);

        this.ring = new RedRingFinder();
    }

    public BufferedImage convertMat2BufferedImage(Mat frame) {
        return imageConverter.convertMat2BufferedImage(frame);
    }

    public Mat convertImage2Mat(BufferedImage bufferedImage) {
        return imageConverter.convertImage2Mat(bufferedImage);
    }

    public Mat getFrame() {
        return frame;
    }

    public void setFrame(Mat frame) {
        this.frame = frame;
    }

    @Override
    public void run() {

        while(!Thread.interrupted()) {

            try {
                Mat tempFrame = convertImage2Mat(controller.getbufImg());
                setFrame(tempFrame);
            } catch (NullPointerException e) {
                System.err.println("No picture received. Will try again in 50ms");
                e.printStackTrace();

                try {
                    Thread.sleep(500);
                } catch (InterruptedException e1) {
                    return;
                }
            }

            if (!frame.empty()) {
                ring.findRedRing(getFrame());
            }

        }
        System.err.println("Image recogniztion stopped.");
    }
}



