import algorithms.MasterAlgorihm;
import controller.Drone;
import de.yadrone.base.ARDrone;
import de.yadrone.base.IARDrone;
import object_recogniztion.video_test.VideoDisplay;

public class Main {
    private static final Boolean debug = false;
    private static Thread masterThread;

    public static void main (String [] agrs) {
        IARDrone iarDrone = new ARDrone();

        VideoDisplay VD = new VideoDisplay(debug);
        Drone drone = new Drone(iarDrone, VD);

        //Do takeoff and drone stuff

        MasterAlgorihm MA = new MasterAlgorihm(drone);
        masterThread = new Thread(MA);
        masterThread.start();
    }
}
