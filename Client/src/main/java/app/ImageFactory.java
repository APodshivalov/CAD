package app;

import javafx.scene.image.Image;

/**
 * Created by APodshivalov on 21.04.2017.
 */
public class ImageFactory {
    public static Image getImage(String imageName, int i) {
        return new Image("images/reactions/mini" + imageName + "grad" + i + ".png", 40,40,true,false);
    }
}
