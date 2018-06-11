package algorithms;

import controller.Drone;

import java.awt.image.BufferedImage;

public class MasterAlgorihm implements Runnable{
    private Drone drone;
    private CenteringAlgorithm CA;
    private SearchAlgorithm SA;
    public Thread caThread, saThread;
    private Boolean ifStart = false;

    public MasterAlgorihm(Drone drone){
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
            if(bi == null) {
                System.out.println(" suck mig");
            }
        }
    }

}
