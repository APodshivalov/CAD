package app.utils;

import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by podsh on 26.04.2017.
 */
public class Colors {
    private static List<Color> colors;
    private static int index = 0;
    static {
        colors = new ArrayList<>();
        colors.add(Color.web("#000099"));
        colors.add(Color.web("#006600"));
        colors.add(Color.web("#666600"));
        colors.add(Color.web("#339999"));
        colors.add(Color.web("#990099"));
        colors.add(Color.web("#003333"));
        colors.add(Color.web("#00CC99"));
        colors.add(Color.web("#CCCC66"));
        colors.add(Color.web("#330033"));
    }
    public static Color getNextColor() {
        if (index == colors.size()-1) {
            index = 0;
        }
        return colors.get(index++);
    }
}
