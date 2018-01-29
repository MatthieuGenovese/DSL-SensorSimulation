package laws;

import values.Nombre;

/**
 * Created by Matthieu on 29/01/2018.
 */
public class RandomLaw implements DataLaw {
    private Nombre value;

    public RandomLaw(){
        this.value = new Nombre();
    }

    public DataLaw getLaw(){
        return this;
    }

    public Nombre generateNextValue(){
        value.setValue(1 + (int)(Math.random() * ((1000 - 1) + 1)));
        return value;
    }
}
