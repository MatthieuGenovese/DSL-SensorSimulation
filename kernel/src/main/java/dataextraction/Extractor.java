package dataextraction;

import structural.Sensor;

public interface Extractor {

    public int extractNextValue(Sensor s , int ligne);
}
