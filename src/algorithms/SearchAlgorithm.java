package algorithms;


import controller.Drone;
import object_recogniztion.image_recogniztion.ImageRecognition;
import object_recogniztion.squareDetect.FilterBackground;
import object_recogniztion.squareDetect.SquareDetect;
import org.opencv.core.Mat;
import utils.Utils;
import utils.imageReturn;

import java.awt.image.BufferedImage;

public class SearchAlgorithm implements Runnable{
    private Drone drone;
    private ImageRecognition IR;
    private SquareDetect SD;
    private FilterBackground FBG;


    public SearchAlgorithm(Drone drone){
        this.drone = drone;
        this.IR = new ImageRecognition();
        this.SD = new SquareDetect();
        this.FBG = new FilterBackground();
            }

    @Override
    public void run() {
        while (!Thread.interrupted()){
            try{

                IR.setFrame(drone.getImg());
                Mat droneMatFrame = Utils.bufferedImageToMat(drone.getImg());
                Mat maskedImage = FBG.filterBackGround(droneMatFrame, 1);

                SD.findRectangle(maskedImage, droneMatFrame);


                //drone.setImg(newFrame);

                // pass info to drone
                //imageReturn ir = IR.qrScan();
                /*
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
                */
            } catch (Exception e) {
                e.printStackTrace();
            }

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
    /*
    public Mat imageWithSquares(){
        return
    }
    */
}
