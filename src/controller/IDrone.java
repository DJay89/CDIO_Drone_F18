package controller;

public interface IDrone {

    public void takeOffAndLand();

    public void takeOff();

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
}

