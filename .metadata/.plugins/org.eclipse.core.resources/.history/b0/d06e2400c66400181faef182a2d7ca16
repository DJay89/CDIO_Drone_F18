package testPackage;

import de.yadrone.apps.tutorial.TutorialAttitudeListener;
import de.yadrone.apps.tutorial.TutorialCommander;
import de.yadrone.apps.tutorial.TutorialVideoListener;
import de.yadrone.base.ARDrone;
import de.yadrone.base.IARDrone;
import de.yadrone.base.command.CommandManager;
import de.yadrone.base.command.FlightAnimation;
import de.yadrone.base.command.FlightAnimationCommand;
import de.yadrone.base.exception.ARDroneException;
import de.yadrone.base.exception.IExceptionListener;
import de.yadrone.base.navdata.VideoListener;
import de.yadrone.base.video.ImageListener;

public class Main {


    public static void main(String[] args) {
        System.out.println("YaDrone is now a jar package");
        IARDrone drone = null;
        try {

            drone = new ARDrone();

            CommandManager cmd = drone.getCommandManager();
            drone.start();
            drone.addExceptionListener(new IExceptionListener() {
                public void exeptionOccurred(ARDroneException exc)
                {
                    exc.printStackTrace();
                }
            });
            //ImageListener listener = drone.getVideoManager();
            new TutorialAttitudeListener(drone);

            TutorialCommander commander = new TutorialCommander(drone);

            // Tutorial Section 3
            TutorialVideoListener testVid =new TutorialVideoListener(drone);
            
            // VideoListener test = new VideoListener(drone);
            cmd.doFor(10000);

            FlightAnimationCommand AniCmd;
            //drone.getVideoManager().addImageListener();


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

    private void RunDrone() {

    }
}
