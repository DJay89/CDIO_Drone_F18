package algorithms;

import controller.Drone;
import object_recogniztion.image_recogniztion.ImageRecognition;

public class DistanceAlgorithm {


    ImageRecognition IR;
    Drone drone;


    public DistanceAlgorithm(Drone drone, ImageRecognition ir){
        this.IR = IR;
        this.drone = drone;
    }


    public int reduceDistance(){

        IR.setFrame(drone.getImg());

        IR.



        return 0;
    }
}
