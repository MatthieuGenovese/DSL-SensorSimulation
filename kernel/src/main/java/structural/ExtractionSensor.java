package structural;

import laws.ExtractionLaw;

import java.util.ArrayList;

/**
 * Created by Matthieu on 20/02/2018.
 */
public class ExtractionSensor extends Sensor{
    private long echantillonageLong;
    private ArrayList<Integer> noise;

    public ExtractionSensor(String name){
        super(name);
        noise = new ArrayList<>();
    }

    public void setNoise(ArrayList<Integer> noise){
        this.noise = noise;
    }
    @Override
    public void tick(){
        if(sensorDataLaw.generateNextValue(previousTime, time)){
            echantillonageLong = ((ExtractionLaw) sensorDataLaw).getCurrentTime();
            time += echantillonageLong;
            if(!noise.isEmpty()){
                System.out.println( "random : " + (noise.get(0) + (int) (Math.random() * ((noise.get(1) - 1) + 1))));
                value = String.valueOf(Integer.valueOf((String)sensorDataLaw.getValue()) + noise.get(0) + (int) (Math.random() * ((noise.get(1) - 1) + 1)));
            }
            else {
                value = sensorDataLaw.getValue();
            }
            setChanged();
            notifyObservers();
        }
        else{
            finish = true;
        }

    }
}
