package structural;

import laws.ExtractionLaw;

/**
 * Created by Matthieu on 20/02/2018.
 */
public class ExtractionSensor extends Sensor{
    private long echantillonageLong;

    public ExtractionSensor(String name){
        super(name);
    }
    @Override
    public void tick(){

        if(sensorDataLaw.generateNextValue(previousTime, time)){
            echantillonageLong = ((ExtractionLaw) sensorDataLaw).getCurrentTime();
            time += echantillonageLong;
            value = sensorDataLaw.getValue();
            setChanged();
            notifyObservers();
        }
        else{
            finish = true;
        }

    }
}
