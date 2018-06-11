package object_recogniztion.image_recogniztion;

import object_recogniztion.imageReturn;
import object_recogniztion.qr_scanner.QRscanner;

import java.awt.image.BufferedImage;

public class ImageRecognition {

    private QRscanner qr;
    private BufferedImage frame;
    public imageReturn info;

    public ImageRecognition() {
        this.qr = new QRscanner();
    }

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
        info = ret;
        return ret;
    }


    public BufferedImage getFrame() {
        return frame;
    }
    public void setFrame(BufferedImage frame) {
        this.frame = frame;
    }
}



