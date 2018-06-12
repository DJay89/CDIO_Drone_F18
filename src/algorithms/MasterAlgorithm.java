package algorithms;

import controller.Drone;
import java.awt.image.BufferedImage;

public class MasterAlgorithm implements Runnable
{
    private Drone drone;
    private CenteringAlgorithm CA;
    private SearchAlgorithm SA;
    public Thread caThread, saThread;

    public AMasterAlgorithm(Drone drone){
        this.drone = drone;
        this.CA = new CenteringAlgorithm(drone);
        this.SA = new SearchAlgorithm(drone);
        this.caThread = new Thread(CA);
        this.saThread = new Thread(SA);

    }

    public void run(){
        while (!Thread.interrupted())
        {
            BufferedImage bi = drone.getImg();
            if(bi != null) {
                if(saThread.getState().equals(Thread.State.NEW)){
                    System.out.println("starting thread");
                    saThread.start();
                }
            }
        }
    }

}
