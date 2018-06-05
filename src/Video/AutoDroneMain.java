package Video;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import managers.PilotManager;
import object_recogniztion.image_recogniztion.ImageRecognition;

public class AutoDroneMain extends Application {

    private VideoDisplay vd;
    private PilotManager pm;
    private ImageRecognition IR;
    private Thread imgThread;
    private Boolean devMode = false;

    public void start(Stage s){
        pm = new PilotManager();
        vd = new VideoDisplay(pm);
        IR = new ImageRecognition(pm);
        vd.start(s);
        while (pm.getImg() == null && devMode == false) {
            // wait for camera
        }

        System.out.println("Camera ready");
        imgThread = new Thread(IR);
        imgThread.start();

    }
    public static void main(String [] args)
    {
        launch(args);
    }
}
