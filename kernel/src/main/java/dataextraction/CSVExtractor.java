package dataextraction;

import laws.ExtractionLaw;
import structural.Building;
import structural.ExtractionSensor;
import structural.Sensor;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by Matthieu on 30/01/2018.
 */
public class CSVExtractor implements Extractor{
    String csvFile;
    BufferedReader br;
    String line;
    String cvsSplitBy;
    int timeMin;
    int timeMax;


    public CSVExtractor(String csvFile,int min,int max) throws FileNotFoundException {
        this.csvFile = csvFile;
        line = "";
        cvsSplitBy = ",";
        timeMin = min;
        timeMax = max;
    }


    public int extractNextValue(Sensor s , int ligne){
        //int newLine = Integer.MAX_VALUE;
        int indexLine = 1;
        try {
            Building b = new Building(1);
            br = new BufferedReader(new FileReader(csvFile));
            while ((line = br.readLine()) != null) {
                String[] data = line.split(cvsSplitBy);
                if (indexLine > ligne || ligne == 0) {
                    if(String.valueOf(s.getId()).equalsIgnoreCase(data[0]) && Integer.valueOf(data[1])<= timeMax && Integer.valueOf(data[1]) >= timeMin ){
                        s.setValue(data[2]);
                        System.out.println("ligne : " + ((ExtractionLaw) s.getSensorDataLaw()).getCurrentLine());
                        s.setTime(Long.valueOf(data[1]));
                        //newLine = indexLine;
                        return indexLine;
                    }
                }
                indexLine++;
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return indexLine;
    }



    public ArrayList<Sensor> extractSensors(){
        ArrayList<Sensor> sensorsList = new ArrayList<>();
        try {
            Building b = new Building(1);
            br = new BufferedReader(new FileReader(csvFile));
            while ((line = br.readLine()) != null) {
                String[] data = line.split(cvsSplitBy);
                if(!b.containsSensor(data[0])){
                        //System.out.println("s:" + data[0] + " t:" + data[1] + " v:"+ data[2]);
                        Sensor newSensor = new ExtractionSensor(this);
                        newSensor.setBuilding(b);
                        newSensor.setId(Integer.valueOf(data[0]));
                        ExtractionLaw extractionLaw = new ExtractionLaw("extract");
                        extractionLaw.setCurrentLine(0);
                        newSensor.setSensorDataLaw(extractionLaw);
                        //newSensor.setTime(Long.valueOf(data[1]));
                        //newSensor.setValue(new ExtractionValue(data[2]));
                        sensorsList.add(newSensor);
                        b.addSensor(newSensor);
                }
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return sensorsList;
    }
}
