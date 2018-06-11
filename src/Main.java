import algorithms.MasterAlgorihm;
import controller.Drone;
import de.yadrone.base.ARDrone;
import de.yadrone.base.IARDrone;
import javafx.application.Application;
import javafx.stage.Stage;
import object_recogniztion.video_test.VideoDisplay;
import org.opencv.core.Core;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

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
        ExecutorService executorService = Executors.newFixedThreadPool(30);
        IARDrone iarDrone = new ARDrone();
        Drone drone = new Drone(iarDrone, debug);
        MasterAlgorihm MA = new MasterAlgorihm(drone);
        VideoDisplay VD = new VideoDisplay(drone, debug);
        VD.start(stage);

        //Do takeoff and drone stuff
        if(debug){
            executorService.execute(drone);
        }
        else if(!debug && flymode){
            drone.takeOff();
            drone.hover(2000);
        }
        //executorService.execute(MA);
        masterThread = new Thread(MA);
        masterThread.start();
        MA.saThread.start();
        try {
            masterThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        //drone.land();
    }
}
