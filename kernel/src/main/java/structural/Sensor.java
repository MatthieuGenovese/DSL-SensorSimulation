package structural;

import laws.DataLaw;

import java.util.Observable;

/**
 * Created by Matthieu on 29/01/2018.
 */
public class Sensor extends Observable{
    protected  String name;
    protected  int id;
    protected  boolean finish = false;
    protected  long stopTime;
    protected int echantillonage;
    protected Area area;
    protected  DataLaw sensorDataLaw;
    protected  long previousTime;
    protected  long time;
    protected  Object value;


    public Area getArea() {
        return area;
    }

    public boolean isFinish() {
        return finish;
    }

    public void setFinish(boolean finish) {
        this.finish = finish;
    }


    public void setStartTime(long startTime) {
        this.time = startTime;
    }

    public long getStopTime() {
        return stopTime;
    }

    public void setStopTime(long stopTime) {
        this.stopTime = stopTime;
    }

    public Sensor(){
        this.echantillonage = 0;
        this.time = 0;
        this.previousTime = 0;
    }

    public Sensor(String name){
        this.name = name;
        this.echantillonage = 0;
        this.time = 0;
        this.previousTime = 0;
    }

    public Sensor(String name, Integer echant, String unit){
        this.name = name;
        switch(unit){
            case "ms":
                this.echantillonage = echant;
                break;
            case "d":
                this.echantillonage = echant * 1000 * 60 * 60 * 24;
                break;
            case "min":
                this.echantillonage = echant * 1000 * 60;
                break;
            case "s":
                this.echantillonage = echant * 1000;
                break;
            case "h":
                this.echantillonage = echant * 1000 * 60 * 60;
                break;
        }
    }

    public void setArea(Area area) {
        this.area = area;
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

    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    public void tick(){
        if(time >= stopTime){
            if(!finish) {
                finish = true;
            }
            return;
        }
        if(sensorDataLaw.generateNextValue(previousTime, time)){
            previousTime = time;
            this.value = sensorDataLaw.getValue();
        }

        this.time += echantillonage;
        setChanged();
        notifyObservers();
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
