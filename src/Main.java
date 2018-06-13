import algorithms.MasterAlgorithm;
import controller.Drone;
import de.yadrone.base.ARDrone;
import de.yadrone.base.IARDrone;
import javafx.application.Application;
import javafx.stage.Stage;
import videoController.VideoDisplay;
import org.opencv.core.Core;

import java.awt.image.BufferedImage;

public class Main extends Application {

    /**
     * debug is set to do Image Recognition with video from webcam
     * true = webcam
     * false = dronecam
     */
    private static final Boolean debug = false;

    /**
     * flymode is made to make sure the drone dont fly random
     * true = It will perform takeoff
     * false = It won't
     */
    private static final Boolean flymode = false;

    /**
     * Thread running the master Thread. This will spawn other Threads
     */
    private static Thread masterThread, webcamThread;

    private static String OS = System.getProperty("os.name").toLowerCase();

    public static void main(String [] args)
    {
        //OpenCV Native path loader
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
        // launch Main.start
        launch(args);
    }

    public void start (Stage stage) {
        System.out.println("Constructing the classes, Debug is : " + debug);
        IARDrone iarDrone = new ARDrone();
        Drone drone = new Drone(iarDrone, debug);
        VideoDisplay VD = new VideoDisplay(drone, debug);
        System.out.println("Done constructing the classes");

        //starts GUI
        VD.start(stage);
        if(debug){
            //if webcam mode, start a Thread to get images from Webcam
            webcamThread = new Thread(drone);
            // start that Thread
            webcamThread.start();
        }

        //System checks before flying
        System.out.print("Connecting Videostreaming");
        if(OS.contains("mac")){
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.print("Wait");
        }
        else
        {
            BufferedImage bi = drone.getImg();
            while(bi == null) {
                bi = drone.getImg();
            }

        }
        if(debug){
            //Do takeoff and drone stuff
            if(webcamThread.getState().equals(Thread.State.TERMINATED))
                System.exit(-1);
        }

        if(!debug && flymode){
            drone.takeOff();
            drone.hover(2000);
        }

        System.out.print("Starting Master Algorithm");
        //init master thread and start it
        MasterAlgorithm MA = new MasterAlgorithm(drone);
        masterThread = new Thread(MA);
        masterThread.start();


        //here is where we finally stop master thread when this done
        //masterThread.join();
        //drone.land();
    }
}
