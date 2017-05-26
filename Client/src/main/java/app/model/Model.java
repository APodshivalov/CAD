package app.model;

import app.Controller;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Stream;

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
    private ArrayOfResult results;

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
        if (controller.getnView().isSelected()) {
            drawN();
        }
        if (controller.getqView().isSelected()) {
            drawQ();
        }
        if (controller.getmView().isSelected()) {
            drawM();
        }
        project.getBars().forEach(bar -> bar.draw(controller));

        points.forEach(point -> point.draw(controller));
    }

    private void drawM() {
        double maxM = results.getItem().stream()
                .flatMap(result -> Stream.of(result.getM1(), result.getM2()))
                .max(Comparator.naturalOrder())
                .get();
        double k = 40.0 / maxM;
        for (Bar bar : project.getBars()) {
            double m1 = results.getResultById(bar.getId()).getM1();
            double m2 = results.getResultById(bar.getId()).getM2();

            drawDiagram(bar, "#C9A0DC", k, m1, m2);
        }
    }

    private void drawQ() {
        double maxQ = results.getItem().stream()
                .flatMap(result -> Stream.of(result.getQ1(), result.getQ2()))
                .max(Comparator.naturalOrder())
                .get();
        double k = 40.0 / maxQ;
        for (Bar bar : project.getBars()) {
            double q1 = results.getResultById(bar.getId()).getQ1();
            double q2 = results.getResultById(bar.getId()).getQ2();

            drawDiagram(bar, "#3E5F8A", k, q1, q2);
        }
    }

    private void drawN() {
        double maxN = results.getItem().stream()
                .flatMap(result -> Stream.of(result.getN1(), result.getN2()))
                .max(Comparator.naturalOrder())
                .get();
        double k = 40.0 / maxN;
        for (Bar bar : project.getBars()) {
            double n1 = results.getResultById(bar.getId()).getN1();
            double n2 = results.getResultById(bar.getId()).getN2();

            drawDiagram(bar, "#2F4F4F", k, n1, n2);
        }
    }

    private void drawDiagram(Bar bar, String color, double k, double n1, double n2) {
        double x1 = controller.getCoordinateUtils().fromRealX(bar.getFirstPoint().getX());
        double y1 = controller.getCoordinateUtils().fromRealY(bar.getFirstPoint().getY());
        double x2 = controller.getCoordinateUtils().fromRealX(bar.getSecondPoint().getX());
        double y2 = controller.getCoordinateUtils().fromRealY(bar.getSecondPoint().getY());

        if (x1 > x2) {
            double temp = x1;
            x1 = x2;
            x2 = temp;
            temp = y1;
            y1 = y2;
            y2 = temp;
            temp = n1;
            n1 = n2;
            n2 = temp;
        }
        double L = Math.sqrt(Math.pow(x2 - x1, 2) + Math.pow(y2 - y1, 2));
        double cos = Math.abs(x2 - x1) / L;
        double sin = (y2 - y1) / L;
        double xn = x1 + sin * n1 * k;
        double yn = y1 - cos * n1 * k;
        double xk = x2 + sin * n2 * k;
        double yk = y2 - cos * n2 * k;
        double[] PolyX = {x1, xn, xk, x2};
        double[] PolyY = {y1, yn, yk, y2};
        GraphicsContext gc = controller.getCanvas().getGraphicsContext2D();
        gc.setStroke(Color.BLACK);
        gc.setFont(new Font("System", 10));
        gc.strokeText(String.valueOf(n1), xn + 10, yn - 10);
        gc.setTextAlign(TextAlignment.RIGHT);
        gc.strokeText(String.valueOf(n2), xk - 10, yk - 10);
        gc.setTextAlign(TextAlignment.LEFT);
        gc.setFont(new Font("System", 12));

        gc.setFill(Color.web(color, 0.5));
        gc.fillPolygon(PolyX, PolyY, 4);
        gc.setStroke(Color.web(color));
        gc.strokeLine(x1, y1, xn, yn);
        gc.strokeLine(xn, yn, xk, yk);
        gc.strokeLine(xk, yk, x2, y2);
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
            if (controller.getCoordinateUtils().isNear(p, x, y)) {
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
        if (!points.contains(point)) {
            points.add(point);
        }
    }

    public void select(double x, double y, double x1, double y1) {
        project.setSelect(x, y, x1, y1);
        points.forEach(point -> point.select(x, y, x1, y1));
    }

    public void draw(MouseEvent mouseEvent) {
        project.getBars().forEach(bar -> bar.draw(controller));
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

    public void setResult(ArrayOfResult arrayOfResult) {
        results = arrayOfResult;
    }

    public ObservableList<Result> getResult() {
        ObservableList<Result> results = FXCollections.observableArrayList();
        results.addAll(this.results.getItem());
        return results;
    }
}
