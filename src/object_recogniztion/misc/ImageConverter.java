package object_recogniztion.misc;

import org.opencv.core.CvType;
import org.opencv.core.Mat;

import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;

public class ImageConverter {

    public Mat convertImage2Mat(BufferedImage img){
    	System.out.println("debug 1");
        byte[] data = ((DataBufferByte) img.getRaster().getDataBuffer()).getData();
    	System.out.println("debug 2");
        Mat mat = new Mat(img.getHeight(),img.getWidth(), CvType.CV_8UC3);
    	System.out.println("debug 3");
        mat.put(0,0,data);
    	System.out.println("debug 4");


        return mat;
    }

    public BufferedImage convertMat2BufferedImage(Mat frame){

        BufferedImage Bi;
        int type;

        byte[] data = new byte[frame.width() * frame.height() * (int)frame.elemSize()];
        frame.get(0, 0, data);

        if(frame.channels() == 1)
            type = BufferedImage.TYPE_BYTE_GRAY;
        else
            type = BufferedImage.TYPE_3BYTE_BGR;

        Bi = new BufferedImage(frame.width(), frame.height(), type);
        Bi.getRaster().setDataElements(0, 0, frame.width(), frame.height(), data);

        return Bi;
    }

}
