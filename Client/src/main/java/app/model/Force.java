package app.model;

import app.utils.CoordinateUtils;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

/**
 * Created by podsh on 30.04.2017.
 */
public class Force {
    private int x;
    private int y;
    private int m;

    public Force(int y, int x, int m) {
        this.y = y;
        this.x = x;
        this.m = m;
    }

    public Force() {

    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getM() {
        return m;
    }

    public void draw(GraphicsContext gc, CoordinateUtils utils, Point point) {
        double xc = utils.fromRealX(point.getX());
        double yc = utils.fromRealY(point.getY());
        if (x < 0) {
            gc.strokeLine(xc, yc, xc + 40, yc);
            gc.strokeLine(xc, yc, xc + 5, yc + 3);
            gc.strokeLine(xc, yc, xc + 5, yc - 3);
            gc.strokeText(x + "", xc + 35, yc + 12);
        }
        if (x > 0) {
            gc.strokeLine(xc, yc, xc - 40, yc);
            gc.strokeLine(xc, yc, xc - 5, yc + 3);
            gc.strokeLine(xc, yc, xc - 5, yc - 3);
            gc.strokeText(x + "", xc - 35, yc + 12);
        }
        if (y < 0) {
            gc.strokeLine(xc, yc, xc, yc + 40);
            gc.strokeLine(xc, yc, xc - 3, yc + 5);
            gc.strokeLine(xc, yc, xc + 3, yc + 5);
            gc.strokeText(y + "", xc + 3, yc + 20);
        }
        if (y > 0) {
            gc.strokeLine(xc, yc, xc, yc - 40);
            gc.strokeLine(xc, yc, xc - 3, yc - 5);
            gc.strokeLine(xc, yc, xc + 3, yc - 5);
            gc.strokeText(y + "", xc + 3, yc - 20);
        }
        if (m != 0) {
            gc.drawImage(new Image("images/mForce_mini.png"), xc - 16, yc - 16);
            gc.strokeText(m + "", xc - 25, yc - 15);
        }
    }

    public void add(Force currentForce) {
        if (currentForce.getX() == 0 && currentForce.getY() == 0 && currentForce.getM() == 0) {
            x = 0;
            y = 0;
            m = 0;
            return;
        }
        if (currentForce.getX() != 0) {
            x = currentForce.getX();
        }
        if (currentForce.getY() != 0) {
            y = currentForce.getY();
        }
        if (currentForce.getM() != 0) {
            m = currentForce.getM();
        }
    }
}
