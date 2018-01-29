package launcher;

import database.InfluxDBManager;
import structural.Building;
import structural.Sensor;

import java.util.ArrayList;

/**
 * Created by Matthieu on 29/01/2018.
 */
public class App {
    private ArrayList<Building> buildings;
    private int step = 0;
    private InfluxDBManager influxDBManager;

    public App(){
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

    public void run(){
        for(int i = 0; i < step; i++)  {
            System.out.println("Step : " + String.valueOf(i+1));
            for (Building b : buildings) {
                System.out.println("\tBuilding : " +b.getId());
                for (Sensor s : b.getSensorList()) {
                    s.tick();
                    System.out.println("\t\t" + s);
                }
            }
        }
    }
}
