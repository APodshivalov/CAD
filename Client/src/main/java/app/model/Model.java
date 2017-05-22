package app.model;

import app.Controller;
import javafx.scene.input.MouseEvent;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by podsh on 08.04.2017.
 * Стержневая модель. Отвечает за хранение информации о конструкции.
 */
public class Model {
    private Project project;
    private List<Point> points;
    private ArrayOfMaterial arrayOfMaterial;
    private ArrayOfCut arrayOfCut;
    private Controller controller;
    private Material currentMaterial;
    private Cut currentCut;

    public Model(Controller controller) {
        this.controller = controller;
        project = new Project();
        points = new ArrayList<>();
        arrayOfMaterial = new ArrayOfMaterial();
        arrayOfCut = new ArrayOfCut();
    }

    /**
     * Отрисовка модели
     */
    public void draw() {
        project.getBars().forEach(bar -> bar.draw(controller));
        points.forEach(point -> point.draw(controller));
    }

    /**
     * Добавить стержень
     *
     * @param bar Стержень
     */
    public void addBar(Bar bar) {
        project.add(bar, currentMaterial, currentCut);
    }

    public Point findNearbyPoint(double x, double y) {
        for (Point p : points) {
            if (controller.getCoordinateUtils().isNear(p,x,y)){
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
        if (!points.contains(point)){
            points.add(point);
        }
    }

    public void select(double x, double y, double x1, double y1) {
        project.setSelect(x,y,x1,y1);
        points.forEach(point -> point.select(x,y,x1,y1));
    }

    public void draw(MouseEvent mouseEvent) {
        project.draw(controller);
        points.forEach(point -> point.draw(controller, mouseEvent));
    }

    public void setCurrentMaterial(Material selectedItem) {
        project.setCurrentMaterial(selectedItem);
        currentMaterial = selectedItem;
        arrayOfMaterial.add(selectedItem);
    }

    public void setCurrentCut(Cut selectedItem) {
        project.setCurrentCut(selectedItem);
        currentCut = selectedItem;
        arrayOfCut.add(selectedItem);
    }

    public ArrayOfMaterial getArrayOfMaterial() {
        return arrayOfMaterial;
    }

    public ArrayOfCut getArrayOfCut() {
        return arrayOfCut;
    }

    public Project getProject() {
        return project;
    }

    public void clear() {
        project = new Project();
        points = new ArrayList<>();
        arrayOfMaterial = new ArrayOfMaterial();
        arrayOfCut = new ArrayOfCut();
    }

    public List<Point> getPoints() {
        return points;
    }

    public void deleteSelected() {
        project.getBars().removeIf(Bar::isSelected);
        points.removeIf(Point::isSelected);
    }

    public void unselect() {
        project.getBars().forEach(bar -> bar.setIsSelected(false));
        points.forEach(point -> point.setSelected(false));
    }
}
