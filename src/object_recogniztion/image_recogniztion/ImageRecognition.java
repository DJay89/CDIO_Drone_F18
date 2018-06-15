package object_recogniztion.image_recogniztion;

import com.xuggle.xuggler.Utils;
import object_recogniztion.RingFinder.RedRingFinder;
import object_recogniztion.image_recogniztion.squareDetect.FilterBackground;
import object_recogniztion.image_recogniztion.squareDetect.SquareDetect;
import org.opencv.core.Mat;
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
    private RedRingFinder rr;
    private SquareDetect sd;
    private FilterBackground fb;

    //init
    public ImageRecognition() {
        this.qr = new QRscanner();
        this.rr = new RedRingFinder();
        this.sd = new SquareDetect();
        this.fb = new FilterBackground();

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
        rr.findRedRing( getFrame() );
        ret.found = rr.foundRing();
        if(ret.found){
            ret.x = rr.getX();
            ret.y = rr.getY();
            ret.resutalt ="found red ring";
        }
        return ret;
    }

    public imageReturn squareDetect() {

        imageReturn ret = new imageReturn();
        Mat newFrame = utils.Utils.bufferedImageToMat( getFrame() );
        //newFrame = fb.filterBackGround( newFrame, 1 );
        //ret.found = sd.findQrCenter( newFrame );
        ret.found = sd.findQrCenter( newFrame );

        if ( ret.found ){
            ret.x = sd.getCenterOfRectX();
            ret.y = sd.getCenterOfRectY();
            ret.resutalt = "QR Code Found";
        }
        return ret;

    }

    //add more after here

    public void emptyCenterCordStack () {
        rr.emptyStack();
    }


}



