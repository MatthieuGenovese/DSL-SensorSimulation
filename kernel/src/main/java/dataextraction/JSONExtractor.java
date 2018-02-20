package dataextraction;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Iterator;

/**
 * Created by Matthieu on 30/01/2018.
 */
public class JSONExtractor implements Extractor {
    String pathJsonFile;
    private long currentTime;
    private int currentLine;
    private boolean finish;

    @Override
    public boolean isFinish() {
        return finish;
    }

    public void setFinish(boolean finish) {
        this.finish = finish;
    }

    public JSONExtractor(String pathJsonFile) {
        this.pathJsonFile = pathJsonFile;
        currentTime = 0;
        currentLine = 0;
        finish = false;

    }


    public long getCurrentTime() {
        return currentTime;
    }
    public Object extractNextValue(String s){
        JSONParser parser = new JSONParser();
        try {
            Object obj = parser.parse(new FileReader(pathJsonFile));
            JSONObject jsonObject = (JSONObject) obj;
            JSONArray sensors = (JSONArray) jsonObject.get("sensors");
            Iterator<JSONObject> iteratorSensor = sensors.iterator();
            while (iteratorSensor.hasNext()) {
                JSONObject jsonSensor =  iteratorSensor.next();
                String nameSensor = String.valueOf(jsonSensor.get("name"));
                    if(nameSensor.equalsIgnoreCase(s)) {
                        JSONArray valuesSensor = (JSONArray) jsonSensor.get("values");
                        if(currentLine < valuesSensor.size()) {
                            JSONObject objValue = (JSONObject) valuesSensor.get(currentLine);
                            Object value = objValue.get("value");
                            currentTime = (long) objValue.get("time");
                            currentLine++;
                            return String.valueOf(value);
                        }
                    }
                }
            finish = true;
            }

        catch (ParseException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        currentLine++;
        finish = true;
        return "";
    }

}
