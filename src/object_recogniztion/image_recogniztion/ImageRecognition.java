package object_recogniztion.image_recogniztion;

import controller.Drone;
import javafx.scene.image.Image;
import object_recogniztion.image_recogniztion.SquareDetection.squareDetection;
import object_recogniztion.misc.ImageConverter;
import object_recogniztion.RingFinder.RedRingFinder;
import object_recogniztion.qr_scanner.QRscanner;
import object_recogniztion.squard.Squarded;
import object_recogniztion.video_test.VideoDisplay;
import object_recogniztion.video_test.VideoDisplayController;
import object_recogniztion.squard.Squarded;
import org.opencv.core.Mat;
import utils.Utils;

import java.awt.image.BufferedImage;

public class ImageRecognition implements IImageRecognition, Runnable {

    private Drone controller;

    private ImageManipulation imageManipulation;

    private ImageConverter imageConverter;
    private RedRingFinder ring;
    private QRscanner qr;
    private Boolean devMode;
    private VideoDisplayController VDC;
    private Mat frame;
    private squareDetection sd;
    private Squarded bw;

    public ImageRecognition(Drone droneController) {
        this.controller = droneController;
        this.imageManipulation = new ImageManipulation(droneController);
        this.frame = new Mat();
        this.ring = new RedRingFinder();
        this.imageConverter = new ImageConverter();
        this.qr = new QRscanner();
    }

    public ImageRecognition(Drone droneController, VideoDisplayController VDC) {
        this.controller = droneController;
        this.imageManipulation = new ImageManipulation(droneController);
        this.frame = new Mat();
        this.ring = new RedRingFinder();
        this.imageConverter = new ImageConverter();
        this.qr = new QRscanner();
        this.VDC = VDC;
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
                Mat tempFrame;
                if(VDC.devMode){
                    tempFrame = VDC.grabFrame();
                }
                else{
                    tempFrame = convertImage2Mat(controller.getImg());
                }
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
                    Mat filteredFrame = bw.changeTreshold(frame, 2);
                    sd.findRectangle(filteredFrame);
                    Image newFrame = Utils.mat2Image(filteredFrame);
                    VDC.updateImageView(VDC.currentFrame, newFrame);

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



