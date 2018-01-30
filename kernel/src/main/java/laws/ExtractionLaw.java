package laws;

import dataextraction.CSVExtractor;
import values.Value;

/**
 * Created by Matthieu on 30/01/2018.
 */
public class ExtractionLaw implements  DataLaw {
    private Value value;
    private int currentLine;

    public ExtractionLaw(){
        currentLine = 0;
    }

    public void setCurrentLine(int currentLine) {
        this.currentLine = currentLine;
    }

    public int getCurrentLine() {
        return currentLine;
    }

    public DataLaw getLaw(){
        return this;
    }
    public Value generateNextValue(){


        return value;
    }
}
