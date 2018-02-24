package structural;

import laws.ExtractionLaw;

import java.util.ArrayList;

/**
 * Created by Matthieu on 20/02/2018.
 */
public class ExtractionSensor extends Sensor{
    private long echantillonageLong;
    private ArrayList<Integer> noise;
    private long minOffset;
    private long maxOffset;

    public ExtractionSensor(String name){
        super(name);
        noise = new ArrayList<>();
        minOffset = Long.MIN_VALUE;
        maxOffset = Long.MAX_VALUE;

    }

    public void setNoise(ArrayList<Integer> noise){
        this.noise = noise;
    }

    public long getMinOffset() {
        return minOffset;
    }

    public void setMinOffset(long minOffset) {
        this.minOffset = minOffset;
    }

    public long getMaxOffset() {
        return maxOffset;
    }

    public void setMaxOffset(long maxOffset) {
        this.maxOffset = maxOffset;
    }

    @Override
    public void tick(){
        if(sensorDataLaw.generateNextValue(previousTime, time)){
            echantillonageLong = ((ExtractionLaw) sensorDataLaw).getCurrentTime();
            time += echantillonageLong;
            if(this.getMinOffset() <= ((ExtractionLaw) getSensorDataLaw()).getExtractor().getCurrentTime() && this.getMaxOffset() >= ((ExtractionLaw) getSensorDataLaw()).getExtractor().getCurrentTime()) {
                if (!noise.isEmpty()) {
                    System.out.println("random : " + (noise.get(0) + (int) (Math.random() * ((noise.get(1) - 1) + 1))));
                    value = String.valueOf(Integer.valueOf((String) sensorDataLaw.getValue()) + noise.get(0) + (int) (Math.random() * ((noise.get(1) - 1) + 1)));
                } else {
                    value = sensorDataLaw.getValue();
                }
                setChanged();
                notifyObservers();
            }
        }
        else{
            finish = true;
        }

    }
}
