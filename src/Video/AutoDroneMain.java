package Video;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import managers.PilotManager;
import managers.QRsearch;
import object_recogniztion.image_recogniztion.ImageRecognition;

public class AutoDroneMain extends Application {

    private VideoDisplay vd;
    private PilotManager pm;
    private ImageRecognition IR;
    private Thread imgThread;
    private QRsearch qr;
    private Boolean devMode = false;

    public void start(Stage s){
        pm = new PilotManager();
        vd = new VideoDisplay(pm);
        IR = new ImageRecognition(pm);
        qr = new QRsearch(IR, pm);
        vd.start(s);
        while (pm.getImg() == null && devMode == false) {
            // wait for camera
        }

        System.out.println("Camera ready");
        imgThread = new Thread(IR);
        imgThread.start();

        pm.takeOff();
        pm.hover(5000);
        if ( qr.searchLvlZero() == 1 ){
            System.out.println( qr.searchLvlZero());
            pm.land();
        }
        System.out.println( qr.searchLvlZero());
        pm.land();

    }
    public static void main(String [] args)
    {
        launch(args);
    }
}
