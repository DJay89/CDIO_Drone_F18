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
        this.imageConverter = new ImageConverter();

        this.ring = new RedRingFinder();
        this.frame = new Mat();
    }

    public BufferedImage convertMat2BufferedImage(Mat frame) {
        return imageConverter.convertMat2BufferedImage(frame);
    }

    public Mat convertImage2Mat(BufferedImage bufferedImage) {
    	System.out.println("lol");
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
    	System.out.println("Image recogniztion started.");

        while(!Thread.interrupted()) {

            try {
				System.out.println("test 1");

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

			System.out.println("test 2");
			boolean lol =frame.empty();
			System.out.println("test 2");
            if (!frame.empty()) {
            	
				System.out.println("test 3");
                ring.findRedRing(getFrame());
				System.out.println("test 3");
            }
            else
            {
				System.out.println("test 4");
            }

        }
        System.err.println("Image recogniztion stopped.");
    }
}



