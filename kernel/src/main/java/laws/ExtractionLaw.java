package laws;

import values.Value;

/**
 * Created by Matthieu on 30/01/2018.
 */
public class ExtractionLaw implements  DataLaw {
    private Value value;
    private String name;
    private int currentLine;
    private int frequency;

    public ExtractionLaw(String name){
        this.name = name;
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
    public Value generateNextValue(){


        return value;
    }
}