package MasterALGO;

import managers.PilotManager;
import managers.QRsearch;

public class masterSeachAlgo implements Runnable{

    private PilotManager pm;
    private QRsearch qr;
    private Thread qrT, pmT;

    public masterSeachAlgo(PilotManager pm){
        this.pm = pm;
        this.qr = new QRsearch(pm);
        qrT = new Thread(qr);
        pmT = new Thread(pm);
    }

    public void run(){

    }
}
