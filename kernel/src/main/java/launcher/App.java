package launcher;

import database.InfluxDBManager;
import structural.Building;
import structural.Sensor;

import java.util.ArrayList;
import java.util.Date;
import java.util.Observable;
import java.util.Observer;

/**
 * Created by Matthieu on 29/01/2018.
 */
public class App implements Observer {
    private ArrayList<Building> buildings;
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

    public ArrayList<Building> getBuildings() {
        return buildings;
    }

    public void setBuildings(ArrayList<Building> buildings) {
        this.buildings = buildings;
    }

    public void setup(){
        for (Building b : buildings) {
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
        while(nbSensorFinished < nbSensor)  {
            nbSensorFinished = 0;
            for (Building b : buildings) {
                System.out.println("\tBuilding : " +b.getId());
                for (Sensor s : b.getSensorList()) {
                    if(s.isFinish()){
                        nbSensorFinished++;
                    }
                    else {
                        s.tick();
                        System.out.println("\t\t" + s);
                    }
                }
            }
        }
    }

    @Override
    public void update(Observable o, Object arg) {
        if(o instanceof Sensor){
            influxDBManager.writeSensor((Sensor) o);
        }
    }
}
