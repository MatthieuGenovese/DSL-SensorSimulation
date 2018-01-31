package laws;

import values.Nombre;

/**
 * Created by Matthieu on 29/01/2018.
 */
public class RandomLaw implements DataLaw {
    private String name;
    private Nombre value;
    private int frequency;

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

    public Nombre generateNextValue(){
        value.setValue(1 + (int)(Math.random() * ((1000 - 1) + 1)));
        return value;
    }
}
