import dao.CutDao;
import dao.MaterialDao;
import domain.Cut;
import domain.Material;
import helpers.Matrix;
import helpers.MatrixPrinter;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * Created by podsh on 06.05.2017.
 */
public class Runner {
    public static void main(String[] args) throws IOException {
        Matrix matrix = new Matrix(2,2);
        matrix.set(0,0,2);
        matrix.set(0,1,1);
        matrix.set(1,0,-1);
        matrix.set(1,1,6);

        double[] p = {3,-8};

        double[] k = matrix.gaoss(p);
        System.out.println(k[0] + " " + k[1]);
    }
}
