package object_recogniztion.image_recogniztion;

import utils.imageReturn;
import object_recogniztion.qr_scanner.QRscanner;

import java.awt.image.BufferedImage;

public class ImageRecognition {
    /**
     * All returns Her should be a imageReturn Type
     * To make a standardized way of checking data
     * Look at qrScan() as example
     */

    //Class Variables
    private BufferedImage frame;

    //Object class we have made
    private QRscanner qr;

    //init
    public ImageRecognition() {
        this.qr = new QRscanner();
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
        if(tmp) {
            ret.found = true;
            ret.x = qr.getX();
            ret.y = qr.getY();
            ret.resutalt = qr.get_qr_txt();
        }
        return ret;
    }

    //add more after here


}



