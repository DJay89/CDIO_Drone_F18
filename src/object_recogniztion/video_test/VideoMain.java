package object_recogniztion.video_test;

import org.opencv.core.Core;

import de.yadrone.base.ARDrone;
import de.yadrone.base.IARDrone;

public class VideoMain {

    public static void main (String[] arg0) {
    	System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
        IARDrone drone;

        drone = new ARDrone();
        drone.start();

        new VideoTest(drone);

    }
}
