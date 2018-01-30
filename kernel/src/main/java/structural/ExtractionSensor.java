package structural;

import dataextraction.CSVExtractor;
import laws.ExtractionLaw;

/**
 * Created by Matthieu on 30/01/2018.
 */
public class ExtractionSensor extends Sensor {
    private CSVExtractor csvExtractor;


    public ExtractionSensor(CSVExtractor csvExtractor){
        this.csvExtractor = csvExtractor;
    }

    @Override
    public void tick(){
        ExtractionLaw law = (ExtractionLaw) getSensorDataLaw();
        law.setCurrentLine(csvExtractor.extractNextValue(this,law.getCurrentLine()));
        setSensorDataLaw(law);
    }
}
