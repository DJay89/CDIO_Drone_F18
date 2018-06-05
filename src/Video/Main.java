package Video;

import javafx.stage.Stage;

public class Main {

    public static void main(String [] arcs){
        Stage s = new Stage();
        VideoDisplay vd = new VideoDisplay();
        vd.start(s);
    }
}
