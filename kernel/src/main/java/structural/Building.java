package structural;

import java.util.ArrayList;

/**
 * Created by Matthieu on 29/01/2018.
 */
public class Building {
    private int id;
    private ArrayList<Sensor> sensorList;

    public Building(ArrayList<Sensor> sensorList){
        this.sensorList = sensorList;
    }
}
