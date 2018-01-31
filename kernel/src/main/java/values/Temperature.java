package values;

/**
 * Created by Matthieu on 29/01/2018.
 */
public class Temperature implements Value<String> {

    private String value;

    public Temperature(){
        this.value = "C°";
    }

    public String getValue(){
        return  value;
    };

    public void setValue(String value){
        this.value = value;
    }

    @Override
    public String toString(){
        return value;
    }
}
