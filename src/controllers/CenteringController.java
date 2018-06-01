package controllers;

import de.yadrone.base.IARDrone;
import managers.PilotManager;


public class CenteringController {

    private PilotManager pilot = null;
    private IARDrone drone = null;

    // simulation
    private int xAxis = -1;
    private int yAxis = -1;

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


    // simulation
    private int getXAxis() {
        return this.xAxis;
    }
    private int getYAxis() {
        return this.yAxis;
    }

    private boolean isCentered() {
        if (this.xAxis == 0 && this.yAxis == 0) {
            return true;
        }
        return false;
    }

}
