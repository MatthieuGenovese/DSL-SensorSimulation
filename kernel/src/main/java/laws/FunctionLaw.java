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
    private int nbSensors;
    private int callTime;
    private int simulationTime;

    public int getNbSensors() {
        return nbSensors;
    }

    public void setNbSensors(int nbSensors) {
        this.nbSensors = nbSensors;
        this.simulationTime = 0;
        this.callTime = 1;
    }

    public FunctionLaw(String name, Closure cl){
        value = null;
        nom = name;
        nbSensors = 0;

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
        if(cl.call(simulationTime) == null){
            value = 0;
        }
        else {
            value = cl.call(simulationTime);
        }
        if(callTime == nbSensors){
            simulationTime++;
            callTime = 0;
        }
        callTime++;
        return true;
    }

}
