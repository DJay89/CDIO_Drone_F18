package algorithms;


import controller.Drone;
import object_recogniztion.image_recogniztion.ImageRecognition;
import utils.imageReturn;

import java.awt.image.BufferedImage;

public class SearchAlgorithm {

    //Objects
    private Drone drone;
    private ImageRecognition IR;

    //Objects init
    public SearchAlgorithm(Drone drone, ImageRecognition IR){
        this.drone = drone;
        this.IR = IR;
    }

    //Search Algorithm
    /*@Override
    public void run() {
        while (!Thread.interrupted()){
            try{
                IR.setFrame(drone.getImg());

                // pass info to drone
                //imageReturn ir = IR.qrScan();
                imageReturn ir = IR.rrScan();
                drone.setRetValues(ir);
                if( ir.found){
                    System.out.println(ir.resutalt);
                }

            } catch (NullPointerException ex){
                System.out.println("No pic.");
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    return;
                }
            }

        }
    }*/

    public int searchLvlZero(long searchTime) {

        //    System.out.println("Search Level 0: Searching for QR and Red Rings");
        long spinTime = System.currentTimeMillis() + searchTime;

        String temp = drone.getRetValues().resutalt;
        while ( System.currentTimeMillis() - spinTime <= 0 && temp.equals("")) {

            //drone.move3D(2, -1, 0 , 20, 500);
            drone.hover(5);

            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            try{
                IR.setFrame(drone.getImg());

                // pass info to drone
                imageReturn ir = new imageReturn();
                //imageReturn             System.out.println("Search Level 0: Searching for QR and Red Rings");
                ir = IR.qrScan();
                drone.setRetValues(ir);
                if( ir.found){
                    System.out.println(ir.resutalt);
                    System.out.println("Point: " + ir.x + " , " + ir.y);
                    //drone.land();
                    return 1;
                }

            } catch (NullPointerException ex){
                System.out.println("No pic.");
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    return -1;
                }
            }
        }

        //System.out.println("Nothing found initiating Search Level 1");
        // drone.land();
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
