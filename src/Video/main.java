package Video;

import de.yadrone.base.ARDrone;
import de.yadrone.base.IARDrone;

public class main {
    public static void main(String[] args) {
        IARDrone drone = null;
        try {
            drone = new ARDrone();
            drone.start();
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
        finally {
            if (drone != null){
                drone.stop();
            }
            System.exit(0);
        }
    }
}
