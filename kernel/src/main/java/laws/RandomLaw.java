package laws;

/**
 * Created by Matthieu on 29/01/2018.
 */
public class RandomLaw implements DataLaw {
    private int value;

    public RandomLaw(){
        this.value = 0;
    }

    public DataLaw getLaw(){
        return this;
    }

    public int generateNextValue(){
        this.value = 1 + (int)(Math.random() * ((1000 - 1) + 1));
        return value;
    }
}
