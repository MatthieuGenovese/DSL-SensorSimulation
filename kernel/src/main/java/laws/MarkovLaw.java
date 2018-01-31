package laws;

import values.Temps;
import values.Value;

import java.math.BigDecimal;
import java.util.ArrayList;

/**
 * Created by Matthieu on 29/01/2018.
 */
public class MarkovLaw implements DataLaw {
    private String name;
    private Value<String> value = new Temps();
    private Matrix matrix;
    private States states;
    private int frequency;

    public MarkovLaw(String name, States states, Matrix matrix){
        this.name = name;
        this.states = states;
        this.value = new Temps();
        this.matrix = matrix;
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


    public Value<String> generateNextValue(){


        double proba = Math.random();

        //CALCUL 1   [0.4, 0.5, 0.1]  -> 0.0 - 0.4    0.4 - 0.9    0.9 - 1
//        ["soleil","nuage","pluie"]
//        [[0.1, 0.2, 0.7], [0.3, 0.5, 0.2], [0.4, 0.5, 0.1]]
        if(value.getValue() == ""){
            value.setValue(states.getValue(0));
        }
        int size = states.getValue().size();
        for(int i = 0; i < states.getValue().size(); i++){
            if(value.getValue() == states.getValue(i)){
                ArrayList<BigDecimal> row = matrix.getRow(i);
                for(int j = 0; j < row.size(); j++){
                    if(proba < matrix.getElement(i, j).doubleValue()){
                        value.setValue(states.getValue(j));
                        return value;
                    }
                    else{
                        proba = proba - matrix.getElement(i, j).doubleValue();
                    }
//                    if(j==0){
//                        if(proba < row.get(j).doubleValue()){
//                            value.setValue(states.getValue(j));
//                            return value;
//                        }
//                    }
//                    else{
//                        if(proba >= row.get(j-1).doubleValue() && proba < row.get(j).doubleValue()){
//                            value.setValue(states.getValue(j));
//                            return value;
//                        }
//                    }
                }
                break;
            }
        }

        //CALCUL 2

//        String s0 = states.getValue(0);
//        String s1 = states.getValue(1);
//        String s2 = states.getValue(2);
//        if(value.getValue() == s0){
//            if(proba > 0.1 && proba <= 0.3){
//                value.setValue(s1);
//            }
//            else if (proba <= 0.1){
//                value.setValue(s2);
//            }
//        }
//        else if(value.getValue() == s1){
//            if(proba > 0.7){
//                value.setValue(s0);
//            }
//            else if (proba <= 0.7 && proba > 0.3){
//                value.setValue(s2);
//            }
//        }
//        else{
//            if(proba < 0.7 && proba > 0.2){
//                value.setValue(s0);
//            }
//            else if (proba >= 0.7){
//                value.setValue(s1);
//            }
//        }

        //CALCUL 3

//        switch(value.getValue()){
//            case s0:
//                if(proba > 0.1 && proba <= 0.3){
//                    value.setValue("nuageux");
//                }
//                else if (proba <= 0.1){
//                    value.setValue("orageux");
//                }
//                break;
//            case "nuageux":
//                if(proba > 0.7){
//                    value.setValue("beau");
//                }
//                else if (proba <= 0.7 && proba > 0.3){
//                    value.setValue("orageux");
//                }
//                break;
//            case "orageux":
//                if(proba < 0.7 && proba > 0.2){
//                    value.setValue("beau");
//                }
//                else if (proba >= 0.7){
//                    value.setValue("nuageux");
//                }
//                break;
//        }
        return value;
    }
}
