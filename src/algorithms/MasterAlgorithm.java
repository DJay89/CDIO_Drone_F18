package algorithms;

import controller.Drone;
import java.awt.image.BufferedImage;

public class MasterAlgorithm implements Runnable
{
    private final int QR_CODE_FOUND = 1;
    private Drone drone;
    private CenteringAlgorithm CA;
    private SearchAlgorithm SA;

    public MasterAlgorithm(Drone drone){
        this.drone = drone;
        this.CA = new CenteringAlgorithm(drone);
        this.SA = new SearchAlgorithm(drone);


    }

    public void run(){
        while ( !Thread.interrupted() )
        {
            BufferedImage bi = drone.getImg();
            if(bi != null) {
                    System.out.println("Starting Master Algorithm");
                    startMaster();
            }
        }
    }


    private void startMaster() {
        if ( SA.searchLvlZero(20000 ) == QR_CODE_FOUND )
        {
            CA.centerDroneOnQR();
        }
    }
}
