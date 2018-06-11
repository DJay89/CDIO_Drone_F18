package algorithms;

import controller.Drone;

public class MasterAlgorihm implements Runnable{
    private Drone drone;
    private CenteringAlgorithm CA;
    private SearchAlgorithm SA;
    private Thread caThread, saThread;

    public MasterAlgorihm(Drone drone){
        this.drone = drone;
        this.CA = new CenteringAlgorithm(drone);
        this.SA = new SearchAlgorithm(drone);
        this.caThread = new Thread(CA);
        this.caThread = new Thread(SA);
    }

    public void run(){

    }

}
