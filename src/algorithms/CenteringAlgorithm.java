package algorithms;

import controller.Drone;
import de.yadrone.apps.paperchase.QRCodeScanner;
import object_recogniztion.image_recogniztion.ImageRecognition;
import object_recogniztion.qr_scanner.QRscanner;
import utils.distReturn;
import utils.imageReturn;

import java.awt.*;


public class CenteringAlgorithm {

    // Video size
    private static final int imgWidth = 640;
    private static final int imgHeight = 360;
    private static final int marginOfCenter = 20;

    private Drone drone;
    private ImageRecognition IR;

    private final int time = 10;
    private boolean findQR;
    private boolean findRing;

    public CenteringAlgorithm(Drone drone, ImageRecognition IR) {
        this.drone = drone;
        this.IR = IR;
    }


    public boolean centerDroneOnQr() {
        System.out.println("Centering on QR");
        findQR = true;
        return tagIsCentered();
    }

    public boolean centerDroneOnRing() {
        System.out.println("Centering on Ring");
        findRing = true;
        return tagIsCentered();
    }

    private boolean tagIsCentered() {
        while (tagIsFound() && !droneIsCentered() && !targetDistanceApproved()) {
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

            // Distance to QR object
            if (findQR) {
                switch (targetDistance()) {
                    case -1:
                        drone.forward(time);
                        System.out.println("moving forward");
                        break;
                    case 0:
                        break;
                    case 1:
                        drone.backward(time);
                        System.out.println("moving backward");
                        break;
                }
            }
            drone.hover(time);
        }

        findRing = false;
        findQR = false;
        if (droneIsCentered()) {
            System.out.println("Drone is centered");
            return true;
        } else {
            System.out.println("Object lost");
            return false;
        }
    }


    private boolean droneIsCentered() {

        // Get the return values from video feed
        imageReturn ir = drone.getRetValues();

        if (!isTagInCenter(ir.x, ir.y)) {
            return false;
        }

        return true;
    }

    private boolean targetDistanceApproved() {
        distReturn dr = drone.getDistValues();
        if (dr.distance > 69 && dr.distance < 100) {
            return true;
        } else {
            return false;
        }
    }

    // Checking if a tag is found. if it is, save values
    private boolean tagIsFound() {
        int i = 0;
        while (i < 100) {
            // Get buffered image
            IR.setFrame(drone.getImg());

            imageReturn ir = null;
            distReturn dr = null;
            // Scan image for object
            if (findQR) {
                ir = IR.qrScan();
                dr = IR.sdScan();
            }
            if (findRing) {
                ir = IR.rrScan();
                System.out.println("scanning for rings");
            }

            // Exit if picture havn't been scanned
            if (ir == null) {
                System.out.println("Error! No object are being searched for");
                return false;
            }

            // Save values
            drone.setRetValues(ir);
            drone.setDistValues(dr);

            System.out.println(ir.resutalt);

            if (ir.found) {
                System.out.println("Image found on point: " + ir.x + ", " + ir.y);
System.out.println(dr.distance);
                if (ir.name.equals("QR")) {
                    if (dr.distFound) {
                        System.out.println(dr.distance);
                        return true;
                    }
                } else {
                    return true;
                }
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

    private int targetDistance() {

        distReturn dr = drone.getDistValues();

        System.out.println("Distance to object: " + dr.distance);
        // distance too close
        if (dr.distance < 69) {
            return 1;
        }
        // distance OK
        if (dr.distance > 69 && dr.distance < 100) {
            return 0;
        }
        // object too far away
        if (dr.distance > 100) {
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
