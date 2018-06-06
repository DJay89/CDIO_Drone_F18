package managers;

import de.yadrone.base.ARDrone;
import de.yadrone.base.IARDrone;
import de.yadrone.base.command.CommandManager;
import de.yadrone.base.video.ImageListener;

import java.awt.image.BufferedImage;


public class PilotManager {

    private IARDrone drone;
    private CommandManager cmd;
    private BufferedImage img;
    private final int SPEED = 25;

    public PilotManager(IARDrone drone) {
         this.drone = drone;
         this.cmd = this.drone.getCommandManager();
    }
    public PilotManager() {
        this.drone = new ARDrone();
        drone.start();
        this.cmd = this.drone.getCommandManager();
        droneCamCapture();

    }

    public void takeOffAndLand() {
         cmd.takeOff();
         cmd.waitFor(5000);
         cmd.landing();
    }

    public void droneCamCapture() {
        drone.getVideoManager().addImageListener(new ImageListener() {
            @Override
            public void imageUpdated(BufferedImage bufferedImage) {
                PilotManager.this.setImg(bufferedImage);
            }
        });
    }

    public void setImg(BufferedImage bufferedImage){
        this.img = bufferedImage;
    }

    public  BufferedImage getImg(){
        return this.img;
    }

    public void takeOff() {
        cmd.takeOff().doFor(5000);
    }

    public void land() {
        cmd.landing();
    }

    public void spinRight (long ms){
         cmd.spinRight(SPEED).doFor(ms);
    }

    public void spinLeft (long ms){
        cmd.spinLeft(SPEED).doFor(ms);
    }

    public void up (long ms) {
        cmd.up(SPEED).doFor(ms);
    }

    public void down (long ms) {
        cmd.down(SPEED).doFor(ms);
    }

    public void tiltLeft (long ms) {
        cmd.goLeft(SPEED).doFor(ms);
    }
    public void tiltRight (long ms) {
        cmd.goRight(SPEED).doFor(ms);
    }

    public void forward(long ms){
        cmd.forward(SPEED).doFor(ms);
    }

    public void backward(long ms){
        cmd.backward(SPEED).doFor(ms);
    }
    public void hover(long ms) {
        cmd.hover().doFor(ms);
    }
}
