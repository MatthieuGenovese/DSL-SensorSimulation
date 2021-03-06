package laws;


import dataextraction.CSVExtractor;
import dataextraction.Extractor;
import dataextraction.JSONExtractor;

import java.io.FileNotFoundException;

/**
 * Created by Matthieu on 30/01/2018.
 */
public class ExtractionLaw implements  DataLaw {
    private Object value;
    private String sensorName;
    private Extractor extractor;
    private String name;
    private int currentLine;
    private long currentTime;
    private int frequency;
    private boolean finish;

    public Extractor getExtractor(){
        return extractor;
    }

    public long getCurrentTime() {
        return currentTime;
    }

    public void setCurrentTime(int currentTime) {
        this.currentTime = currentTime;
    }


    public ExtractionLaw(String name, String mode, String path, String sensor, String timeunit) throws FileNotFoundException {
        this.name = name;
        finish = false;
        currentTime = 0;

        switch(mode){
            case "csv":
                extractor = new CSVExtractor(path);
                break;
            case "json":
                extractor = new JSONExtractor(path);
        }
        switch (timeunit){
            case "ms":
                this.frequency = 1;
                break;
            case "d":
                this.frequency = 1000 * 60 * 60 * 24;
                break;
            case "min":
                this.frequency = 1000 * 60;
                break;
            case "s":
                this.frequency = 1000;
                break;
            case "h":
                this.frequency = 1000 * 60 * 60;
                break;
        }
        sensorName = sensor;
        currentLine = 0;
    }

    public int getFrequency(){
        return frequency;
    }

    public void setFrequency(int frequency){
        this.frequency = frequency;
    }

    public void setCurrentLine(int currentLine) {
        this.currentLine = currentLine;
    }

    public int getCurrentLine() {
        return currentLine;
    }

    @Override
    public String getName() {
        return name;
    }

    public DataLaw getLaw(){
        return this;
    }

    public Object getValue(){
        return value;
    }

    public boolean isFinish() {
        return finish;
    }

    public void setFinish(boolean finish) {
        this.finish = finish;
    }

    public boolean generateNextValue(long previousTime, long time){
        value = extractor.extractNextValue(sensorName);
        currentTime = extractor.getCurrentTime()*frequency;
        if(extractor.isFinish()){
            return  false;
        }
        return true;

    }
}
