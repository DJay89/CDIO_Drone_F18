package controller;

import de.yadrone.base.IARDrone;
import de.yadrone.base.command.CommandManager;
import de.yadrone.base.command.VideoChannel;
import de.yadrone.base.video.ImageListener;
import utils.distReturn;
import utils.imageReturn;
import org.opencv.core.Mat;
import org.opencv.videoio.VideoCapture;
import utils.Utils;
import java.awt.image.BufferedImage;

public class Drone implements IDrone, Runnable {

    //Drone Movement Variables
    private IARDrone drone;
    private CommandManager cmd;
    private final int SPEED = 25;

    //Return values for Image Recognition
    private imageReturn retValues;
    private distReturn distValues;
    //Camera Settings
    public VideoCapture capture = new VideoCapture();
    private static int cameraId = 0;
    private BufferedImage img;
    private Boolean debug;

    public int getSPEED() {
        return SPEED;
    }

    public synchronized imageReturn getRetValues(){
        return this.retValues;
    }
    public synchronized void setRetValues(imageReturn ir){
        this.retValues = ir;
    }

    public Drone(IARDrone drone, Boolean debug) {
        this.drone = drone;
        this.debug = debug;
        retValues = new imageReturn();
        distValues = new distReturn();

        if(debug){
            System.out.println("Starting Web cam");
            initWebcam();
        }
        else{
            System.out.println("Starting drone camera");
            this.cmd = this.drone.getCommandManager();
            droneCamCapture();
        }
        System.out.println("Done constructing the drone");

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

    //used for webcam stream
    @Override
    public void run(){
        while(!Thread.interrupted()){
            Mat frame = new Mat();
            if(capture.isOpened()){
                Drone.this.capture.read(frame);
                if(!frame.empty()){
                    Drone.this.setImg(Utils.mat2BufferedImage(frame));
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

    //use for drone camera stream
    @Override
    public void droneCamCapture() {
        drone.getVideoManager().addImageListener(new ImageListener() {
                @Override
                public void imageUpdated(BufferedImage bufferedImage) { Drone.this.setImg(bufferedImage); }
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

    public void setDistValues(distReturn distValues) {
        this.distValues = distValues;
    }

    public distReturn getDistValues() {
        return this.distValues;
    }
}
