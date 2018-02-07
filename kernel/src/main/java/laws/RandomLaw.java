package laws;

import values.Nombre;

/**
 * Created by Matthieu on 29/01/2018.
 */
public class RandomLaw implements DataLaw {
    private String name;
    private Nombre value;
    private int frequency;
    private int min;
    private int max;

    public RandomLaw(String name){
        this.name = name;
        this.value = new Nombre();
    }

    public int getFrequency(){
        return frequency;
    }

    public void setFrequency(int frequency){
        this.frequency = frequency;
    }

    @Override
    public String getName() {
        return name;
    }

    public DataLaw getLaw(){
        return this;
    }

    public int getMin() {
        return min;
    }

    public void setMin(int min) {
        this.min = min;
    }

    public int getMax() {
        return max;
    }

    public void setMax(int max) {
        this.max = max;
    }

    public Nombre generateNextValue(){
        value.setValue(min + (int)(Math.random() * ((max - 1) + 1)));
        return value;
    }
}
