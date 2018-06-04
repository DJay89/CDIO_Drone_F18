package controllers;

import de.yadrone.base.IARDrone;
import managers.PilotManager;


public class CenteringController {

    private PilotManager pilot = null;
    private IARDrone drone = null;

    private int hori;

    private char turnDirection = 'n';

    // simulation
    private int xAxis = -1;
    private int yAxis = -1;
    private int hDia = 5;
    private int vDia = 5;

    public CenteringController (IARDrone drone, PilotManager pilot) {
        this.drone = drone;
        this.pilot = pilot;
    }


    public boolean tagIsCentered () {

        // simulation
        int i = 0;
        int j = 0;

        // Actual code
        while(!isCentered()) {
            int x = getXAxis();
            int y = getYAxis();

            switch(y) {
                case -1:
                    right();
                    break;
                case 0:
                    break;
                case 1:
                    left();
                    break;
            }

            switch(x) {
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
    public boolean placingCircle () {
/* Whats needed:
* Diamenter
* højde
* bredde
* radius
* */
if (getHoriDiamenter() != getVertiDiamenter() ) {

    //Virkelig ikke tilfreds med dette stykke kode.
if (turnDirection == 'n') {
    hori = getHoriDiamenter();

    spinLeft();

    if (hori > getHoriDiamenter()) {
        turnDirection = 'L';
        hori = getHoriDiamenter();

    } else {
        turnDirection = 'R';
        hori = getHoriDiamenter();
    }
    return false;
}
    switch (turnDirection) {
        case 'L':
            spinLeft();
            if (hori < getHoriDiamenter()) {
                turnDirection = 'R';
            }
            break;
        case 'R':
            spinRight();
            if (hori < getHoriDiamenter()) {
                turnDirection = 'L';
            }

            break;
    }

    hori = getHoriDiamenter();

return false;
}
else {
    turnDirection = 'n';
    return true;
}

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

    private int getVertiDiamenter () {
        return this.vDia;
    }
    private int getHoriDiamenter () {
        return this.hDia;
   }

    private boolean isCentered() {
        if (this.xAxis == 0 && this.yAxis == 0) {
            return true;
        }
        return false;
    }
}
