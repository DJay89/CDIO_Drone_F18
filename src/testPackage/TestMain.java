package testPackage;

import controllers.CenteringController;
import de.yadrone.base.ARDrone;
import de.yadrone.base.IARDrone;
import managers.PilotManager;

public class TestMain
{

    public static void main(String[] args) {
        IARDrone drone = new ARDrone();
        PilotManager pilotManager = new PilotManager(drone);

       pilotManager.takeOff();
       pilotManager.hover(2000);

        CenteringController test = new CenteringController(drone, pilotManager);

        test.centerDroneOnCircle();
        pilotManager.hover(2000);
        pilotManager.land();
        System.out.println("End");
        //drone.stop();

    }
}
