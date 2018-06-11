package algorithms;


import controller.Drone;
import object_recogniztion.image_recogniztion.ImageRecognition;

public class SearchAlgorithm implements Runnable{
    private Drone drone;
    private ImageRecognition IR;
    private Thread irThread;

    public SearchAlgorithm(Drone drone){
        this.drone = drone;
        this.IR = new ImageRecognition(drone);
        this.irThread = new Thread(IR);
    }

    @Override
    public void run() {

    }
}
