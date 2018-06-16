package algorithms;

import controller.Drone;
import object_recogniztion.image_recogniztion.ImageRecognition;
import utils.distReturn;

public class DistanceAlgorithm {


    ImageRecognition IR;
    Drone drone;
    private final int time = 5;


    public DistanceAlgorithm(Drone drone, ImageRecognition IR){
        this.IR = IR;
        this.drone = drone;
    }


    public int reduceDistance(){

        System.out.println("-------------starting reduce Distance----------");

        IR.setFrame(drone.getImg());
        distReturn dr = IR.sdScan();

        System.out.println("found distance : " + dr.distFound);

        if(dr.distFound) {
            System.out.println("Distance : " + dr.distance);

            if (dr.distance > 300) {
                System.out.println("doing nothing");

                return -1;

            }
            if (dr.distance < 80) {
                System.out.println("moving backwards");
                drone.backward(time);
                return 0;
            } else if (80 < dr.distance && dr.distance < 120) {
                System.out.println("Correckt distance");
                drone.land();
                return 1;

            } else if (120 < dr.distance && dr.distance < 300) {
                System.out.println("moving forward");
                drone.forward(time);
                return 0;

            }
        }

        return -1;



    }
}
