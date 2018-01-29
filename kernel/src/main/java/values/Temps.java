package values;

/**
 * Created by Matthieu on 29/01/2018.
 */
public class Temps implements Value<String> {

    private String value;

    public Temps(){
        this.value = "beau";
    }

    public String getValue(){
        return value;
    }

    public void setValue(String temps){
        this.value = temps;
    }

    @Override
    public String toString(){
        return value;
    }
}
