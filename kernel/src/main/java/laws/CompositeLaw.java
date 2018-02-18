package laws;

import structural.Sensor;

/**
 * Created by Michael on 12/02/2018.
 */
public class CompositeLaw implements DataLaw{
    private String name;
    private Sensor sensor;
    private String function;
    private int nbSensors;
    private Object value;

    public CompositeLaw(String name, Sensor sensor, String function) {
        this.name = name;
        this.function = function;
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
        for(Sensor s : sensor.getBuilding().getSensorList()){
            Object val = s.getValue();
            if(val instanceof Number && s.getName().equalsIgnoreCase(sensor.getName())){
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
