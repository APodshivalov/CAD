package app.model;

import javafx.scene.canvas.GraphicsContext;

/**
 * Created by podsh on 08.04.2017.
 */
public class Bar {
    private Point firstPoint;
    private Point secondPoint;

    public Bar(Point first, Point second){
        this.firstPoint = first;
        this.secondPoint = second;
    }

    public void draw(GraphicsContext gc) {
        gc.strokeLine(firstPoint.getX(), firstPoint.getY(), secondPoint.getX(), secondPoint.getY());
    }
}
