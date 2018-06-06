package managers;

import object_recogniztion.image_recogniztion.ImageRecognition;

public class QRsearch {

    private PilotManager pm;
    private ImageRecognition IR;
    private long time = 5000;
    private int resultQR = 0;
    private int result = -1;

    public QRsearch(ImageRecognition IR, PilotManager pm) {
    this.IR = IR;
    this.pm = pm;
    }

    public int searchLvlZero() {

        if (IR.getqr().get_qr_txt() == null) {
            pm.spinLeft(time);
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


        pm.land();
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
