package managers;

import object_recogniztion.image_recogniztion.ImageRecognition;

public class QRsearch implements Runnable{

    private PilotManager pm;
    private ImageRecognition IR;
    private long time = 25;



    public QRsearch(ImageRecognition IR, PilotManager pm) {
        this.IR = IR;
        this.pm = pm;
    }

    public void run(){
        searchLvlZero();
    }

    // Spins 360 degrees and searches for QR-CODE and Red Rings. //
    public int searchLvlZero(long searchTime) {

        long spinTime = System.currentTimeMillis() + searchTime;
        String temp = IR.getqr().get_qr_txt();
        while ( System.currentTimeMillis() - spinTime <= 0 && temp.equals("")) {

            System.out.println("Search Level 0: Searching for QR and Red Rings");
            pm.move3D(2, -1, 0 , 20, 500);
            pm.hover(5);

            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            temp = IR.getqr().get_qr_txt();
        }

        if (!temp.equals("")) {
            System.out.println("QR Found\n" + "Result: " + temp);
            pm.land();
            return 1;
        }

        System.out.println("Nothing found initiating Search Level 1");
        pm.land();
        return 0;
        // return searchLvlOne();


    }


    public int searchLvlOne(long searchTime) {

        long spinTime = System.currentTimeMillis() + searchTime;
        String temp = IR.getqr().get_qr_txt();
        while (System.currentTimeMillis() - spinTime <= 0 && temp.equals("")) {

            System.out.println("Search Level 1: Searching for QR and Red Rings");
            pm.move3D(2, -1, 0, 20, 500);
            pm.hover(5);

            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            temp = IR.getqr().get_qr_txt();
        }

        if (!temp.equals("")) {
            System.out.println("QR Found\n" + "Result: " + temp);
            pm.land();
            return 1;
        }

        System.out.println("Nothing found initiating Search Level 2");
        pm.land();
        return 0;
        // return searchLvlTwo();
    }

    public void searchLvlTwo() {

    }

}
