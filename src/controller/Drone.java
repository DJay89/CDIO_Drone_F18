package controller;

import de.yadrone.base.IARDrone;
import de.yadrone.base.command.CommandManager;
import de.yadrone.base.command.VideoChannel;
import de.yadrone.base.video.ImageListener;
import javafx.scene.image.Image;
import object_recogniztion.imageReturn;
import object_recogniztion.video_test.VideoDisplay;
import org.opencv.core.Mat;
import org.opencv.videoio.VideoCapture;
import utils.Utils;

import javax.swing.text.DefaultEditorKit;
import java.awt.image.BufferedImage;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class Drone implements IDrone, Runnable {

    private IARDrone drone;
    private CommandManager cmd;
    private final int SPEED = 25;
    public imageReturn retValues;
    private Runnable frameGrabber;

    //cam
    public VideoCapture capture = new VideoCapture();
    private static int cameraId = 0;
    private Boolean debug = false;
    private BufferedImage img;
    private ScheduledExecutorService timer;

    public int getSPEED() {
        return SPEED;
    }

    public Drone(IARDrone drone, Boolean debug) {
        this.drone = drone;
        this.debug = debug;
        retValues = new imageReturn();

        if(debug){
            System.out.println("Starting Web cam");
            initWebcam();
        }
        else{
            System.out.println("Starting drone camerae");
            this.cmd = this.drone.getCommandManager();
            droneCamCapture();
        }
    }


    public void toggleCamera() {
        if (this.cmd != null) {
            this.cmd.setVideoChannel(VideoChannel.NEXT);
        }
    }
    public void initWebcam(){
        this.capture.open(cameraId);
        System.out.println("start cam");
    }

    @Override
    public void run(){
        while(!Thread.interrupted()){
            Mat frame = new Mat();
            if(capture.isOpened()){
                Drone.this.capture.read(frame);
                if(!frame.empty()){
                    Drone.this.setImg(Utils.Mat2BufferedImage(frame));
                }
            }
            else capture.open(cameraId);
        }
    }


    @Override
    public void takeOffAndLand() {
         cmd.takeOff();
         cmd.waitFor(5000);
         cmd.landing();
    }

    @Override
    public void takeOff() {
        cmd.takeOff().doFor(2000);
    }

    @Override
    public void takeOff(long ms) {
        cmd.takeOff().doFor(ms);
    }

    @Override
    public void land() {
        cmd.landing();
    }

    @Override
    public void spinRight (long ms){
        cmd.spinRight(SPEED).doFor(ms);
    }

    @Override
    public void spinLeft (long ms){
        cmd.spinLeft(SPEED).doFor(ms);
    }

    @Override
    public void up (long ms) {
        cmd.up(SPEED).doFor(ms);
    }

    @Override
    public void down (long ms) {
        cmd.down(SPEED).doFor(ms);
    }

    @Override
    public void tiltLeft (long ms) {
        cmd.goLeft(SPEED).doFor(ms);
    }

    @Override
    public void tiltRight (long ms) {
        cmd.goRight(SPEED).doFor(ms);
    }

    @Override
    public void forward(long ms){
        cmd.forward(SPEED).doFor(ms);
    }

    @Override
    public void backward(long ms){
        cmd.backward(SPEED).doFor(ms);
    }

    @Override
    public void hover(long ms) {
        cmd.hover().doFor(ms);
    }

    @Override
    public void spin360(long ms) {
        //TODO: some method
    }

    @Override
    public void move3D(int speedX, int speedY, int speedZ, int speedSpin, long ms) {
        cmd.move(speedX, speedY, speedZ, speedSpin).doFor(ms);
    }

    @Override
    public void droneCamCapture() {
        drone.getVideoManager().addImageListener(new ImageListener() {
                @Override
                public void imageUpdated(BufferedImage bufferedImage)
                {
                    Drone.this.setImg(bufferedImage);
                }
            });
    }

    @Override
    public synchronized void setImg(BufferedImage bufferedImage) {
        this.img = bufferedImage;
    }

    @Override
    public synchronized BufferedImage getImg() {
        return this.img;
    }
}
