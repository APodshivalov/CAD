package app.model;

import app.Controller;
import javafx.scene.canvas.GraphicsContext;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by podsh on 08.04.2017.
 * Стержневая модель. Отвечает за хранение информации о конструкции.
 */
public class Model {
    private List<Bar> bars;
    private Controller controller;
    private GraphicsContext gc;

    public Model(Controller controller) {
        this.controller = controller;
        gc = controller.getCanvas().getGraphicsContext2D();
        bars = new ArrayList<>();
    }

    /**
     * Отрисовка модели
     */
    public void draw() {
        bars.forEach(bar -> bar.draw(controller));
    }

    /**
     * Добавить стержень
     *
     * @param bar Стержень
     */
    public void addBar(Bar bar) {
        if(bars.stream().noneMatch(bar::equals)) {
            bars.add(bar);
        }
    }

    public Point findNearbyPoint(double x, double y) {
        for (Bar bar : bars) {
            if (bar.getFirstPoint().near(x,y)){
                return bar.getFirstPoint();
            }
            if (bar.getSecondPoint().near(x,y)){
                return bar.getSecondPoint();
            }
        }
        return null;
    }
}
