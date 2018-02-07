package structural;

import dataextraction.CSVExtractor;
import dataextraction.Extractor;
import dataextraction.JSONExtractor;
import laws.ExtractionLaw;

/**
 * Created by Matthieu on 30/01/2018.
 */
public class ExtractionSensor extends Sensor {
    private CSVExtractor csvExtractor;
    private JSONExtractor jsonExtractor;
    private Extractor extractor;


    public ExtractionSensor(CSVExtractor csvExtractor){
        this.extractor = csvExtractor;
    }
    public ExtractionSensor(JSONExtractor jsonExtractor){
        this.extractor = jsonExtractor;
    }

    @Override
    public void tick(){
        ExtractionLaw law = (ExtractionLaw) getSensorDataLaw();
            law.setCurrentLine(extractor.extractNextValue(this,law.getCurrentLine()));
        setSensorDataLaw(law);
    }
}
