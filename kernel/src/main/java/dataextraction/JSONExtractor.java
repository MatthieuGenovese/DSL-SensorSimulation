package dataextraction;

import laws.ExtractionLaw;
import org.json.simple.parser.ParseException;
import structural.Building;
import structural.ExtractionSensor;
import structural.Sensor;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Iterator;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import java.util.ArrayList;

/**
 * Created by Matthieu on 30/01/2018.
 */
public class JSONExtractor implements Extractor {
    String pathJsonFile;
    int timeMin;
    int timeMax;

    public JSONExtractor(String pathJsonFile,int min,int max) {
        this.pathJsonFile = pathJsonFile;
        timeMin = min;
        timeMax = max;
    }

    public ArrayList<Sensor> extractSensors(){
        ArrayList<Sensor> sensorsList = new ArrayList<>();
        JSONParser parser = new JSONParser();
        try {
            Building b = new Building(1);
            Object obj = parser.parse(new FileReader(pathJsonFile));
            JSONObject jsonObject = (JSONObject) obj;
            JSONArray sensors = (JSONArray) jsonObject.get("sensors");
            Iterator<JSONObject> iteratorSensor = sensors.iterator();
            while (iteratorSensor.hasNext()) {
                Sensor newSensor = new ExtractionSensor(this);
                newSensor.setBuilding(b);
                JSONObject jsonSensor =  iteratorSensor.next();
                long nameSensor = (long) jsonSensor.get("name");
                JSONArray valuesSensor = (JSONArray) jsonSensor.get("values");
                newSensor.setId((int) nameSensor);
                ExtractionLaw extractionLaw = new ExtractionLaw("extract");
                newSensor.setSensorDataLaw(extractionLaw);
                sensorsList.add(newSensor);
                b.addSensor(newSensor);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return sensorsList;
    }




    public int extractNextValue(Sensor s , int ligne){
        JSONParser parser = new JSONParser();
        try {
            Object obj = parser.parse(new FileReader(pathJsonFile));
            JSONObject jsonObject = (JSONObject) obj;
            JSONArray sensors = (JSONArray) jsonObject.get("sensors");
            Iterator<JSONObject> iteratorSensor = sensors.iterator();
            while (iteratorSensor.hasNext()) {
                JSONObject jsonSensor =  iteratorSensor.next();
                long nameSensor = (long) jsonSensor.get("name");
                    if(nameSensor == s.getId()) {
                        JSONArray valuesSensor = (JSONArray) jsonSensor.get("values");
                        if(ligne < valuesSensor.size()) {
                            JSONObject objValue = (JSONObject) valuesSensor.get(ligne);
                            long value = (long) objValue.get("value");
                            long time = (long) objValue.get("time");
                            if(time <= timeMax && time >= timeMin){
                                s.setValue(String.valueOf(value));
                                s.setTime(time);
                            }
                        }
                    }
                }
            } catch (ParseException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return ligne+1;
    }

}
