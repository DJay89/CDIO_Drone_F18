package Video;

import de.yadrone.base.ARDrone;
import de.yadrone.base.IARDrone;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import managers.PilotManager;
import managers.QRsearch;
import object_recogniztion.image_recogniztion.ImageRecognition;

import java.awt.image.BufferedImage;

public class AutoDroneMain extends Application {

    private VideoDisplay vd;
    private PilotManager pm;
    private IARDrone drone;
    private ImageRecognition IR;
    private Thread imgThread;
    private QRsearch qr;

    //Toggle debugmode, if true when the webcam will be use for Image Recognition
    private Boolean devMode = false;
    // Toggle Flight mode, this will a launch of the drone
    private Boolean testRun = true;

    public void start(Stage s){


        if(devMode)
        {
            drone = new ARDrone();
            pm = new PilotManager(drone);
            pm.droneCamCapture(); // start drone image listener
            vd = new VideoDisplay(pm);
            vd.start(s); //starts video controller
            IR = new ImageRecognition(pm);
            imgThread = new Thread(IR);
            imgThread.start();
        }
        else
        {

            System.out.println("HEJ");
            drone = new ARDrone();
            pm = new PilotManager(drone);
            pm.droneCamCapture(); // start drone image listener
            vd = new VideoDisplay(pm);
            vd.start(s); //starts video controller
            IR = new ImageRecognition(pm);
            qr = new QRsearch(IR, pm);
            imgThread = new Thread(IR);
            imgThread.start();


            if( testRun )
            {
                System.out.println("test start");
                pm.takeOff();

                /*
                long spinTime = System.currentTimeMillis() + 5000;
                while ( System.currentTimeMillis() - 5000 != 0)
                {
                    pm.hover(25);
                    pm.move3D(10, 0);
                }

                */
                //pm.takeOffAndLand();
               // pm.move3D(0, 0, 0, 2, 2000);
                pm.move3D(2, -1, 0 , 25, 1000);
                pm.hover(500);
                pm.move3D(2, -1, 0 , 25, 1000);
                pm.hover(500);
                pm.land();
                //int value = gr.searchLvlZero();

               // if ( qr.searchLvlZero() == 1 ){
                   //aSystem.out.println( qr.searchLvlZero());
               //     pm.land();
              //A  }

               // qr.searchLvlZero();

            }

        }

    }
    public static void main(String [] args)
    {
        launch(args);
    }
}