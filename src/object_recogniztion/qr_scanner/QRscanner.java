package object_recogniztion.qr_scanner;

        import com.google.zxing.*;
        import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
        import com.google.zxing.common.HybridBinarizer;
        import com.google.zxing.qrcode.QRCodeReader;
        import java.awt.Image;
        import java.awt.image.BufferedImage;
        import java.awt.image.DataBufferByte;
        import java.util.logging.Level;
        import java.util.logging.Logger;

        import de.yadrone.apps.paperchase.QRCodeScanner;
        import org.opencv.core.Mat;
        import org.opencv.imgproc.Imgproc;
        import utils.Utils;

/**
 *
 * @author Patrick
 */
public class QRscanner {

    private String qr_return = "";
    private int x = 0;
    private int y = 0;

    public String get_qr_txt(){
        return qr_return;
    }

    public int getX() {
        return this.x;
    }

    public int getY() {
        return this.y;
    }

    public boolean decodeQR(BufferedImage BI)
    {
        LuminanceSource bufferedImageLuminanceSource = new BufferedImageLuminanceSource(BI);
        BinaryBitmap binaryBitmap = new BinaryBitmap(new HybridBinarizer(bufferedImageLuminanceSource));
        QRCodeReader qrCodeReader = new QRCodeReader();

        try {
            Result result = qrCodeReader.decode(binaryBitmap);
            this.qr_return = result.getText();

            int x = 0;
            int y = 0;
            ResultPoint[] resPoints = result.getResultPoints();
            for(int i = 0; i < resPoints.length; i++)
            {
                ResultPoint rp = resPoints[i];
                x += Math.round( rp.getX() );
                y += Math.round( rp.getY() );
                //System.out.println(i+" - "+"x: "+rp.getX()+"y: "+rp.getY());
            }
            this.x = Math.round(x/result.getResultPoints().length);
            this.y = Math.round(y/result.getResultPoints().length);

        } catch (NotFoundException | ChecksumException | FormatException ex) {
            //Logger.getLogger(QRscanner.class.getName()).log(Level.SEVERE, null, ex);
            qr_return = "";
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

            Image image = Utils.mat2BufferedImage(temp);
            LuminanceSource ls = new BufferedImageLuminanceSource((BufferedImage)image);
            HybridBinarizer hb = new HybridBinarizer(ls);
            BinaryBitmap bm = new BinaryBitmap(hb);
            QRCodeReader qrr = new QRCodeReader();

            try {
                Result res = qrr.decode(bm);
                String tmp = res.getText();
                if(tmp.isEmpty() && tmp.length() > 3)
                {
                    qr_return = tmp;
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
}
