package algorithms;

import controller.Drone;
import object_recogniztion.image_recogniztion.ImageRecognition;
import utils.imageReturn;

import java.awt.image.BufferedImage;

public class MasterAlgorithm implements Runnable {
    //objects
    private Drone drone;
    private CenteringAlgorithm CA;
    private SearchAlgorithm SA;
    private ImageRecognition IR;

    private final int QR_CODE_FOUND = 1;

    //objects init
    public MasterAlgorithm(Drone drone) {
        this.drone = drone;
        this.IR = new ImageRecognition();
        this.CA = new CenteringAlgorithm(drone, IR);
        this.SA = new SearchAlgorithm(drone, IR);

    }

    //Master thread
    public void run() {
        while (!Thread.interrupted()) {
            BufferedImage bi = drone.getImg();
            if (bi != null) {
                //System.out.println("Starting Master Algorithm");
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
        // Run the algorithm while condition
     //   while(true) {
/*
            IR.setFrame(drone.getImg());
            imageReturn ret = IR.squareDetect();
            if (ret.found) {
            }*/
        // Start search level
     //   while ( SA.searchLvlZero(20000) == QR_CODE_FOUND ) {


           //     CA.centerDroneOnQr();
                // Center on tag
                //CA.centerDroneOnQr();
         //   }

      //  }
    }

}
