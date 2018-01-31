package laws;

import structural.Matrix;
import structural.States;
import values.Temps;
import values.Value;

/**
 * Created by Matthieu on 29/01/2018.
 */
public class MarkovLaw implements DataLaw {
    private String name;
    Value<String> value = new Temps();
    Matrix matrix;
    States states;

    public MarkovLaw(String name, States states){
        this.name = name;
        this.states = states;
        this.value = new Temps();
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

    public Value<String> generateNextValue(){


        double proba = Math.random();

        //CALCUL 1

        if(value.getValue() == ""){
            value.setValue(states.getValue(0));
        }
        int size = states.getValue().size();
        for(int i = 0; i < states.getValue().size(); i++){
            if(value.getValue() == states.getValue(i)){
                if(proba > 0.1 && proba <= 0.3){
                    value.setValue(states.getValue((i+1)%size));
                }
                else if (proba <= 0.1){
                    value.setValue(states.getValue((i+2)%size));
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
