package algorithms;

import controller.Drone;
import de.yadrone.apps.paperchase.QRCodeScanner;
import object_recogniztion.image_recogniztion.ImageRecognition;
import object_recogniztion.qr_scanner.QRscanner;
import utils.imageReturn;

import java.awt.*;


public class CenteringAlgorithm {

    // Video size
    private static final int imgWidth = 640;
    private static final int imgHeight = 360;
    private static final int marginOfCenter = 10;

    private Drone drone;
    private ImageRecognition IR;

    private int time = 10;


    public CenteringAlgorithm(Drone drone, ImageRecognition IR) {
        this.drone = drone;
        this.IR = IR;
    }


    public boolean centerDroneOnQr() {
        System.out.println("Centering Algorithm Starting");
        //findQr = true;
        return tagIsCentered();
    }

    /*
        public boolean centerDroneOnRing() {
            findCircle = true;
            return tagIsCentered();
        }
      */
    private boolean tagIsCentered() {
        while (tagIsFound() && !droneIsCentered()) {
            System.out.println("centering on point: " + drone.getRetValues().x + ", " + drone.getRetValues().y);
            switch (flightDirectionX()) {
                case -1:
                    drone.tiltRight(time);
                    System.out.println("moving right");
                    break;
                case 0:
                    break;
                case 1:
                    drone.tiltLeft(time);
                    System.out.println("moving left");
                    break;
            }

            switch (flightDirectionY()) {
                case -1:
                    drone.up(time);
                    System.out.println("moving up");
                    break;
                case 0:
                    break;
                case 1:
                    drone.down(time);
                    System.out.println("moving down");
                    break;
            }
            drone.hover(time);
        }
        System.out.println("Drone is centered");
        return true;
    }


    private boolean droneIsCentered() {

        // Get the return values from video feed
        imageReturn ir = drone.getRetValues();

        if (!isTagInCenter(ir.x, ir.y)) {
            return false;
        }

        return true;
    }

    // Checking if a tag is found. if it is, save values
    private boolean tagIsFound() {
        int i = 0;
        while (i < 100) {
            // Get buffered image
            IR.setFrame(drone.getImg());

            // Scan image
            imageReturn ir = IR.qrScan();
            // Save values
            drone.setRetValues(ir);
            ir = drone.getRetValues();

            System.out.println("Points: " + ir.x + ", " + ir.y);

            if (ir.found) {
                System.out.println("Image found");
                return true;
            }

            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            i++;
        }

        System.out.println("Image not found");
        return false;
    }

    private boolean isTagInCenter(int x, int y) {

        if ((
                x < imgWidth / 2 + marginOfCenter &&
                        x > imgWidth / 2 - marginOfCenter
        ) && (
                y < imgHeight / 2 + marginOfCenter &&
                        y > imgHeight / 2 - marginOfCenter)) {
            return true;
        }
        return false;
    }

    private int flightDirectionX() {
        imageReturn ir = drone.getRetValues();

        if (ir.x < (imgWidth / 2 - marginOfCenter)) {
            return 1;
        }
        if (ir.x > (imgWidth / 2 + marginOfCenter)) {
            return -1;
        }
        return 0;
    }

    private int flightDirectionY() {
        imageReturn ir = drone.getRetValues();

        if (ir.y > (imgHeight / 2 + marginOfCenter)) {
            return 1;
        }
        if (ir.y < (imgHeight / 2 - marginOfCenter)) {
            return -1;
        }
        return 0;
    }

    //Placing the drone in front of the circle
//    private boolean angleAdjusted() {
//
//        if (getHoriDiamenter() != getVertiDiamenter()) {
//
//            switch (turnDirection) {
//                // turn direction not yet decided
//                case 'n':
//                    drone.spinLeft(50);
//                    turnDirection = 'L';
//                    break;
//                // turn direction is left
//                case 'L':
//                    if (horizontalDiameter > getHoriDiamenter()) {
//                        drone.spinLeft(50);
//                    } else {
//                        drone.spinRight(50);
//                        turnDirection = 'R';
//                    }
//                    break;
//                // turn direction is right
//                case 'R':
//                    if (horizontalDiameter > getHoriDiamenter()) {
//                        drone.spinRight(50);
//                    } else {
//                        drone.spinLeft(50);
//                        turnDirection = 'L';
//                    }
//                    break;
//                default:
//                    break;
//            }
//
//            horizontalDiameter = getHoriDiamenter();
//            return false;
//        }
//
//        // The angle is now correct
//        turnDirection = 'n';
//        return true;
//    }
}
