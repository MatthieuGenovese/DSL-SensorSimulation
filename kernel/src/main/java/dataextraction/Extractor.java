package dataextraction;

import structural.Sensor;

import java.util.ArrayList;

public interface Extractor {

    public Object extractNextValue(String s);
    public long getCurrentTime();
    public boolean isFinish();

}
