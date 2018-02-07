package dataextraction;

import structural.Sensor;

import java.util.ArrayList;

public interface Extractor {

    public int extractNextValue(Sensor s , int ligne);
    public ArrayList<Sensor> extractSensors();

}
