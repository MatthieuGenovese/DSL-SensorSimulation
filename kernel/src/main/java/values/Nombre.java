package values;

/**
 * Created by Matthieu on 29/01/2018.
 */
public class Nombre implements Value<Number> {
    private Number value;

    public Nombre(){
        this.value = 0;
    }
    public Number getValue(){
        return value;
    }

    public void setValue(Number value){
        this.value = value;
    }

    @Override
    public String toString(){
        return String.valueOf(value);
    }
}
