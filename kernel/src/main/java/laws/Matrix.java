package laws;

import java.math.BigDecimal;
import java.util.ArrayList;

/**
 * Created by Michael on 30/01/2018.
 */
public class Matrix {
    private ArrayList<ArrayList<BigDecimal>> matrix;

    public Matrix(ArrayList<ArrayList<BigDecimal>> matrix) {
        this.matrix = matrix;
    }

    public ArrayList<ArrayList<BigDecimal>> getMatrix() {
        return matrix;
    }

    public ArrayList<BigDecimal> getRow(int i){
        return matrix.get(i);
    }

    public BigDecimal getElement(int i, int j){
        return matrix.get(i).get(j);
    }

    public void setMatrix(ArrayList<ArrayList<BigDecimal>> matrix) {
        this.matrix = matrix;
    }
}
