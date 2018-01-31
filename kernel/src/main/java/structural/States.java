package structural;

import java.util.ArrayList;

/**
 * Created by Michael on 30/01/2018.
 */
public class States {
    private ArrayList<String> value;

    public States(ArrayList<String> states) {
        this.value = states;
    }


    public ArrayList<String> getValue() {
        return value;
    }

    public String getValue(int i){
        return value.get(i);
    }

    public void setValue(ArrayList<String> states) {
        this.value = states;
    }
}
