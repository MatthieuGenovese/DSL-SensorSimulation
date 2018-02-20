package dataextraction;

import structural.Sensor;

import java.util.ArrayList;

public interface Extractor {

    public Object extractNextValue(String s);
    public int getCurrentTime();
    public boolean isFinish();

}
