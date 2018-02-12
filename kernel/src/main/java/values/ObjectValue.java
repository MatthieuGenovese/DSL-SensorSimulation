package values;

/**
 * Created by Matthieu on 12/02/2018.
 */
public class ObjectValue implements Value<Object> {
    private Object value;

    public ObjectValue(){
        this.value = null;
    }
    public Object getValue(){
        return value;
    }

    public void setValue(Object value){
        this.value = value;
    }

    @Override
    public String toString(){
        return String.valueOf(value);
    }
}
