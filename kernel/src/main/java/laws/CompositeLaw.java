package laws;

import structural.Sensor;

import java.util.Observable;
import java.util.Observer;

/**
 * Created by Michael on 12/02/2018.
 */
public class CompositeLaw implements DataLaw{
    private String name;
    private Sensor sensor;
    private int nbSensors;
    private Object value;

    public CompositeLaw(String name, Sensor sensor) {
        this.name = name;
        this.value = 0;
        nbSensors = 0;
        this.sensor = sensor;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Sensor getSensor() {
        return sensor;
    }

    public void setSensor(Sensor sensor) {
        this.sensor = sensor;
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

    @Override
    public Object generateNextValue(long time) {
        int tmp = (Integer) value;
        for(Sensor s : sensor.getBuilding().getSensorList()){
            Object val = s.getValue();
            if(val instanceof Number && s.getName().equalsIgnoreCase(sensor.getName())){
                tmp += (Integer)val;
            }
        }
        value = tmp;
        return value;
    }

}
