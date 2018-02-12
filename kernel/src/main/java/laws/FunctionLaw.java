package laws;


import groovy.lang.Closure;
import values.Nombre;
import values.ObjectValue;
import values.Value;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.function.Predicate;

/**
 * Created by Matthieu on 31/01/2018.
 *
 */
public class FunctionLaw implements DataLaw{
    public String nom;
    private Value<Object> value;
    private Closure cl;

    public FunctionLaw(String name, Closure cl){
        value = new ObjectValue();
        nom = name;
        this.cl = cl;
    }


    @Override
    public String getName() {
        return nom;
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
    public Value<Object> generateNextValue(long time) {
        if(cl.call(time) == null){
            return value;
        }
        value.setValue(cl.call(time));
        return value;
    }

}
