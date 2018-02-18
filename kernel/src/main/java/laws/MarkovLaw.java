package laws;

import java.math.BigDecimal;
import java.util.ArrayList;

/**
 * Created by Matthieu on 29/01/2018.
 */
public class MarkovLaw implements DataLaw {
    private String name;
    private Object value;
    private Matrix matrix;
    private States states;
    private int frequency;

    public MarkovLaw(String name, States states, Matrix matrix, int frequency, String unit){
        this.name = name;
        this.states = states;
        this.value = null;
        this.matrix = matrix;
        switch(unit){
            case "ms":
                this.frequency = frequency;
                break;
            case "d":
                this.frequency = frequency * 1000 * 60 * 60 * 24;
                break;
            case "min":
                this.frequency = frequency * 1000 * 60;
                break;
            case "s":
                this.frequency = frequency * 1000;
                break;
            case "h":
                this.frequency = frequency * 1000 * 60 * 60;
                break;
        }
    }

    @Override
    public String getName() {
        return name;
    }

    public DataLaw getLaw(){
        return this;
    }

    public States getStates() {
        return states;
    }

    public void setStates(States states) {
        this.states = states;
    }

    public Matrix getMatrix() {
        return matrix;
    }

    public void setMatrix(Matrix matrix) {
        this.matrix = matrix;
    }

    public int getFrequency(){
        return frequency;
    }

    public void setFrequency(int frequency){
        this.frequency = frequency;
    }

    public Object getValue(){
        return value;
    }


    public boolean generateNextValue(long previousTime, long time){
            if(previousTime + frequency <= time) {
                double proba = Math.random();
                if (value == null) {
                    value = states.getValue(0);
                }
                int size = states.getValue().size();
                for (int i = 0; i < states.getValue().size(); i++) {
                    if (value == states.getValue(i)) {
                        ArrayList<BigDecimal> row = matrix.getRow(i);
                        for (int j = 0; j < row.size(); j++) {
                            if (proba < matrix.getElement(i, j).doubleValue()) {
                                value = states.getValue(j);
                                return true;
                            } else {
                                proba = proba - matrix.getElement(i, j).doubleValue();
                            }
                        }
                        break;
                    }
                }
            }
        return false;
    }
}
