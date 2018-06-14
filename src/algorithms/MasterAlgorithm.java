package algorithms;

import controller.Drone;

import java.awt.image.BufferedImage;

public class MasterAlgorithm implements Runnable {
    //objects
    private Drone drone;
    private CenteringAlgorithm CA;
    private SearchAlgorithm SA;

    private final int QR_CODE_FOUND = 1;

    //objects init
    public MasterAlgorithm(Drone drone) {
        this.drone = drone;
        this.CA = new CenteringAlgorithm(drone);
        this.SA = new SearchAlgorithm(drone);

    }

    //Master thread
    public void run() {
        while (!Thread.interrupted()) {
            BufferedImage bi = drone.getImg();
            if (bi != null) {
                System.out.println("Starting Master Algorithm");
                algorithm();
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

    private void algorithm() {
        if (SA.searchLvlZero(20000) == QR_CODE_FOUND) {
            CA.centerDroneOnQr();
        };

    }

}
