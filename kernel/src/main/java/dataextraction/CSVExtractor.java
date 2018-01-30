package dataextraction;

import laws.ExtractionLaw;
import structural.Building;
import structural.ExtractionSensor;
import structural.Sensor;
import values.ExtractionValue;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by Matthieu on 30/01/2018.
 */
public class CSVExtractor {
    String csvFile;
    BufferedReader br;
    String line;
    String cvsSplitBy;


    public CSVExtractor(String csvFile) throws FileNotFoundException {
        this.csvFile = csvFile;
        line = "";
        cvsSplitBy = ",";
    }


    public int extractNextValue(Sensor s , int ligne){
        int newLine = 999;
        int indexLine = 0;
        try {
            Building b = new Building(1);
            br = new BufferedReader(new FileReader(csvFile));
            while ((line = br.readLine()) != null) {
                String[] data = line.split(cvsSplitBy);

                if (indexLine > ligne) {
                    if( String.valueOf(s.getId()).equalsIgnoreCase(data[0])){
                        s.setValue(new ExtractionValue(data[2]));
                        s.setTime(Long.valueOf(data[1]));
                        newLine = indexLine;
                        break;
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
        return newLine;
    }



    public ArrayList<Sensor> extractSensors(){
        ArrayList<Sensor> sensorsList = new ArrayList<>();
        int cpt =0;
        try {
            Building b = new Building(1);
            br = new BufferedReader(new FileReader(csvFile));
            while ((line = br.readLine()) != null) {
                String[] data = line.split(cvsSplitBy);
                if(!b.containsSensor(data[0])){
                    System.out.println("s:" + data[0] + " t:" + data[1] + " v:"+ data[2]);
                    Sensor newSensor = new ExtractionSensor(this);
                    newSensor.setBuilding(b);
                    newSensor.setId( Integer.valueOf(data[0]));
                    ExtractionLaw extractionLaw = new ExtractionLaw("extract");
                    extractionLaw.setCurrentLine(cpt);
                    newSensor.setSensorDataLaw(extractionLaw);
                    newSensor.setTime(Long.valueOf(data[1]));
                    newSensor.setValue(new ExtractionValue(data[2]));
                    sensorsList.add(newSensor);
                    b.addSensor(newSensor);
                }
                cpt++;
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
