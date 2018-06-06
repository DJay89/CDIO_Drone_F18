package object_recogniztion.qr_scanner;

        import com.google.zxing.*;
        import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
        import com.google.zxing.common.HybridBinarizer;
        import com.google.zxing.oned.rss.FinderPattern;
        import com.google.zxing.qrcode.QRCodeReader;
        import de.yadrone.apps.paperchase.QRCodeScanner;
        import java.awt.Image;
        import java.awt.image.BufferedImage;
        import java.awt.image.DataBufferByte;
        import java.util.EnumMap;
        import java.util.Hashtable;
        import java.util.logging.Level;
        import java.util.logging.Logger;

        import object_recogniztion.misc.ImageConverter;
        import org.opencv.core.Mat;
        import org.opencv.imgproc.Imgproc;

/**
 *
 * @author Bruger
 */
public class QRscanner {

    private String qrTxt = "";
    private int x = 0;
    private int y = 0;
    private ImageConverter IC;

    public String get_qr_txt(){
        return qrTxt;
    }
    public double theta;

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public boolean decodeQR(Mat mat)
    {
        Image image = Mat2BufferedImage(mat);

        LuminanceSource ls = new BufferedImageLuminanceSource((BufferedImage)image);
        HybridBinarizer hb = new HybridBinarizer(ls);
        BinaryBitmap bm = new BinaryBitmap(hb);

        QRCodeReader qrr = new QRCodeReader();
        QRCodeScanner qrcs = new QRCodeScanner();

        try {
            Result res = qrr.decode(bm);
            qrTxt = res.getText();

            int x = 0;
            int y = 0;
            ResultPoint[] resPoints = res.getResultPoints();
            for(int i = 0; i < resPoints.length; i++)
            {
                ResultPoint rp = resPoints[i];
                x += Math.round( rp.getX() );
                y += Math.round( rp.getY() );
                System.out.println("["+i+"]: x = " + rp.getX() + "| y = " + rp.getY());
            }
            x = Math.round(x/res.getResultPoints().length);
            y = Math.round(y/res.getResultPoints().length);

        } catch (NotFoundException | ChecksumException | FormatException ex) {
            //Logger.getLogger(QRscanner.class.getName()).log(Level.SEVERE, null, ex);
            qrTxt = null;
            return false;
        }
        return true;
    }

    public boolean imageUpdated(Mat mat) {
        BufferedImage image = Mat2BufferedImage(mat);
        LuminanceSource source = new BufferedImageLuminanceSource(image);
        BinaryBitmap bitmap = new BinaryBitmap(new HybridBinarizer(source));
        MultiFormatReader reader = new MultiFormatReader();
        double theta = 0.0D / 0.0;
        Result scanResult = null;
        try {
            scanResult = reader.decode(bitmap);
            qrTxt = scanResult.getText();
            ResultPoint[] points = scanResult.getResultPoints();
            ResultPoint a = points[1];
            ResultPoint b = points[2];
            double z = (double)Math.abs(a.getX() - b.getX());
            double x = (double)Math.abs(a.getY() - b.getY());
            theta = Math.atan(x / z);
            theta *= 57.29577951308232D;
            if (b.getX() < a.getX() && b.getY() > a.getY()) {
                theta = 180.0D - theta;
            } else if (b.getX() < a.getX() && b.getY() < a.getY()) {
                theta += 180.0D;
            } else if (b.getX() > a.getX() && b.getY() < a.getY()) {
                theta = 360.0D - theta;
            }
        } catch (NotFoundException e) {
            //e.printStackTrace();
            return false;
        }
        return true;
    }

    public void decodeQrWithFilters(Mat mat)
    {
        boolean done = false;
        int filterAmount = 45;
        do{
            if(filterAmount > 120) return;
            Mat temp = new Mat();
            mat.copyTo(temp);

            if(filterAmount != 45){
                Imgproc.cvtColor(temp, temp, Imgproc.COLOR_RGB2GRAY);
                Imgproc.threshold(temp, temp, filterAmount, 255, Imgproc.THRESH_BINARY);
            }

            Image image = Mat2BufferedImage(temp);
            LuminanceSource ls = new BufferedImageLuminanceSource((BufferedImage)image);
            HybridBinarizer hb = new HybridBinarizer(ls);
            BinaryBitmap bm = new BinaryBitmap(hb);
            QRCodeReader qrr = new QRCodeReader();

            try {
                Result res = qrr.decode(bm);
                String tmp = res.getText();
                if(tmp.isEmpty() && tmp.length() > 3)
                {
                    qrTxt = tmp;
                    done = true;
                }
                else
                {
                    filterAmount += 5;
                }
            } catch (NotFoundException | ChecksumException | FormatException ex) {
                Logger.getLogger(QRscanner.class.getName()).log(Level.SEVERE, null, ex);
            }
        }while(done);
    }

    private BufferedImage Mat2BufferedImage(Mat m)
    {
        //source: http://answers.opencv.org/question/10344/opencv-java-load-image-to-gui/
        //Fastest code
        //The output can be assigned either to a BufferedImage or to an Image

        int type = BufferedImage.TYPE_BYTE_GRAY;
        if (m.channels() > 1) {
            type = BufferedImage.TYPE_3BYTE_BGR;
        }
        int bufferSize = m.channels() * m.cols() * m.rows();
        byte[] b = new byte[bufferSize];
        m.get(0, 0, b); // get all the pixels
        BufferedImage image = new BufferedImage(m.cols(), m.rows(), type);
        final byte[] targetPixels = ((DataBufferByte) image.getRaster().getDataBuffer()).getData();
        System.arraycopy(b, 0, targetPixels, 0, b.length);
        return image;
    }
}
