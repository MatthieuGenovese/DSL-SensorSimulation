package dataextraction;

/**
 * Created by Matthieu on 30/01/2018.
 */
public class JSONExtractor implements Extractor {
    String pathJsonFile;
    private int currentTime;
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
        finish = false;

    }

    /*public ArrayList<Sensor> extractSensors(){
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
                newSensor.setId((int) nameSensor);
                newSensor.setName("jsonSensor");
                ExtractionLaw extractionLaw = new ExtractionLaw("extract");
                newSensor.setSensorDataLaw(extractionLaw);
                sensorsList.add(newSensor);
                b.addSensor(newSensor);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return sensorsList;
    }*/
    public Object extractNextValue(String s){
        return null;
    }

    public int getCurrentTime() {
        return currentTime;
    }
 /*   public int extractNextValue(Sensor s , int ligne){
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
    }*/

}
