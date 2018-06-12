package videoController;
import controller.Drone;
import org.opencv.core.Core;
import javafx.event.EventHandler;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.fxml.FXMLLoader;

public class VideoDisplay {
    private Drone drone;
    private Boolean withWebcam;
    public VideoDisplayController controller;

    public VideoDisplay(Drone drone, Boolean state){
        withWebcam = state;
        this.drone = drone;
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
    }

    public void start(Stage primaryStage)
    {
        System.out.println("Setting up the stage");
        try
        {
            // load the FXML resource
            FXMLLoader loader = new FXMLLoader(getClass().getResource("videoDisplay.fxml"));
            // store the root element so that the controllers can use it
            BorderPane rootElement = (BorderPane) loader.load();
            // create and style a scene
            Scene scene = new Scene(rootElement, 1200, 1000);
            scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
            // create the stage with the given title  and the previously created
            // scene
            primaryStage.setTitle("JavaFX meets OpenCV");
            primaryStage.setScene(scene);
            // show the GUI
            primaryStage.show();

            // set the proper behavior on closing the application
            this.controller = loader.getController();

            //pass if webcam is needed
            controller.setWebcamRB(withWebcam);

            //pass the drone so we know where to get the picture
            controller.setDrone(drone);
            controller.setCapture(drone.capture);

            primaryStage.setOnCloseRequest((new EventHandler<WindowEvent>() {
                public void handle(WindowEvent we)
                {
                    controller.setClosed();
                }
            }));
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        System.out.println("Done setting up the stage");
    }
}
