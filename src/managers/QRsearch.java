package managers;

import object_recogniztion.image_recogniztion.ImageRecognition;

public class QRsearch implements Runnable{

    private PilotManager pm;
    private ImageRecognition IR;
    private long time = 25;
    private int resultQR = 0;
    private int result = -1;


    public QRsearch(ImageRecognition IR, PilotManager pm) {
    this.IR = IR;
    this.pm = pm;
    }

    public void run(){
        searchLvlZero();
    }

    public int searchLvlZero() {
        long spinTime = System.currentTimeMillis() + 20000;
            while ( System.currentTimeMillis() - spinTime <= 0 || IR.getqr().get_qr_txt() == null)
            {
                pm.move3D(2, -1, 0 , 20, 500);
                pm.hover(5);
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            if (IR.getqr().get_qr_txt() != null){
                System.out.println("Worked");
                pm.land();
                return 1;
            }
        pm.land();
        return 0;


      //  else if (result + 1 == qr.get_qr_txt()) {
        /*
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
