package object_recogniztion.video_test;

import de.yadrone.base.ARDrone;
import de.yadrone.base.IARDrone;

public class VideoMain {

    public static void main (String[] arg0) {

        IARDrone drone;

        drone = new ARDrone();
        drone.start();

        new VideoTest(drone);

    }
}
