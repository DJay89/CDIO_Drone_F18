package videoController;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import controller.Drone;
import object_recogniztion.image_recogniztion.squareDetect.SquareDetect;
import object_recogniztion.image_recogniztion.squareDetect.SquareDetectTst;
import org.opencv.core.Mat;
import org.opencv.videoio.VideoCapture;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import utils.Utils;

public class VideoDisplayController {

    //FX
    // the FXML toggleCam
    @FXML
    private Button toggleCam;
    // the FXML image view
    @FXML
    private ImageView currentFrame;
    // the OpenCV object that realizes the video capture
    private VideoCapture capture;

    //Thread/Runnable
    private Runnable frameGrabber;
    // a timer for acquiring the video stream
    private ScheduledExecutorService timer;

    //Extra
    // a flag to change the toggleCam behavior
    private boolean cameraActive = false;
    private Drone drone;
    public Boolean debug = false;
    protected Boolean changeDroneCam = false;
    private SquareDetectTst sd;


    /**
     * The action triggered by pushing the toggleCam on the GUI
     *
     * @param event
     *            the push toggleCam event
     */
    @FXML
    protected void startCamera(ActionEvent event)
    {
        this.sd = new SquareDetectTst();
        if (!this.cameraActive)
        {
            if(drone != null){
                this.cameraActive = true;

                // grab a frame every 33 ms (30 frames/sec)
                frameGrabber = new Runnable() {
                    @Override
                    public void run()
                    {
                        //get videostream from drone
                        Mat frame  = Utils.bufferedImageToMat(drone.getImg());
                        // convert and show the frame

                        frame = sd.findRectangle( frame, frame );
                        Image imageToShow = Utils.mat2Image(frame);
                        //display
                        updateImageView(currentFrame, imageToShow);
                    }
                };
                // frame rate setup
                this.timer = Executors.newSingleThreadScheduledExecutor();
                this.timer.scheduleAtFixedRate(frameGrabber, 0, 33, TimeUnit.MILLISECONDS);

                // update the toggleCam content
                this.toggleCam.setText("Stop Camera");
            }
        }
        else
        {
            // the camera is not active at this point
            this.cameraActive = false;
            // update again the toggleCam content
            this.toggleCam.setText("Start Camera");
            // stop the timer
            this.stopAcquisition();
        }
    }

    /**
     * The action triggered by pushing the DroneCam on the GUI
     *
     * @param event
     *            the push toggleCam event
     */
    @FXML
    protected void changeCam(ActionEvent event)
    {
        drone.toggleCamera();
    }

    //passing drone
    public void setDrone(Drone drone){
        this.drone = drone;
    }

    //pass drone capture setup
    public void setCapture(VideoCapture capture){
        this.capture = capture;
    }

    //Use when webcam is needed
    public  void setWebcamRB(Boolean toggle){
        debug = toggle;
    }

    /**
     * Stop the acquisition from the camera and release all the resources
     */
    private void stopAcquisition()
    {
        if (this.timer!=null && !this.timer.isShutdown())
        {
            try
            {
                // stop the timer
                this.timer.shutdown();
                this.timer.awaitTermination(33, TimeUnit.MILLISECONDS);
            }
            catch (InterruptedException e)
            {
                // log any exception
                System.err.println("Exception in stopping the frame capture, trying to release the camera now... " + e);
            }
        }

        if (this.capture.isOpened())
        {
            // release the camera
            this.capture.release();
        }
    }

    /**
     * Update the {@link ImageView} in the JavaFX AutoDroneMain thread
     *
     * @param view
     *            the {@link ImageView} to update
     * @param image
     *            the {@link Image} to show
     */
    private void updateImageView(ImageView view, Image image)
    {
        Utils.onFXThread(view.imageProperty(), image);
    }

    /**
     * On application close, stop the acquisition from the camera
     */
    protected void setClosed()
    {
        this.stopAcquisition();
        this.drone.land();
    }
}
