package object_recogniztion.video_test;

import controller.Drone;
import org.opencv.core.Mat;
import de.yadrone.base.ARDrone;
import de.yadrone.base.IARDrone;
import javafx.application.Application;
import javafx.stage.Stage;
import managers.QRsearch;
import object_recogniztion.image_recogniztion.ImageRecognition;
import object_recogniztion.image_recogniztion.SquareDetection.squareDetection;


public class AutoDroneMain extends Application {

    private VideoDisplay vd;
    private Drone droneController;
    private IARDrone drone;
    private ImageRecognition IR;
    private Thread imgThread, searchTread;
    private QRsearch qr;

    //Toggle debugmode, if true when the webcam will be use for Image Recognition
    private Boolean devMode =true;
    // Toggle Flight mode, this will a launch of the drone
    private Boolean testRun = true;

    public void start(Stage s){


        if(devMode)
        {
            drone = new ARDrone();
            droneController = new Drone(drone);
            droneController.droneCamCapture(); // start drone image listener
            vd = new VideoDisplay(droneController);
            vd.start(s); //starts video controller
            IR = new ImageRecognition(droneController);
            imgThread = new Thread(IR);
            imgThread.start();

        }
        else
        {
            /*
            drone = new ARDrone();
            droneController = new Drone(drone);
            droneController.droneCamCapture(); // start drone image listener
            vd = new VideoDisplay(droneController);
            vd.start(s); //starts video controller
            IR = new ImageRecognition(droneController);
            qr = new QRsearch(IR, droneController);
            imgThread = new Thread(IR);
            imgThread.start();
*/

            if( testRun )
            {


                /*
                System.out.println("test start");
                droneController.takeOff();
                droneController.hover(2000);
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                    searchTread = new Thread(qr);
                    searchTread.start();
*/
                //pm.takeOffAndLand();
               // pm.move3D(0, 0, 0, 2, 2000);

                //int value = gr.searchLvlZero();

               //
                   //a;
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
