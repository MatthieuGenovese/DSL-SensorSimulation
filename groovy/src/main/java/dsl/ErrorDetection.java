package dsl;

import groovy.lang.Closure;

import java.io.File;
import java.util.ArrayList;

/**
 * Created by Matthieu on 31/01/2018.
 */
public class ErrorDetection {

    private String erreurs = "-----ERREURS DE COMPILATION-----\n\n";

    private String fileName;

    public ErrorDetection(String fileName){
        System.out.println(fileName);
        String[] array = fileName.split("\\.");
        this.fileName = array[0];
    }

    public void findAndAddLine(Exception e){
        for(int i = 0; i < e.getStackTrace().length; i++){
            if(e.getStackTrace()[i].getClassName().equalsIgnoreCase(fileName)){
                erreurs += "(ligne " + e.getStackTrace()[i].getLineNumber() + ")\n";
                e.printStackTrace();
                break;
            }
        }
    }

    public void checkMarkovImplementation(Object states,Object transi){
        if(states instanceof ArrayList && transi instanceof ArrayList) {
            if (((ArrayList) states).size() == ((ArrayList)transi).size()) {
                if(((ArrayList)transi).get(0) instanceof ArrayList) {
                    for (int i = 0; i < ((ArrayList) transi).size(); i++) {
                        if (((ArrayList)((ArrayList) transi).get(i)).size() != ((ArrayList) transi).size()) {
                            erreurs += "Erreur sur la loi de markov ! ";
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

    public void arraylistExpected(ArrayList<Object> olist, int length){
        for(Object o : olist) {
            if (!(o instanceof ArrayList)) {
                erreurs += "Le paramètre " + o.toString() + "n'est pas une liste ! ";
            }
            if(((ArrayList) o).size() < length){
                erreurs += "Le paramètre " + o.toString() + " n'a pas assez d'element : requiert " + length + " éléments ! ";
            }
            if(((ArrayList) o).size() > length){
                erreurs += "Le paramètre " + o.toString() + " a  trop d'element : requiert " + length + " éléments ! ";
            }
        }
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
