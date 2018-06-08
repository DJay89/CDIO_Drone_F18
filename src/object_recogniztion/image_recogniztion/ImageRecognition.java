package object_recogniztion.image_recogniztion;

import controller.Drone;
import managers.PilotManager;
import object_recogniztion.misc.ImageConverter;
import object_recogniztion.misc.RedRingFinder;
import object_recogniztion.qr_scanner.QRscanner;
import org.opencv.core.Mat;

import java.awt.image.BufferedImage;

public class ImageRecognition implements IImageRecognition, Runnable {

    /*
    TESTING
     */
    private Drone controller;
    //

    private ImageManipulation imageManipulation;

    private ImageConverter imageConverter;
    private RedRingFinder ring;
    private PilotManager pm;
    private QRscanner qr;

    private Mat frame;

    public ImageRecognition(Drone droneController) {
        this.controller = droneController;
        this.imageManipulation = new ImageManipulation(droneController);
        this.frame = new Mat();
        this.ring = new RedRingFinder();
        this.imageConverter = new ImageConverter();
        this.qr = new QRscanner();
    }

    public BufferedImage convertMat2BufferedImage(Mat frame) {
        return imageConverter.convertMat2BufferedImage(frame);
    }

    public synchronized Mat convertImage2Mat(BufferedImage bufferedImage) {
        return imageConverter.bufferedImageToMat(bufferedImage);
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
                Mat tempFrame = convertImage2Mat(pm.getImg());
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
                if(qr.decodeQR(getFrame())){
                    System.out.println(qr.get_qr_txt());
                    System.out.println("x: "+ qr.getX() + " y: "+qr.getY());
                }

                //qr.decodeQrWithFilters(getFrame());

            //    ring.findRedRing(getFrame());
            }

        }
        System.err.println("Image recogniztion stopped.");
    }
}



