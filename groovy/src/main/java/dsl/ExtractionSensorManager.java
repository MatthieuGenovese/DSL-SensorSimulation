package dsl;

import structural.ExtractionSensor;
import structural.Sensor;

import java.util.ArrayList;

/**
 * Created by Matthieu on 20/02/2018.
 */
public class ExtractionSensorManager {
    private String sensorName;
    private ArrayList<Sensor> sensors;

    public ExtractionSensorManager(String sensorName, ArrayList<Sensor> sensors){
        this.sensorName = sensorName;
        this.sensors = sensors;

    }

    public void addNoise(ArrayList<Integer> noise){
        for(Sensor s : sensors){
            if(s.getName().equalsIgnoreCase(sensorName)){
                ((ExtractionSensor) s).setNoise(noise);
            }
        }
    }

    public void addMinOffset(Long minDate){
        for(Sensor s : sensors){
            if(s.getName().equalsIgnoreCase(sensorName)){
                ((ExtractionSensor) s).setMinOffset(minDate);
            }
        }
    }

    public void addMaxOffset(Long maxDate){
        for(Sensor s : sensors){
            if(s.getName().equalsIgnoreCase(sensorName)){
                ((ExtractionSensor) s).setMaxOffset(maxDate);
            }
        }
    }
}