package dsl;

import laws.CompositeLaw;
import structural.ExtractionSensor;
import structural.Sensor;

import java.util.ArrayList;

/**
 * Created by Matthieu on 20/02/2018.
 */
public class Manager {
    private String sensorName;
    private ArrayList<Sensor> sensors;
    private ArrayList<Sensor> sensorsComposites;
    private ErrorDetection errorDetection;

    public Manager(String sensorName, ArrayList<Sensor> sensorsComposites, ArrayList<Sensor> sensors, ErrorDetection errorDetection){
        this.sensorName = sensorName;
        this.sensorsComposites = sensorsComposites;
        this.sensors = sensors;
        this.errorDetection = errorDetection;

    }

    public void addNoise(ArrayList<Integer> noise){
        for(Sensor s : sensors){
            if(s.getName().equalsIgnoreCase(sensorName)){
                this.errorDetection.extractionSensorExpected(s);
                try {
                    ((ExtractionSensor) s).setNoise(noise);
                }
                catch (Exception e){
                    this.errorDetection.findAndAddLine(e);
                    return;
                }
            }
        }
    }

    public void addMinOffset(Long minDate){
        for(Sensor s : sensors){
            if(s.getName().equalsIgnoreCase(sensorName)){
                this.errorDetection.extractionSensorExpected(s);
                try {
                    ((ExtractionSensor) s).setMinOffset(minDate);
                }
                catch(Exception e){
                    this.errorDetection.findAndAddLine(e);
                    return;
                }
            }
        }
    }

    public void addSensor(String name){
        boolean compositeExist = false;
        boolean s2Exist = false;
        for(Sensor s : sensorsComposites){
            if(s.getName().equalsIgnoreCase(sensorName)){
                compositeExist = true;
                CompositeLaw law = (CompositeLaw) s.getSensorDataLaw();
                for(Sensor s2 : sensors){
                    if(s2.getName().equalsIgnoreCase(name)){
                        s2Exist = true;
                        this.errorDetection.compositeLawImplementation(s2);
                        law.addSensor(s2);
                    }
                }
            }
        }
        if(!compositeExist){
            errorDetection.addErreur("Le sensor composite " + sensorName + " n'existe pas !");
            return;
        }
        if(!s2Exist){
            errorDetection.addErreur("Le sensor " + name + " n'existe pas !");
        }
    }

    public void addMaxOffset(Long maxDate){
        for(Sensor s : sensors){
            if(s.getName().equalsIgnoreCase(sensorName)){
                this.errorDetection.extractionSensorExpected(s);
                try {
                    ((ExtractionSensor) s).setMaxOffset(maxDate);
                }
                catch(Exception e){
                    this.errorDetection.findAndAddLine(e);
                    return;
                }
            }
        }
    }
}
