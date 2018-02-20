package dataextraction;

import structural.Building;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

/**
 * Created by Matthieu on 30/01/2018.
 */
public class CSVExtractor implements Extractor{
    String csvFile;
    BufferedReader br;
    String line;
    int currentLine;
    int currentTime;
    String cvsSplitBy;
    boolean finish;
    int timeMin;
    int timeMax;


    public CSVExtractor(String csvFile) throws FileNotFoundException {
        this.csvFile = csvFile;
        currentLine = 0;
        currentTime = 0;
        finish = false;
        line = "";
        cvsSplitBy = ",";
    }

    public int getCurrentTime(){
        return currentTime;
    }


    @Override
    public boolean isFinish() {
        return finish;
    }

    public void setFinish(boolean finish) {
        this.finish = finish;
    }

    public Object extractNextValue(String s){
        int indexLine = 1;
        try {
            Building b = new Building(1);
            br = new BufferedReader(new FileReader(csvFile));
            while ((line = br.readLine()) != null) {
                String[] data = line.split(cvsSplitBy);
                if (indexLine > currentLine || currentLine == 0) {
                    if(s.equalsIgnoreCase(data[0])){
                        currentTime = Integer.valueOf(data[1]);
                        currentLine = indexLine;
                        return data[2];
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
        currentLine = indexLine;
        finish = true;
        return "";
    }



    /*public ArrayList<Sensor> extractSensors(){
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
                        newSensor.setName("csvSensor");
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
    }*/
}
