package dsl;

import groovy.lang.Closure;
import laws.DataLaw;
import laws.FunctionLaw;
import laws.MarkovLaw;
import structural.Sensor;

import java.io.File;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Observer;

/**
 * Created by Matthieu on 31/01/2018.
 */
public class ErrorDetection {

    private String erreurs = "-----ERREURS DE COMPILATION-----\n\n";

    private String fileName;

    public ErrorDetection(String fileName){
        String[] array = fileName.split("\\.");
        this.fileName = array[0];
    }

    public void findAndAddLine(Exception e){
        for(int i = 0; i < e.getStackTrace().length; i++){
            if(e.getStackTrace()[i].getClassName().equalsIgnoreCase(fileName)){
                erreurs += "(ligne " + e.getStackTrace()[i].getLineNumber() + ")\n";
                break;
            }
        }
    }

    public void checkMarkovImplementation(Object states,Object transi) throws Exception{
        if(states instanceof ArrayList && transi instanceof ArrayList) {
            if (((ArrayList) states).size() == ((ArrayList)transi).size()) {
                if(((ArrayList)transi).get(0) instanceof ArrayList) {

                    for (int i = 0; i < ((ArrayList) transi).size(); i++) {
                        Double proba = 0.0;
                        if (((ArrayList)((ArrayList) transi).get(i)).size() != ((ArrayList) transi).size()) {
                            erreurs += "Erreur sur la loi de markov ! ";
                            throw new Exception();
                        }
                        for(int j = 0; j < ((ArrayList)((ArrayList) transi).get(i)).size(); j++){
                            bigDecimalExpected(((ArrayList)((ArrayList) transi).get(i)).get(j));
                            proba += Double.valueOf(((ArrayList)((ArrayList) transi).get(i)).get(j).toString());
                        }
                        if(!((proba + 0.0001 > 1 && proba <= 1) || (proba - 0.0001 < 1 && proba >= 1))){
                            erreurs += "la somme des probabilités de " +((ArrayList) transi).get(i) + " ne fait pas 1 !";
                            throw new Exception();
                        }
                    }
                }
                else{
                    erreurs += "Les probalités sont mal formulées !";
                }
            } else {
                erreurs += "Erreur sur la loi de markov ! ";
            }
        }
        else{
            erreurs += "Un des 2 paramètres de markov n'est pas une liste !";
        }
    }

    public void integerExpected(Object o){
        if (!(o instanceof Integer)){
            erreurs += "Le paramètre " + o.toString() + " est invalide! ";
        }
    }

    public void bigDecimalExpected(Object o) throws Exception{
        if (!(o instanceof BigDecimal)){
            erreurs += "Le paramètre " + o.toString() + " est invalide! ";
            throw new Exception();
        }
    }

    public void throwIncorrectWord(Exception e){
        erreurs += e.getMessage()+ " ";
        findAndAddLine(e);
    }

    public void integerExpected(ArrayList<Object> olist){
        for(Object o : olist) {
            if (!(o instanceof Integer)) {
                erreurs += "Le paramètre " + o.toString() + " est invalide! ";
            }
        }
    }

    public void throwFunctionErr(String err) throws Exception{
        throw  new Exception(err);
    }

    public void matriceExpected(Object olist){
        if(olist instanceof  ArrayList) {
            for (Object o : (ArrayList) olist) {
                if (!(o instanceof ArrayList)) {
                    erreurs += "Le paramètre " + o.toString() + "n'est pas une liste ! ";
                }
            }
        }
        else{
            erreurs += "Le paramètre " + olist.toString() + "n'est pas une liste ! ";
        }
    }

    public void compositeLawImplementation(Sensor s) throws Exception {
        DataLaw law = s.getSensorDataLaw();
        if(law instanceof MarkovLaw){
            erreurs += "Les sensors composites sont incompatibles avec les MarkovLaws !\n";
            throw new Exception();
        }
    }

    public void arraylistExpected(Object olist, int length){
        if (!(olist instanceof ArrayList)) {
            erreurs += "Le paramètre " + olist.toString() + " n'est pas une liste ! ";
            return;
        }
        if(((ArrayList) olist).size() < length){
            erreurs += "Le paramètre " + olist.toString() + " n'a pas assez d'element : requiert " + length + " éléments ! ";
        }
        if(((ArrayList) olist).size() > length){
            erreurs += "Le paramètre " + olist.toString() + " a  trop d'element : requiert " + length + " éléments ! ";
        }
    }

    public void sensorExist(ArrayList<Sensor> list, String name) throws Exception{
        for(Sensor s : list){
            if(s.getName().equalsIgnoreCase(name)){
                return;
            }
        }
        erreurs += "Le sensor " + name +" n'existe pas ! ";
        throw new Exception();
    }

    public  void filePathExpected(Object path){
        if(!(path instanceof String)){
            erreurs += "Le chemin est invalide ";
        }
        else{
            File f = new File((String) path);
            if(!f.exists()){
                erreurs += "Le fichier spécifié est introuvable ! ";
            }
        }
    }

    public String getErreurs() {
        return erreurs;
    }

    public void setErreurs(String erreurs) {
        this.erreurs = erreurs;
    }

    public void arraylistExpected(Object o){
        if(!(o instanceof ArrayList)){
            erreurs += "Le paramètre " + o.toString() + "n'est pas une liste ! ";
        }
    }
}
