package Video;

import de.yadrone.base.ARDrone;
import de.yadrone.base.IARDrone;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import managers.PilotManager;
import managers.QRsearch;
import object_recogniztion.image_recogniztion.ImageRecognition;

public class AutoDroneMain extends Application {

    private VideoDisplay vd;
    private PilotManager pm;
    private IARDrone drone;
    private ImageRecognition IR;
    private Thread imgThread;
    private QRsearch qr;

    //Toggle debugmode, if true when the webcam will be use for Image Recognition
    private Boolean devMode = true;
    // Toggle Flight mode, this will a launch of the drone
    private Boolean testRun = false;

    public void start(Stage s){


        if(devMode)
        {
            drone = new ARDrone();
            pm = new PilotManager(drone);
            vd = new VideoDisplay(pm);
            vd.start(s); //starts video controller
            IR = new ImageRecognition(pm);
            imgThread = new Thread(IR);
            imgThread.start();
            qr = new QRsearch(IR, pm);
        }
        else
        {
            drone = new ARDrone();
            pm = new PilotManager(drone);
            pm.droneCamCapture(); // start drone image listener

            while (pm.getImg() == null && devMode == false) {
                // wait for drone camera to get ready
            }
            vd = new VideoDisplay(pm);
            vd.start(s); //starts video controller
            IR = new ImageRecognition(pm);
            imgThread = new Thread(IR);
            imgThread.start();

            if( testRun )
                pm.takeOff();
            pm.hover(5000);
            if ( qr.searchLvlZero() == 1 ){
                System.out.println( qr.searchLvlZero());
                pm.land();
            }
            System.out.println( qr.searchLvlZero());
            pm.land();
        }
                System.out.println("Camera ready");
    }
    public static void main(String [] args)
    {
        launch(args);
    }
}
