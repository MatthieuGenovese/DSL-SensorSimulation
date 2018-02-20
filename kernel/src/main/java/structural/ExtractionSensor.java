package structural;

import laws.ExtractionLaw;

/**
 * Created by Matthieu on 20/02/2018.
 */
public class ExtractionSensor extends Sensor{

    public ExtractionSensor(String name){
        super(name);
    }
    @Override
    public void tick(){
        if(((ExtractionLaw) sensorDataLaw).isFinish()){
            finish = true;
        }
        else {
            echantillonage = ((ExtractionLaw) sensorDataLaw).getCurrentTime();
            sensorDataLaw.generateNextValue(previousTime, time);
            time += echantillonage;
            value = sensorDataLaw.getValue();
        }
    }
}
