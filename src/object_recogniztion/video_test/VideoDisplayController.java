package object_recogniztion.video_test;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import javafx.scene.control.RadioButton;
import managers.PilotManager;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.imgproc.Imgproc;
import org.opencv.videoio.VideoCapture;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import utils.Utils;

public class VideoDisplayController {
    // the FXML button
    @FXML
    private Button button;
    // the FXML image view
    @FXML
    private ImageView currentFrame;
    @FXML
    private RadioButton webcamRB;

    @FXML
    private RadioButton filterRB;


    // a timer for acquiring the video stream
    private ScheduledExecutorService timer;
    // the OpenCV object that realizes the video capture
    private VideoCapture capture = new VideoCapture();
    // a flag to change the button behavior
    private boolean cameraActive = true;
    // the id of the camera to be used
    private Thread TIR;

    private static int cameraId = 0;

    private PilotManager pm;
    private Runnable frameGrabber;
    private Boolean devMode = false;
    private Boolean filter = false;

    public void setPM(PilotManager pm){
        this.pm = pm;
    }

    /**
     * The action triggered by pushing the button on the GUI
     *
     * @param event
     *            the push button event
     */
    @FXML
    protected void startCamera(ActionEvent event)
    {
        camera();
    }

    private void camera()
    {
        if(!devMode) // drone cam
        {
            if (!this.cameraActive)
            {
                if(pm != null){
                    this.cameraActive = true;
                    //TIR = new Thread((ImageRecognition) IR);
                    //TIR.start();

                    // grab a frame every 33 ms (30 frames/sec)
                    frameGrabber = new Runnable() {
                        @Override
                        public void run()
                        {
                            // effectively grab and process a single frame
                            Mat frame = new Mat();
                            if(filter == true){
                                grabFrame();
                            }
                            else
                            {
                               frame = bufferedImageToMat(pm.getImg());
                            }
                            // convert and show the frame
                            Image imageToShow = Utils.mat2Image(frame);
                            updateImageView(currentFrame, imageToShow);
                        }
                    };

                    this.timer = Executors.newSingleThreadScheduledExecutor();
                    this.timer.scheduleAtFixedRate(frameGrabber, 0, 33, TimeUnit.MILLISECONDS);

                    // update the button content
                    this.button.setText("Stop Camera");
                }
            }
            else
            {
                // the camera is not active at this point
                this.cameraActive = false;
                // update again the button content
                this.button.setText("Start Camera");
                // stop the timer
                this.stopAcquisition();
            }
        }
        else // start devMode
        {
            if (!this.cameraActive)
            {
                // start the video capture
                this.capture.open(cameraId);

                // is the video stream available?
                if (this.capture.isOpened())
                {
                    this.cameraActive = true;

                    // grab a frame every 33 ms (30 frames/sec)
                    Runnable frameGrabber = new Runnable() {

                        @Override
                        public void run()
                        {
                            // effectively grab and process a single frame
                            Mat frame = grabFrame();
                            // convert and show the frame
                            Image imageToShow = Utils.mat2Image(frame);
                            updateImageView(currentFrame, imageToShow);
                        }
                    };

                    this.timer = Executors.newSingleThreadScheduledExecutor();
                    this.timer.scheduleAtFixedRate(frameGrabber, 0, 33, TimeUnit.MILLISECONDS);

                    // update the button content
                    this.button.setText("Stop Camera");
                }
                else
                {
                    // log the error
                    System.err.println("Impossible to open the camera connection...");
                }
            }
		else
            {
                // the camera is not active at this point
                this.cameraActive = false;
                // update again the button content
                this.button.setText("Start Camera");

                // stop the timer
                this.stopAcquisition();
            }
        }
    }

    @FXML
    protected void setDevMode(ActionEvent event)
    {
        this.stopAcquisition();
        if( webcamRB.isSelected() )
        {
            devMode = true;
        }
        else{
            devMode = false;
        }
        cameraActive = false;
        camera();
    }

    @FXML
    protected void setFilter(ActionEvent event){
        if(filterRB.isSelected()){
            filter = true;
        }
        else
            filter = false;
    }

    /**
     * Get a frame from the opened video stream (if any)
     *
     * @return the {@link Mat} to show
     */
    private Mat grabFrame()
    {
        // init everything
        Mat frame = new Mat();

        if(devMode){
            // check if the capture is open
            if (this.capture.isOpened())
            {
                try
                {
                    // read the current frame
                    this.capture.read(frame);

                    // if the frame is not empty, process it
                    if (!frame.empty() && filter == true)
                    {
                        Imgproc.cvtColor(frame, frame, Imgproc.COLOR_BGR2GRAY);
                    }

                }
                catch (Exception e)
                {
                    // log the error
                    System.err.println("Exception during the image elaboration: " + e);
                }
            }
        }
        else
            if(pm != null){
            BufferedImage BI = pm.getImg();
            frame = bufferedImageToMat(BI);
            if (!frame.empty())
                {
                    Imgproc.cvtColor(frame, frame, Imgproc.COLOR_BGR2GRAY);
                }
            }
        return frame;
    }

    private Mat bufferedImageToMat(BufferedImage bi)
    {
        Mat mat = new Mat(bi.getHeight(), bi.getWidth(), CvType.CV_8UC3);
        byte[] data = ((DataBufferByte) bi.getRaster().getDataBuffer()).getData();
        mat.put(0, 0, data);
        return mat;
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
    }
}
