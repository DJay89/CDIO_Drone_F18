package controller;

import de.yadrone.base.IARDrone;
import de.yadrone.base.command.CommandManager;
import de.yadrone.base.video.ImageListener;
import managers.PilotManager;

import java.awt.image.BufferedImage;

public class Drone implements IDrone {

    private IARDrone drone;
    private CommandManager cmd;
    private BufferedImage img;

    public int getSPEED() {
        return SPEED;
    }

    private final int SPEED = 25;



    public Drone(IARDrone drone) {
         this.drone = drone;
         this.cmd = this.drone.getCommandManager();
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
            public void imageUpdated(BufferedImage bufferedImage) {
                Drone.this.setImg(bufferedImage);
            }
        });
    }

    @Override
    public void setImg(BufferedImage bufferedImage) {
        this.img = bufferedImage;
    }

    @Override
    public BufferedImage getImg() {
        return this.img;
    }
}
