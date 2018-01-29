package structural;

import laws.DataLaw;
import values.Value;

/**
 * Created by Matthieu on 29/01/2018.
 */
public abstract class Sensor {
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

    public Value getValue() {
        return value;
    }

    public void setValue(Value value) {
        this.value = value;
    }

    private int id;
    private Building building;
    private DataLaw sensorDataLaw;
    private int time;
    private Value value;

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
        return "Sensor : " + id + " Law : " + sensorDataLaw.getClass().getName() + " Value : " + getValue();
    }

}
