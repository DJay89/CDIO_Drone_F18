package object_recogniztion.video_test;

import de.yadrone.base.IARDrone;
import de.yadrone.base.video.ImageListener;
import object_recogniztion.image_recogniztion.ImageRecognition;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class VideoTest extends JFrame {

    private BufferedImage image;
    private IARDrone drone;
    private ImageRecognition imageRecog;

    public VideoTest(IARDrone drone) {

        this.drone = drone;
        imageRecog = new ImageRecognition(this);

        setSize(1280, 720);
        setVisible(true);

        imageCapture();
    }

    private synchronized void setImg(BufferedImage img) {
        image = img;
    }

    public synchronized BufferedImage getbufImg() {
        return image;
    }

    private void imageCapture() {
        drone.getVideoManager().addImageListener(new ImageListener() {
            @Override
            public void imageUpdated(BufferedImage bufferedImage) {

                /*

                 */
                setImg(bufferedImage);

                SwingUtilities.invokeLater(new Runnable() {
                    public void run() {
                        repaint();
                        imageRecog.run();
                    }
                });

                /*

                 */
                //VideoTest.this.setImg(bufferedImage);
            }
        });

    }

    public synchronized void paint(Graphics g) {
        if (image != null)
            g.drawImage(image, 0, 0, image.getWidth(), image.getHeight(), null);
    }

}
