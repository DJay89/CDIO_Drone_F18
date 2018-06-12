import algorithms.MasterAlgorithm;
import controller.Drone;
import de.yadrone.base.ARDrone;
import de.yadrone.base.IARDrone;
import javafx.application.Application;
import javafx.stage.Stage;
import object_recogniztion.squareDetect.SquareDetect;
import videoController.VideoDisplay;
import org.opencv.core.Core;

import java.awt.image.BufferedImage;

public class Main extends Application {
    private static final Boolean debug = true;
    private static final Boolean flymode = false;
    private static Thread masterThread;

    public static void main(String [] args)
    {
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
        launch(args);
    }

    public void start (Stage stage) {
        System.out.println("Constructing the classes, Debug is : " + debug);
        IARDrone iarDrone = new ARDrone();
        Drone drone = new Drone(iarDrone, debug);
        VideoDisplay VD = new VideoDisplay(drone, debug);
        System.out.println("Done constructing the classes");
        VD.start(stage);
        if(debug){
            //if webcam, start framegrabber
            Thread dT = new Thread(drone);
            dT.start();
        }
        System.out.print("Connecting Videostreaming");
        BufferedImage bi = drone.getImg();
        /*
        while(bi == null) {
            bi = drone.getImg();
            System.out.print(".");
        }
        */

        //Do takeoff and drone stuff

        if(!debug && flymode){
            drone.takeOff();
            drone.hover(2000);
        }

        System.out.print("Starting Master Algorithm");
        MasterAlgorithm MA = new MasterAlgorithm(drone);

        masterThread = new Thread(MA);
        masterThread.start();
        //drone.land();
    }
}
