package managers;

import controller.Drone;
import object_recogniztion.image_recogniztion.ImageRecognition;

public class QRsearch implements Runnable{

    private Drone drone;
    private ImageRecognition IR;
    private long time = 25;



    public QRsearch(ImageRecognition IR, Drone drone) {
        this.IR = IR;
        this.drone = drone;
    }


    public void run(){
        //Ukommenteret for at kunne kører projektet (Metoden mangler en long som input) - Thomas
        //searchLvlZero();
    }

    // Spins 360 degrees and searches for QR-CODE and Red Rings. //


}
