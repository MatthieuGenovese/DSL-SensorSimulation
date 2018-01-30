package structural;

/**
 * Created by Michael on 30/01/2018.
 */
public class States {
    private String[] value;

    public States(String[] states) {
        this.value = states;
    }

    public States(String states){
        this.value = states.split(",");
    }

    public String[] getValue() {
        return value;
    }

    public String getValue(int i){
        return value[i];
    }

    public void setValue(String[] states) {
        this.value = states;
    }
}
