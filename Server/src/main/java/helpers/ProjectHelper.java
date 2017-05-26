package helpers;

import model.Bar;
import model.Point;
import model.Project;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

/**
 * Created by podsh on 24.05.2017.
 */
public class ProjectHelper {

    public List<Point> getPoints(List<Bar> bars) {
        List<Point> list = new ArrayList<>();
        bars.stream()
                .flatMap(bar -> Stream.of(bar.getFirstPoint(), bar.getSecondPoint()))
                .forEach(point -> {
                    if (!list.contains(point)) {
                        list.add(point);
                    }
                });
        return list;
    }
}
