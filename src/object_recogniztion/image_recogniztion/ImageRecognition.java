package object_recogniztion.image_recogniztion;

import controller.Drone;
import object_recogniztion.image_recogniztion.SquareDetection.squareDetection;
import object_recogniztion.misc.ImageConverter;
import object_recogniztion.RingFinder.RedRingFinder;
import object_recogniztion.qr_scanner.QRscanner;
import org.opencv.core.Mat;

import java.awt.image.BufferedImage;

public class ImageRecognition implements IImageRecognition, Runnable {

    private Drone controller;

    private ImageManipulation imageManipulation;

    private ImageConverter imageConverter;
    private RedRingFinder ring;
    private QRscanner qr;
    private squareDetection sd;


    private Mat frame;

    public ImageRecognition(Drone droneController) {
        this.controller = droneController;
        this.imageManipulation = new ImageManipulation(droneController);
        this.frame = new Mat();
        this.ring = new RedRingFinder();
        this.imageConverter = new ImageConverter();
        this.qr = new QRscanner();
        this.sd = new squareDetection();

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
                Mat tempFrame = convertImage2Mat(controller.getImg());
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

                try {
                    sd.findRectangle(getFrame());
                } catch (Exception e) {
                    e.printStackTrace();
                }

                if(qr.decodeQR(getFrame())){
                    System.out.println(qr.get_qr_txt());
                }

                //qr.decodeQrWithFilters(getFrame());

                //ring.findRedRing(getFrame());
            }

        }
        System.err.println("Image recogniztion stopped.");
    }

    public QRscanner getqr() {
        return qr;
    }
}



