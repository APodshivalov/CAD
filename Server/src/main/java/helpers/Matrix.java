package helpers;

import model.Bar;

/**
 * Created by podsh on 25.05.2017.
 */
public class Matrix {
    private double[][] elements;
    private boolean symmetry;
    private int width;
    private int height;

    public Matrix(int y, int x) {
        width = x;
        height = y;
        elements = new double[y][x];
    }

    public Matrix(int y, int x, boolean symmetry) {
        elements = new double[y][x];
        this.symmetry = symmetry;
        width = x;
        height = y;
    }

    public void set(int y, int x, double v) {
        if (symmetry && x != y) {
            elements[x][y] = v;
            elements[y][x] = v;
        } else {
            elements[y][x] = v;
        }
    }

    public double[][] getElements() {
        return elements;
    }

    public static Matrix getRotationMatrix(Bar bar) {
        Matrix v = new Matrix(6, 6);
        v.set(0, 0, bar.getCos());
        v.set(0, 1, bar.getSin());
        v.set(1, 0, -bar.getSin());
        v.set(1, 1, bar.getCos());
        v.set(2, 2, 1);

        v.set(3, 3, bar.getCos());
        v.set(3, 4, bar.getSin());
        v.set(4, 3, -bar.getSin());
        v.set(4, 4, bar.getCos());
        v.set(5, 5, 1);

        return v;
    }

    public static Matrix getLocalStiffnessMatrix(double E, double F, double I, double length) {
        Matrix rISh = new Matrix(6, 6, true);

        rISh.set(0, 0, E * F / length);
        rISh.set(0, 3, -E * F / length);

        rISh.set(1, 1, 12 * E * I / Math.pow(length, 3));
        rISh.set(1, 2, 6 * E * I / Math.pow(length, 2));
        rISh.set(1, 4, -12 * E * I / Math.pow(length, 3));

        rISh.set(2, 2, 4 * E * I / length);
        rISh.set(2, 4, -6 * E * I / Math.pow(length, 2));
        rISh.set(2, 5, 2 * E * I / length);

        rISh.set(3, 3, E * F / length);

        rISh.set(4, 4, 12 * E * I / Math.pow(length, 3));
        rISh.set(4, 5, 6 * E * I / Math.pow(length, 2));

        rISh.set(5, 5, 4 * E * I / length);

        return rISh;
    }

    public Matrix multiple(Matrix v) {
        if (this.getWidth() != v.getHeight()) throw new RuntimeException("Нельзя перемножить");
        Matrix result = new Matrix(this.height, v.getWidth());
        for (int i = 0; i < result.getHeight(); i++) {
            for (int j = 0; j < result.getWidth(); j++) {
                for (int q = 0; q < this.getWidth(); q++) {
                    result.add(i, j, this.get(i, q) * v.get(q, j));
                }
            }
        }
        return result;
    }

    public void add(int i, int j, double v) {
        elements[i][j] += v;
    }

    public double get(int q, int j) {
        return elements[q][j];
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public Matrix transponate() {
        Matrix matrix = new Matrix(width, height);
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                matrix.set(j, i, this.get(i, j));
            }
        }
        return matrix;
    }

    public void setRow(int i, int number) {
        for (int column = 0; column < getWidth(); column++) {
            elements[i][column] = number;
        }
    }

    public void setColumn(int i, int number) {
        for (int row = 0; row < getWidth(); row++) {
            elements[row][i] = number;
        }
    }

    public double[] gaoss(double[] P) {
        double[] Z = new double[elements.length];

        for (int i = 0; i < height; i++) {
            double tmp = elements[i][i];
            for (int j = width - 1; j >= i; j--) elements[i][j] /= tmp;
            P[i] /= tmp;

            for (int j = i + 1; j < width; j++) {
                tmp = elements[j][i];
                for (int k = width - 1; k >= i; k--) {
                    elements[j][k] -= tmp * elements[i][k];
                }
                P[j] -= tmp * P[i];
            }
        }

        Z[height - 1] = P[height - 1];
        for (int i = height - 2; i >= 0; i--) {
            Z[i] = P[i];
            for (int j = i + 1; j < width; j++) Z[i] -= elements[i][j] * Z[j];
        }
        return Z;
    }

    public double[] multiple(double[] vector) {
        if (width != vector.length) throw new IllegalArgumentException();
        double[] result = new double[height];
        for (int row = 0; row < height; row++) {
            for (int column = 0; column < width; column++) {
                result[row]+= elements[row][column] * vector[column];
            }
        }
        return result;
    }
}
