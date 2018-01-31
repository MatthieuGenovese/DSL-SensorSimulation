package dsl;

import java.io.File;
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

    public static void integerExpected(Object o)throws Exception{
        if (!(o instanceof Integer)){
            throw new Exception("Le paramètre " + o.toString() + " est invalide!");
        }
    }

    public static void integerExpected(ArrayList<Object> olist)throws Exception{
        for(Object o : olist) {
            if (!(o instanceof Integer)) {
                throw new Exception("Le paramètre " + o.toString() + " est invalide!");
            }
        }
    }

    public static void arraylistExpected(ArrayList<Object> olist) throws Exception{
        for(Object o : olist) {
            if (!(o instanceof ArrayList)) {
                throw new Exception("Le paramètre " + o.toString() + "n'est pas une liste !");
            }
        }
    }

    public static void filePathExpected(Object path) throws Exception{
        if(!(path instanceof String)){
            throw new Exception("Le chemin est invalide");
        }
        else{
            File f = new File((String) path);
            if(!f.exists()){
                throw new Exception("Le fichier spécifié est introuvable !");
            }
        }
    }

    public static void arraylistExpected(Object o) throws Exception{
        if(!(o instanceof ArrayList)){
            throw new Exception("Le paramètre " + o.toString() + "n'est pas une liste !");
        }
    }
}
