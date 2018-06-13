package algorithms;


import controller.Drone;
import object_recogniztion.image_recogniztion.ImageRecognition;
import utils.imageReturn;

import java.awt.image.BufferedImage;

public class SearchAlgorithm implements Runnable{

    //Objects
    private Drone drone;
    private ImageRecognition IR;
    private Thread irThread;

    //Objects init
    public SearchAlgorithm(Drone drone){
        this.drone = drone;
        this.IR = new ImageRecognition(drone);
    }

    //Search Algorithm
    @Override
    public void run() {
        while (!Thread.interrupted()){
            //set type for looking for qr
            IR.setType(IR.QR);
            //create new thread and start it
            irThread = new Thread(IR);
            irThread.start();
            //check search level
            searchLvlZero(20000);
            //interrupt and end thread
            irThread.interrupt();

            // find ring
            IR.setType(IR.RING);
            irThread = new Thread(IR);
            irThread.start();
            searchLvlOne(20000);
        }
    }

    public int searchLvlZero(long searchTime) {

        long spinTime = System.currentTimeMillis() + searchTime;
        String temp = drone.getRetValues().resutalt;
        while ( System.currentTimeMillis() - spinTime <= 0 && temp.equals("")) {

            System.out.println("Search Level 0: Searching for QR and Red Rings");
            drone.move3D(2, -1, 0 , 20, 500);
            drone.hover(5);

            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            temp = drone.getRetValues().resutalt;
        }

        if (!temp.equals("")) {
            System.out.println("QR Found\n" + "Result: " + temp);
            drone.land();
            return 1;
        }

        System.out.println("Nothing found initiating Search Level 1");
        drone.land();
        return 0;
        // return searchLvlOne();


    }


    public int searchLvlOne(long searchTime) {

        long spinTime = System.currentTimeMillis() + searchTime;
        String temp = drone.getRetValues().resutalt;
        while (System.currentTimeMillis() - spinTime <= 0 && temp.equals("")) {

            System.out.println("Search Level 1: Searching for QR and Red Rings");
            drone.move3D(2, -1, 0, 20, 500);
            drone.hover(5);

            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            temp = drone.getRetValues().resutalt;
        }

        if (!temp.equals("")) {
            System.out.println("QR Found\n" + "Result: " + temp);
            drone.land();
            return 1;
        }

        System.out.println("Nothing found initiating Search Level 2");
        drone.land();
        return 0;
        // return searchLvlTwo();
    }

    public void searchLvlTwo() {

    }
}
