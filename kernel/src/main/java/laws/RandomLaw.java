package laws;


/**
 * Created by Matthieu on 29/01/2018.
 */
public class RandomLaw implements DataLaw {
    private String name;
    private Object value;
    private int frequency;
    private int min;
    private int max;

    public RandomLaw(String name, int frequency, String unit){
        this.name = name;
        switch(unit){
            case "ms":
                this.frequency = frequency;
                break;
            case "d":
                this.frequency = frequency * 1000 * 60 * 60 * 24;
                break;
            case "min":
                this.frequency = frequency * 1000 * 60;
                break;
            case "s":
                this.frequency = frequency * 1000;
                break;
            case "h":
                this.frequency = frequency * 1000 * 60 * 60;
                break;
        }
    }

    public int getFrequency(){
        return frequency;
    }

    public void setFrequency(int frequency){
        this.frequency = frequency;
    }

    @Override
    public String getName() {
        return name;
    }

    public DataLaw getLaw(){
        return this;
    }

    public int getMin() {
        return min;
    }

    public void setMin(int min) {
        this.min = min;
    }

    public int getMax() {
        return max;
    }

    public void setMax(int max) {
        this.max = max;
    }

    public Object generateNextValue(long time){
        value = min + (int)(Math.random() * ((max - 1) + 1));
        return value;
    }
}
