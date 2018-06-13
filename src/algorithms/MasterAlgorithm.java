package algorithms;

import controller.Drone;
import java.awt.image.BufferedImage;

public class MasterAlgorithm implements Runnable
{
    //objects
    private Drone drone;
    private CenteringAlgorithm CA;
    private SearchAlgorithm SA;
    public Thread caThread, saThread;

    //objects init
    public MasterAlgorithm(Drone drone){
        this.drone = drone;
        this.CA = new CenteringAlgorithm(drone);
        this.SA = new SearchAlgorithm(drone);
        this.caThread = new Thread(CA);
        this.saThread = new Thread(SA);

    }

    //Master thread
    public void run(){
        while (!Thread.interrupted())
        {
            BufferedImage bi = drone.getImg();
            if(bi != null) {
                if(saThread.getState().equals(Thread.State.NEW)){
                    System.out.println("starting thread");
                    saThread.start();
                }
                /* Might be useful
                try {
                    saThread.join();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                if(caThread.getState().equals(Thread.State.NEW)){
                    System.out.println("starting thread");
                    caThread.start();
                }
                try {
                    caThread.join();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                */

            }
        }
    }

}
