package app.model;

/**
 * Created by podsh on 08.04.2017.
 */
public class Point {
    private double x;
    private double y;

    public Point(double x, double y){
        this.x = x;
        this.y = y;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public boolean near(double x, double y) {
        return (Math.abs(x - this.x) < 5) && (Math.abs(y - this.y) < 5);
    }
}
