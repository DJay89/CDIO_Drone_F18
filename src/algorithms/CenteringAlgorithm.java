package algorithms;

import controller.Drone;
import de.yadrone.apps.paperchase.QRCodeScanner;
import object_recogniztion.qr_scanner.QRscanner;
import utils.imageReturn;

public class CenteringAlgorithm {

    // Video size
    private static final int imgWidth = 1280;
    private static final int imgHeight = 720;
    private static final int marginOfCenter = 10;

    private Drone drone = null;

    private boolean findCircle;
    private boolean findQr;

    private QRscanner qrScanner;
    private int tagX;
    private int tagY;

    public CenteringAlgorithm(Drone drone) {
        this.drone = drone;
    }


    public boolean centerDroneOnQr() {
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
        while (!isDroneCentered()) { // and circle or qr found (otherwise endless loop)

            switch (flightDirectionX()) {
                case -1:
                    drone.tiltRight(50);
                    break;
                case 0:
                    break;
                case 1:
                    drone.tiltLeft(50);
                    break;
            }

            switch (flightDirectionY()) {
                case -1:
                    drone.up(50);
                    break;
                case 0:
                    break;
                case 1:
                    drone.down(50);
                    break;
            }
        }
        return true;
    }

    private boolean isDroneCentered() {
        // get coords from drone
        imageReturn ir = drone.getRetValues();
        if(ir.found){
            this.tagX = ir.x;
            this.tagY = ir.y;
            if (!isTagInCenter(tagX, tagY)) {
                return false;
            }
            else {
                return true;
            }
        }
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
        if (this.tagX > imgWidth / 2 + marginOfCenter) { return 1; }
        if (this.tagX < imgWidth / 2 - marginOfCenter) { return -1; }
        return 0;
    }

    private int flightDirectionY() {
        if (this.tagY > imgHeight + marginOfCenter) { return 1; }
        if (this.tagY < imgHeight - marginOfCenter) { return -1; }
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
