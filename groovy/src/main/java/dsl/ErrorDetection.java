package dsl;

import java.util.ArrayList;

/**
 * Created by Matthieu on 31/01/2018.
 */
public class ErrorDetection {

    public static  void checkMarkovImplementation(ArrayList<String> states, ArrayList<ArrayList<Double>> transi) throws Exception{
        if(states.size() == transi.size()){
            for(int i = 0; i < transi.size(); i++){
                if(transi.get(i).size() != transi.size()){
                    throw new Exception("markov pas content");
                }
            }
        }
        else{
            throw new Exception("markov pas content");
        }
    }
}
