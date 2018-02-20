package dataextraction;

import structural.Building;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

/**
 * Created by Matthieu on 30/01/2018.
 */
public class CSVExtractor implements Extractor {
    String csvFile;
    BufferedReader br;
    String line;
    int currentLine;
    long currentTime;
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

    public long getCurrentTime() {
        return currentTime;
    }


    @Override
    public boolean isFinish() {
        return finish;
    }

    public void setFinish(boolean finish) {
        this.finish = finish;
    }

    public Object extractNextValue(String s) {
        int indexLine = 1;
        try {
            br = new BufferedReader(new FileReader(csvFile));
            while ((line = br.readLine()) != null) {
                String[] data = line.split(cvsSplitBy);
                if (indexLine > currentLine || currentLine == 0) {
                    if (s.equalsIgnoreCase(data[0])) {
                        currentTime = Integer.valueOf(data[1]);
                        currentLine = indexLine;
                        return data[2];
                    }
                }
                indexLine++;
            }
            finish = true;
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


}
