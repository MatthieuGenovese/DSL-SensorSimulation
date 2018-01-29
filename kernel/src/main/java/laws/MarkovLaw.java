package laws;

/**
 * Created by Matthieu on 29/01/2018.
 */
public class MarkovLaw implements DataLaw {
    int value;

    public MarkovLaw(){
        value = 0;
    }

    public  DataLaw getLaw(){
        return this;
    }

    public int generateNextValue(){
        return value;
    }
}
