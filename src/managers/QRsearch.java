package managers;

import de.yadrone.base.command.CommandManager;
import object_recogniztion.image_recogniztion.ImageRecognition;
import object_recogniztion.qr_scanner.QRscanner;

import java.awt.*;
import java.util.ArrayList;

public class QRsearch {

    private PilotManager cmd;
    private ImageRecognition IR;
    private long time = 5000;
    private int resultQR = 0;
    private int result = -1;

    public QRsearch(ImageRecognition IR) {
    this.IR = IR;
    }

    public int searchLvlZero() {

        if (IR.getqr().get_qr_txt() == null) {
            cmd.spinLeft(time);
            return 0;
        }

      //  else if (result + 1 == qr.get_qr_txt()) {

          else {
            result = Integer.parseInt(IR.getqr().get_qr_txt());
            return 1;
        }
        /*else {
            searchLvlOne();
            return 0;
        }
*/
    }


    public String searchLvlOne() {


        cmd.land();
        return "LANDING SUCCESSFUL";
        /*
        if ( qr.get_qr_txt().equals(null) ) {

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
*/
    }

}
