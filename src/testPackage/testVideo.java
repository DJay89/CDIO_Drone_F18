package testPackage;

import de.yadrone.base.ARDrone;
import de.yadrone.base.IARDrone;
import de.yadrone.base.command.CommandManager;
import de.yadrone.base.exception.ARDroneException;
import de.yadrone.base.exception.IExceptionListener;
import de.yadrone.base.video.ImageListener;
import managers.PilotManager;

import java.awt.image.BufferedImage;

public class testVideo {

    public final static int IMAGE_WIDTH = 1280; // 640 or 1280
    public final static int IMAGE_HEIGHT = 720; // 360 or 720
    public static IARDrone drone = null;
    public BufferedImage bufImgOut;
    public static void main(String[] args) {
        System.out.println("YaDrone is now a jar package");

        try {

            drone = new ARDrone();
            drone.start();
            PilotManager pilotTest = new PilotManager(drone);
            CommandManager cmd = drone.getCommandManager();

            drone.addExceptionListener(new IExceptionListener() {
                public void exeptionOccurred(ARDroneException exc) {
                    exc.printStackTrace();
                }
            });


        }catch (Exception exp) {
            exp.printStackTrace();
        }
        finally
        {
            if (drone != null)
                drone.stop();

            System.exit(0);
        }
    }
    private void imageCapture() {
        drone.getVideoManager().addImageListener(new ImageListener() {
            @Override
            public void imageUpdated(BufferedImage bufferedImage) {
                testVideo.this.setImg(bufferedImage);
            }
        });
    }

    private synchronized void setImg(BufferedImage img) {
        // System.err.println("Billede opdateret.");
        bufImgOut = img;
    }
}
