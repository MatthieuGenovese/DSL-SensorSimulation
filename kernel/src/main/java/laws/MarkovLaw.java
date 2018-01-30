package laws;

import values.Temps;
import values.Value;

/**
 * Created by Matthieu on 29/01/2018.
 */
public class MarkovLaw implements DataLaw {

    Value<String> value = new Temps();

    public MarkovLaw(){
    }

    public DataLaw getLaw(){
        return this;
    }

    public Value<String> generateNextValue(){
        double proba = Math.random();
        switch(value.getValue()){
            case "beau":
                if(proba > 0.1 && proba <= 0.3){
                    value.setValue("nuageux");
                }
                else if (proba <= 0.1){
                    value.setValue("orageux");
                }
                break;
            case "nuageux":
                if(proba > 0.7){
                    value.setValue("beau");
                }
                else if (proba <= 0.7 && proba > 0.3){
                    value.setValue("orageux");
                }
                break;
            case "orageux":
                if(proba < 0.7 && proba > 0.2){
                    value.setValue("beau");
                }
                else if (proba >= 0.7){
                    value.setValue("nuageux");
                }
                break;
        }
        return value;
    }
}
