package laws;

import structural.Sensor;

import java.util.ArrayList;

/**
 * Created by Michael on 12/02/2018.
 */
public class CompositeLaw implements DataLaw{
    private String name;
    private ArrayList<Sensor> sensors;
    private String function;
    private int nbSensors;
    private Object value;

    public CompositeLaw(String name, Sensor sensor, String function) {
        this.name = name;
        this.function = function;
        this.value = 0;
        nbSensors = 0;
        sensors = new ArrayList<>();
        for(Sensor s : sensor.getBuilding().getSensorList()){
            if(s.getName().equalsIgnoreCase(sensor.getName())){
                sensors.add(s);
            }
        }
    }

    public void addSensor(Sensor s){
        sensors.add(s);
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<Sensor> getSensors() {
        return sensors;
    }

    public String getFunction() {
        return function;
    }

    public void setFunction(String function) {
        this.function = function;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public DataLaw getLaw() {
        return this;
    }

    @Override
    public int getFrequency() {
        return 0;
    }

    @Override
    public void setFrequency(int frequency) {

    }

    public Object getValue(){
        return value;
    }

    @Override
    public boolean generateNextValue(long previousTime, long time) {
        this.value = 0;
        int tmp = (Integer) value;
        int nbElements = 0;
        for(Sensor s : sensors){
            Object val = s.getValue();
            if(val instanceof Number){
                tmp += (Integer)val;
                nbElements++;
            }
        }

        if(this.getFunction() == "average"){
            value = Double.valueOf(tmp) / Double.valueOf(nbElements);
        }
        else{
            value = tmp;
        }
        return true;
    }

}
