package structural;

import java.util.ArrayList;

/**
 * Created by Matthieu on 29/01/2018.
 */
public class Area {
    private int id;
    private ArrayList<Sensor> sensorList;

    public Area(ArrayList<Sensor> sensorList){
        this.sensorList = sensorList;
        this.id = 0;
    }

    public Area(int id){
        this.sensorList = new ArrayList<>();
        this.id = id;
    }

    public ArrayList<Sensor> getSensorList() {
        return sensorList;
    }

    public int getId(){
        return id;

    }

    public void addSensor(Sensor s){
        sensorList.add(s);
    }

    public boolean containsSensor(String idSensor){
        boolean result = false;
        for(Sensor s : sensorList){
            result =  s.getId()== Integer.valueOf(idSensor);
            if(result){
                return result;
            }
        }
        return result;
    }

    @Override
    public String toString(){
        String res = "";
        res += "Batiment : " + getId() + "\n Sensors : \n";
        for(Sensor s : sensorList){
            res+= " \t- " + s.toString() + "\n";
        }
        return res;
    }
}
