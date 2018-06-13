package object_recogniztion.image_recogniztion;

import controller.Drone;
import object_recogniztion.RingFinder.RedRingFinder;
import utils.imageReturn;
import object_recogniztion.qr_scanner.QRscanner;
import java.awt.image.BufferedImage;

public class ImageRecognition  implements Runnable{
    /**
     * All returns Her should be a imageReturn Type
     * To make a standardized way of checking data
     * Look at qrScan() as example
     */

    //Class Variables
    private BufferedImage frame;

    //Object class we have made
    private QRscanner qr;
    private RedRingFinder rr;
    private Drone drone;
    private int type;
    public final static int QR = 1;
    public final static int RING = 2;
    public final static int SQUARD = 3;


    //init
    public ImageRecognition(Drone drone) {
        this.qr = new QRscanner();
        this.rr = new RedRingFinder();
        this.drone = drone;
    }

    @Override
    public void run() {
        try{
            setFrame(drone.getImg());
            imageReturn ir = new imageReturn();
            switch (type)
            {
                case 1: // Do Qr scan
                {
                    ir = qrScan();
                }
                break;

                case 2: // find red ring
                {
                    ir = rrScan();
                }
                break;
                case 3: // TODO Squard
                {
                    //insert more here
                }
                break;
            }
            // pass info to drone
            drone.setRetValues(ir);

        } catch (NullPointerException ex){
            System.out.println("No pic.");
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                return;
            }
        }
    }

    public void setType(int type){
        this.type = type;
    }


    //Class Needed functions
    public BufferedImage getFrame() {
        return frame;
    }
    public void setFrame(BufferedImage frame) {
        this.frame = frame;
    }

    //function to execute qr scanning
    public imageReturn qrScan()
    {
        imageReturn ret = new imageReturn();
        ret.name = "QR";
        ret.found = false;
        Boolean tmp = qr.decodeQR( getFrame() );
        if(tmp){
            ret.found = true;
            ret.x = qr.getX();
            ret.y = qr.getY();
            ret.resutalt = qr.get_qr_txt();
        }
        return ret;
    }

    //function to execute qr scanning
    public imageReturn rrScan()
    {
        imageReturn ret = new imageReturn();
        ret.name = "RR";
        ret.found = false;
        rr.findRedRing( getFrame() );
        ret.found = rr.getFound();
        if(ret.found){
            ret.x = rr.getX();
            ret.y = rr.getY();
            ret.resutalt ="found red ring";
        }
        return ret;
    }

    //add more after here


}



