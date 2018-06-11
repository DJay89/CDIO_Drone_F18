package algorithms;


import controller.Drone;
import object_recogniztion.image_recogniztion.ImageRecognition;

import java.awt.image.BufferedImage;

public class SearchAlgorithm implements Runnable{
    private Drone drone;
    private ImageRecognition IR;
    private Thread irThread;
    private BufferedImage BI;

    public SearchAlgorithm(Drone drone){
        this.drone = drone;
        this.IR = new ImageRecognition();
    }

    @Override
    public void run() {
        while (!Thread.interrupted()){
            try{
                IR.setFrame(drone.getImg());
            } catch (NullPointerException ex){
                System.out.println("No pic. \t");
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    return;
                }
            }
            drone.retValues = IR.qrScan();
            System.out.print(drone.retValues.resutalt);
        }
    }

    public int searchLvlZero(long searchTime) {

        long spinTime = System.currentTimeMillis() + searchTime;
        String temp = drone.retValues.resutalt;
        while ( System.currentTimeMillis() - spinTime <= 0 && temp.equals("")) {

            System.out.println("Search Level 0: Searching for QR and Red Rings");
            drone.move3D(2, -1, 0 , 20, 500);
            drone.hover(5);

            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            temp = drone.retValues.resutalt;
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
        String temp = drone.retValues.resutalt;
        while (System.currentTimeMillis() - spinTime <= 0 && temp.equals("")) {

            System.out.println("Search Level 1: Searching for QR and Red Rings");
            drone.move3D(2, -1, 0, 20, 500);
            drone.hover(5);

            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            temp = drone.retValues.resutalt;
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
