package algorithms;

import controller.Drone;
import object_recogniztion.image_recogniztion.ImageRecognition;

import java.awt.image.BufferedImage;

public class MasterAlgorithm implements Runnable {
    //objects
    private Drone drone;
    private CenteringAlgorithm CA;
    private SearchAlgorithm SA;
    private DistanceAlgorithm DA;

    private ImageRecognition IR;

    private final int QR_CODE_FOUND = 1;

    //objects init
    public MasterAlgorithm(Drone drone) {
        this.drone = drone;
        this.IR = new ImageRecognition();
        this.CA = new CenteringAlgorithm(drone, IR);
        this.SA = new SearchAlgorithm(drone, IR);
        this.DA = new DistanceAlgorithm(drone, IR);

    }

    //Master thread
    public void run() {
        while (!Thread.interrupted()) {
            BufferedImage bi = drone.getImg();
            if (bi != null) {
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
        //int counter = 0;
        //while(true) {
        //    IR.setFrame(drone.getImg());
        //    IR.squareDetect();
            // Start search level
        if (SA.searchLvlZero(60000) == QR_CODE_FOUND) {
        System.out.println("################# found QR ################3");
            // Center on tag
            while(true)
            {
                System.out.println("################# BEGIN CENTERING ################3");
                if (CA.centerDroneOnQr()) {
                    System.out.println("################# BEGIN DISTANCE################3");
                    int dist = DA.getDistance();

                    System.out.println(" dist : " + dist);
                    if (dist != -1)
                    {
                        System.out.println("################# found value ! ################3");
                        DA.reduceDistance(dist);
                    }
                    else
                    {
                        System.out.println("################# PLZ DONT DIE################3");
                        drone.forward(15);
                        drone.hover(2);
                        try {
                            Thread.sleep(200);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }


                    }
                }
            }
        }
    }

}
