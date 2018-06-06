package algorithm;

import de.yadrone.base.IARDrone;
import managers.PilotManager;


public class CenteringController {

    private PilotManager pilot = null;
    private IARDrone drone = null;

    private boolean isCentered;
    private int horizontalDiameter;
    private char turnDirection = 'n';

    // simulation
    private int xAxis = -1;
    private int yAxis = -1;
    private int hDia = 5;
    private int vDia = 5;

    public CenteringController(IARDrone drone, PilotManager pilot) {
        this.drone = drone;
        this.pilot = pilot;
    }

    public boolean centerDroneOnCircle() {
        this.isCentered = isCenteredCircle();
        tagIsCentered();
        angleAdjusted();
        return true;
    }

    public boolean centerDroneOnQR() {
        this.isCentered = isCenteredQR();
        tagIsCentered();
        return true;
    }


    private boolean tagIsCentered() {

        // simulation
        int i = 0;
        int j = 0;

        // Actual code
        while (!isCentered) { // and circle or qr found (otherwise endless loop)
            int x = getXAxis();
            int y = getYAxis();

            switch (y) {
                case -1:
                    right();
                    break;
                case 0:
                    break;
                case 1:
                    left();
                    break;
            }

            switch (x) {
                case -1:
                    up();
                    break;
                case 0:
                    break;
                case 1:
                    down();
                    break;
            }


            // simulation
            if (i == 10) {
                this.xAxis = 0;
            }
            if (j == 5) {
                this.yAxis = 0;
            }

            i++;
            j++;

        }
        return true;
    }

    //Placing the drone in front of the circle
    private boolean angleAdjusted() {

        if (getHoriDiamenter() != getVertiDiamenter()) {

            switch (turnDirection) {
                // turn direction not yet decided
                case 'n':
                    spinLeft();
                    turnDirection = 'L';
                    break;
                // turn direction is left
                case 'L':
                    if (horizontalDiameter > getHoriDiamenter()) {
                        spinLeft();
                    } else {
                        spinRight();
                        turnDirection = 'R';
                    }
                    break;
                // turn direction is right
                case 'R':
                    if (horizontalDiameter > getHoriDiamenter()) {
                        spinRight();
                    } else {
                        spinLeft();
                        turnDirection = 'L';
                    }
                    break;
                default:
                    break;
            }

            horizontalDiameter = getHoriDiamenter();
            return false;
        }

        // The angle is now correct
        turnDirection = 'n';
        return true;
    }


    private void up() {
        pilot.up(50);
    }

    private void down() {
        pilot.down(50);
    }

    private void left() {
        pilot.tiltLeft(50);
    }

    private void right() {
        pilot.tiltRight(50);
    }

    private void spinRight() {
        pilot.spinRight(25);
    }

    private void spinLeft() {
        pilot.spinLeft(25);
    }

    // simulation
    private int getXAxis() {
        return this.xAxis;
    }

    private int getYAxis() {
        return this.yAxis;
    }

    private int getVertiDiamenter() {
        return this.vDia;
    }

    private int getHoriDiamenter() {
        return this.hDia;
    }

    private boolean isCenteredQR() {
        if(this.xAxis == 0 && this.yAxis == 0) {
            return true;
        }else {
            return false;
        }
    }
    private boolean isCenteredCircle() {
        if(this.xAxis == 0 && this.yAxis == 0) {
            return true;
        }else {
            return false;
        }
    }
}
