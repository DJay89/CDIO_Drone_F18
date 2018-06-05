package Video;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import managers.PilotManager;
import object_recogniztion.image_recogniztion.ImageRecognition;

public class Main extends Application {

    private VideoDisplay vd;
    private PilotManager pm;
    private ImageRecognition IR;
    private Thread imgThread;

    public void start(Stage s){
        pm = new PilotManager();
        vd = new VideoDisplay(pm);
        imgThread = new Thread( (ImageRecognition) IR);
        imgThread.start();
        vd.start(s);
    }
    public static void main(String [] args)
    {

        launch(args);
    }
}
