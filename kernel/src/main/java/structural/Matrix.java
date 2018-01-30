package structural;

import java.util.ArrayList;

/**
 * Created by Michael on 30/01/2018.
 */
public class Matrix {
    private ArrayList<Double> matrix;

    public Matrix(ArrayList<Double> matrix) {
        this.matrix = matrix;
    }

    public ArrayList<Double> getMatrix() {
        return matrix;
    }

    public void setMatrix(ArrayList<Double> matrix) {
        this.matrix = matrix;
    }
}
