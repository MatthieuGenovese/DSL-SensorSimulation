package structural;

import laws.DataLaw;
import values.Value;

import java.util.Observable;

/**
 * Created by Matthieu on 29/01/2018.
 */
public class Sensor extends Observable{
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

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public Value getValue() {
        return value;
    }

    public void setValue(Value value) {
        this.value = value;
    }

    private String name;
    private int id;

    public Sensor(){
        this.echantillonage = 1;
        this.time = 0;
    }

    public Sensor(String name){
        this.name = name;
        this.echantillonage = 1;
        this.time = 0;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int echantillonage;
    private Building building;
    private DataLaw sensorDataLaw;
    private long time;
    private Value value;

    public void tick(){
        //if(time % sensorDataLaw.getFrequency() == 0){
        this.value = sensorDataLaw.generateNextValue(time);
        //}
        if(time % echantillonage == 0){
            setChanged();
            notifyObservers();
        }

        this.time++;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getEchantillonage() {
        return echantillonage;
    }

    public void setEchantillonage(int echantillonage) {
        this.echantillonage = echantillonage;
    }

    public String toString(){
        return "Sensor : " + id + " Law : " + sensorDataLaw.getClass().getName() + " Value : " + getValue() + " Time : " +  getTime();
    }

}
