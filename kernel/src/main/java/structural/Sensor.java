package structural;

import laws.DataLaw;

/**
 * Created by Matthieu on 29/01/2018.
 */
public class Sensor {
    public Building getBuilding() {
        return building;
    }

    public void setBuilding(Building building) {
        this.building = building;
    }

    public DataLaw getSensorDataLaw() {
        return sensorDataLaw;
    }

    public void setSensorDataLaw(DataLaw sensorDataLaw) {
        this.sensorDataLaw = sensorDataLaw;
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    private int id;
    private Building building;
    private DataLaw sensorDataLaw;
    private int time;
    private int value;

    public Sensor(Building building, DataLaw law){
        this.building = building;
        this.sensorDataLaw = law;
        this.value = 0;
        this.time = 0;
    }

    public void tick(){
        this.time++;
        this.value = sensorDataLaw.generateNextValue();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String toString(){
        return "Sensor : " + id + "Building associated : " + building.getId() + " Law : " + sensorDataLaw.getClass().getName();

    }

}
