package laws;

import structural.Sensor;
import values.Nombre;
import values.Value;

/**
 * Created by Michael on 12/02/2018.
 */
public class CompositeLaw implements DataLaw {
    private String name;
    private Sensor sensor;

    public CompositeLaw(String name, Sensor sensor) {
        this.name = name;
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
    public Value generateNextValue(long time) {
        Value sum = new Nombre();
        for(int i = 0; i<sensor.getBuilding().getSensorList().size()-1; i++){ //TODO retirer le size()-1
            Value val = sensor.getBuilding().getSensorList().get(i).getValue(); //TODO Toutes les values du building sont identiques !
            if(val.getValue() instanceof Number){
                sum.setValue((Integer)sum.getValue() + (Integer)val.getValue());
            }
        }
        return sum;
    }


}
