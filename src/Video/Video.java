package Video;

import de.yadrone.base.IARDrone;
import de.yadrone.base.video.ImageListener;

import java.awt.image.BufferedImage;

public class Video {
private  BufferedImage img = null;

    private Video(IARDrone drone){
        drone.getVideoManager().addImageListener(new ImageListener() {
            @Override
            public void imageUpdated(BufferedImage bufferedImage) {
                Video.this.setImg(bufferedImage);
            }
        });
    }
    private synchronized void setImg(BufferedImage img) {
        this.img = img;
    }
}
