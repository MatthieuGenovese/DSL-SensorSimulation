package launcher;

import structural.Building;
import structural.Sensor;

import java.util.ArrayList;

/**
 * Created by Matthieu on 29/01/2018.
 */
public class App {
    public ArrayList<Building> buildings;

    public ArrayList<Building> getBuildings() {
        return buildings;
    }

    public void setBuildings(ArrayList<Building> buildings) {
        this.buildings = buildings;
    }

    public void run(){
        for(Building b : buildings){
            System.out.println(b);
        }
    }
}
