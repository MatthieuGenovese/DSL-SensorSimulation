package launcher;

import database.InfluxDBManager;
import structural.Area;
import structural.Sensor;

import java.util.ArrayList;
import java.util.Date;
import java.util.Observable;
import java.util.Observer;

/**
 * Created by Matthieu on 29/01/2018.
 */
public class App implements Observer {
    private ArrayList<Area> areas;
    private int step = 0;
    private int nbSensor;
    private InfluxDBManager influxDBManager;
    private long startTime;
    private long stopTime;

    public App(Date startTime, Date stopTime){

        this.startTime = startTime.getTime();
        this.stopTime = stopTime.getTime();
        nbSensor = 0;
        influxDBManager = new InfluxDBManager();
    }

    public int getStep() {
        return step;
    }

    public void setStep(int step) {
        this.step = step;
    }

    public ArrayList<Area> getAreas() {
        return areas;
    }

    public void setAreas(ArrayList<Area> areas) {
        this.areas = areas;
    }

    public void setup(){
        for (Area b : areas) {
            for (Sensor s : b.getSensorList()) {
                s.setStartTime(startTime);
                s.setStopTime(stopTime);
                s.addObserver(this);
                nbSensor++;
            }
        }
    }

    public void run(){
        int nbSensorFinished = 0;
        System.out.println("Debut de la simulation");
        System.out.println("Simulation en cours...");
        while(nbSensorFinished < nbSensor)  {
            nbSensorFinished = 0;
            for (Area b : areas) {
                for (Sensor s : b.getSensorList()) {
                    if(s.isFinish()){
                        nbSensorFinished++;
                    }
                    else {
                        s.tick();
                    }
                }
            }
        }
        System.out.println("Simulation terminée !");
    }

    @Override
    public void update(Observable o, Object arg) {
        if(o instanceof Sensor){
            influxDBManager.writeSensor((Sensor) o);
        }
    }
}
