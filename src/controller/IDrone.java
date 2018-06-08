package controller;

import java.awt.image.BufferedImage;

public interface IDrone {

    public int getSPEED();

    public void takeOffAndLand();

    public void takeOff();

    public void takeOff(long ms);

    public void land();

    public void spinRight (long ms);

    public void spinLeft (long ms);

    public void up (long ms);

    public void down (long ms);

    public void tiltLeft (long ms);

    public void tiltRight (long ms);

    public void forward(long ms);

    public void backward(long ms);

    public void hover(long ms);

    public void spin360(long ms);

    public void move3D (int speedX, int speedY, int speedZ, int speedSpin, long ms);

    public void droneCamCapture();

    public void setImg(BufferedImage bufferedImage);

    public  BufferedImage getImg();
}

