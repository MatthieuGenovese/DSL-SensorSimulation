package values;

/**
 * Created by Matthieu on 29/01/2018.
 */
public class Nombre implements Value<Integer> {
    private int value;

    public Nombre(){
        this.value = 0;
    }
    public Integer getValue(){
        return value;
    }

    public void setValue(Integer value){
        this.value = value;
    }

    @Override
    public String toString(){
        return String.valueOf(value);
    }
}
