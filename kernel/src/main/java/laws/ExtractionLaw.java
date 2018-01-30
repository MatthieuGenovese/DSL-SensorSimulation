package laws;

import values.Value;

/**
 * Created by Matthieu on 30/01/2018.
 */
public class ExtractionLaw implements  DataLaw {
    private Value value;
    private int currentLine;

    public DataLaw getLaw(){
        return this;
    }
    public Value generateNextValue(){

        return value;
    }
}
