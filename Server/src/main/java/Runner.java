import helpers.Matrix;
import helpers.MatrixPrinter;

import java.util.Arrays;

/**
 * Created by podsh on 06.05.2017.
 */
public class Runner {
    public static void main(String[] args) {
        MatrixPrinter printer = new MatrixPrinter(System.out);

        Matrix matrix = new Matrix(3,3);
        matrix.set(0,0,2);
        matrix.set(0,1,4);
        matrix.set(0,2,0);
        matrix.set(1,0,-2);
        matrix.set(1,1,1);
        matrix.set(1,2,3);
        matrix.set(2,0,-1);
        matrix.set(2,1,0);
        matrix.set(2,2,1);

        printer.print(matrix);

        double[] vector = new double[3];
        vector[0] = 1;
        vector[1] = 2;
        vector[2] = -1;

        System.out.println(Arrays.toString(matrix.multiple(vector)));
    }
}
