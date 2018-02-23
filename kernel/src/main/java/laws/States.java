package laws;

import java.util.ArrayList;

/**
 * Created by Michael on 30/01/2018.
 */
public class States {

    private ArrayList<Object> value;

    public States(ArrayList<Object> states) {
        this.value = states;
    }


    public ArrayList<Object> getValue() {
        return value;
    }

    public Object getValue(int i){
        return value.get(i);
    }

    public void setValue(ArrayList<Object> states) {
        this.value = states;
    }
}
