package app.model;

/**
 * Created by podsh on 08.04.2017.
 */
public class Point {
    private double x;
    private double y;
    private boolean isSelected;
    private double near = 10;

    public Point(double x, double y) {
        this.x = x;
        this.y = y;
        isSelected = false;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public boolean near(double x, double y) {
        return (Math.abs(x - this.x) < near) && (Math.abs(y - this.y) < near);
    }

    public boolean equals(double x, double y) {
        return this.x == x && this.y == y;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    public boolean between(double x1, double y1, double x2, double y2) {
        isSelected = x1 <= x && x <= x2 && y2 <= y && y <= y1;
        return isSelected;
    }
}
