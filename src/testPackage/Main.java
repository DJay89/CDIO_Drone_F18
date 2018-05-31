package testPackage;

import de.yadrone.base.ARDrone;
import de.yadrone.base.IARDrone;
import de.yadrone.base.command.CommandManager;

public class Main {
    IARDrone drone = null;

    public static void main(String[] args) {
        System.out.println("YaDrone is now a jar package");





    }

    private void RunDrone() {
        drone = new ARDrone();
        CommandManager cmd = drone.getCommandManager();
    }

}
