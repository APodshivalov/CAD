package app.model;

import app.Controller;
import app.utils.CoordinateUtils;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

/**
 * Created by podsh on 08.04.2017.
 */
public class Bar {
    private Point firstPoint;
    private Point secondPoint;
    private Material material;
    private boolean isSelected;

    public Bar(Point first, Point second){
        this.firstPoint = first;
        this.secondPoint = second;
        isSelected = false;
    }

    public void draw(Controller controller) {

        CoordinateUtils utils = controller.getCoordinateUtils();
        GraphicsContext gc = controller.getCanvas().getGraphicsContext2D();
        if (isSelected) {
            gc.setStroke(Color.RED);
            draw(gc, utils);
        } else {
            if (controller.getMaterialView().isSelected()){
                drawWithColors(gc, utils);
            } else {
                drawWithoutColors(gc, utils);
            }
        }
    }

    private void drawWithoutColors(GraphicsContext gc, CoordinateUtils utils) {
        gc.setStroke(Color.BLACK);
        draw(gc, utils);
    }

    private void draw(GraphicsContext gc, CoordinateUtils utils) {
        gc.strokeLine(utils.fromRealX(firstPoint.getX()), utils.fromRealY(firstPoint.getY()),
                utils.fromRealX(secondPoint.getX()), utils.fromRealY(secondPoint.getY()));
    }

    private void drawWithColors(GraphicsContext gc, CoordinateUtils utils) {
        if (material == null){
            gc.setStroke(Color.BLACK);
        } else {
            gc.setStroke(material.getColor());
        }
        draw(gc, utils);
    }

    public boolean between(double x1, double y1, double x2, double y2) {
        return firstPoint.between(x1,y1,x2,y2) && secondPoint.between(x1,y1,x2,y2);
    }

    public boolean equals(Bar obj) {
        return ((obj.secondPoint == secondPoint) && (obj.firstPoint == firstPoint)) ||
                ((obj.secondPoint == firstPoint) && (obj.firstPoint == secondPoint));
    }

    public Point getFirstPoint() {
        return firstPoint;
    }

    public Point getSecondPoint() {
        return secondPoint;
    }

    public void setIsSelected(boolean isSelected) {
        this.isSelected = isSelected;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public Material getMaterial() {
        return material;
    }

    public void setMaterial(Material material) {
        this.material = material;
    }
}
