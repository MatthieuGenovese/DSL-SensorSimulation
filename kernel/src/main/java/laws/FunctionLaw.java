package laws;


import groovy.lang.Closure;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.function.Predicate;

/**
 * Created by Matthieu on 31/01/2018.
 *
 */
public class FunctionLaw implements DataLaw{
    public String nom;
    private Object value;
    private Closure cl;

    public FunctionLaw(String name, Closure cl){
        value = null;
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

    public Object getValue(){
        return value;
    }

    @Override
    public boolean generateNextValue(long previousTime, long time) {
        if(cl.call(time) == null){
            return true;
        }
        value = cl.call(time);
        return true;
    }

}
