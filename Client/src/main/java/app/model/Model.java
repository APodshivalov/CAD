package app.model;

import app.Controller;
import app.utils.Colors;
import javafx.scene.input.MouseEvent;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by podsh on 08.04.2017.
 * Стержневая модель. Отвечает за хранение информации о конструкции.
 */
public class Model {
    private List<Bar> bars;
    private List<Point> points;
    private ArrayOfMaterial arrayOfMaterial;
    private Controller controller;
    private Material currentMaterial;

    public Model(Controller controller) {
        this.controller = controller;
        bars = new ArrayList<>();
        points = new ArrayList<>();
        arrayOfMaterial = new ArrayOfMaterial();
    }

    /**
     * Отрисовка модели
     */
    public void draw() {
        points.forEach(point -> point.draw(controller));
        bars.forEach(bar -> bar.draw(controller));
    }

    /**
     * Добавить стержень
     *
     * @param bar Стержень
     */
    public void addBar(Bar bar) {
        if (bars.stream().noneMatch(bar::equals)) {
            bars.add(bar);
            bar.setMaterial(currentMaterial);
        }
    }

    public Point findNearbyPoint(double x, double y) {
        for (Point p : points) {
            if (p.near(x, y)) {
                return p;
            }
        }
        return null;
    }

    public Point findPoint(double x, double y) {
        for (Point p : points) {
            if (p.equals(x, y)) {
                return p;
            }
        }
        return null;
    }

    public void addPoint(Point point) {
        points.add(point);
    }

    public void select(double x, double y, double x1, double y1) {
        for (Bar bar : bars) {
            bar.setIsSelected(bar.between(x, y, x1, y1));
        }
    }

    public void draw(MouseEvent mouseEvent) {
        points.forEach(point -> point.draw(controller, mouseEvent));
        bars.forEach(bar -> bar.draw(controller));
    }

    public void setCurrentMaterial(Material selectedItem) {
        bars.stream().filter(Bar::isSelected).forEach(bar -> {
            bar.setMaterial(selectedItem);
            bar.setIsSelected(false);
        });
        currentMaterial = selectedItem;
        arrayOfMaterial.add(selectedItem);
    }

    public ArrayOfMaterial getArrayOfMaterial() {
        return arrayOfMaterial;
    }
}
