package managers;

import de.yadrone.base.command.CommandManager;

import java.util.ArrayList;

public class QRsearch {

    private CommandManager cmd;
    private long time = 5000;
    private int resultQR = 0;
    private int result = -1;

    public QRsearch() {

    }

    public boolean searchLvlZero() {


        //// RUN QR CODE LISTENER
        cmd.hover();

       if (  qr not localized ) {
            cmd.spinLeft(time);
        }

        else {
            // SCAN QR and save result in list

            if ( result+1 == QRresult )
            {
                result = QRresult;
                // fly through ring //
            }

            else {
                searchLvlOne();
            }

        }

    }


    public void searchLvlOne() {


        // RUN QR CODE LISTENER //

        if ( qr not localized ) {
            // generate random number and fly according to these pin points. spin 360 at every stop.

        }

        else {
            // SCAN QR and save result in list
            if ( result+1 == QRresult )
            {
                result = QRresult;
                // fly through ring //
            }

            else {
                    searchLvlTwo();
            }
        }
    }


    public boolean searchLvlTwo() {

    }

}
