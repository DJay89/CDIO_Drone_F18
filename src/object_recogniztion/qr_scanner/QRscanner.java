package object_recogniztion.qr_scanner;

        import com.google.zxing.*;
        import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
        import com.google.zxing.common.HybridBinarizer;
        import com.google.zxing.qrcode.QRCodeReader;
        import de.yadrone.apps.paperchase.QRCodeScanner;
        import java.awt.Image;
        import java.awt.image.BufferedImage;
        import java.awt.image.DataBufferByte;
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
                rp.getX();
                rp.getY();
                System.out.println("["+i+"]: x=");
            }

            for(ResultPoint rp: res.getResultPoints()){
                x += rp.getX();
                y += rp.getY();
            }
            x = (int)(x/res.getResultPoints().length);
            y = (int)(y/res.getResultPoints().length);

        } catch (NotFoundException | ChecksumException | FormatException ex) {
            //Logger.getLogger(QRscanner.class.getName()).log(Level.SEVERE, null, ex);
            qrTxt = null;
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
