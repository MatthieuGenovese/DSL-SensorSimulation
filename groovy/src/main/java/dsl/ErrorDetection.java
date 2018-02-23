package dsl;

import dataextraction.CSVExtractor;
import dataextraction.JSONExtractor;
import groovy.lang.Closure;
import laws.*;
import structural.ExtractionSensor;
import structural.Sensor;

import java.io.File;
import java.io.FileNotFoundException;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Observer;

/**
 * Created by Matthieu on 31/01/2018.
 */
public class ErrorDetection {

    private String erreurs = "-----ERREURS DE COMPILATION-----\n\n";
    private CSVExtractor csvExtractor;
    private JSONExtractor jsonExtractor;
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

    public boolean dataLawExist(Object datalaw){
        if(datalaw == null){
            erreurs += "la datalaw " + datalaw + " n'existe pas !";
            System.out.println("yoyo");
            Exception e = new Exception();
            findAndAddLine(e);
            return true;
        }
        return false;
    }

    public void goodSensorExpected(Object name, ArrayList<Sensor> sensors, Object list, Object unit, Object datalaw){
        integerExpected((ArrayList)list);
        timeUnitExpected(unit);
        dataLawExist(datalaw);
        if(sensorExistBool(sensors, (String) name)){
            erreurs += "Le sensor " + name + " existe déja !";
        }
    }

    public boolean checkMarkovImplementation(Object states,Object transi, Object f, Object unit) throws Exception{
        boolean rep = false;
        integerExpected(f);
        if(timeUnitExpected(unit)){
            rep = true;
        }
        if(states instanceof ArrayList && transi instanceof ArrayList) {
            if (((ArrayList) states).size() == ((ArrayList)transi).size()) {
                if(((ArrayList)transi).get(0) instanceof ArrayList) {
                    for (int i = 0; i < ((ArrayList) transi).size(); i++) {
                        Double proba = 0.0;
                        if (((ArrayList)((ArrayList) transi).get(i)).size() != ((ArrayList) transi).size()) {
                            erreurs += "Erreur sur la loi de markov ! ";
                            rep = true;
                        }
                        for(int j = 0; j < ((ArrayList)((ArrayList) transi).get(i)).size(); j++){
                            bigDecimalExpected(((ArrayList)((ArrayList) transi).get(i)).get(j));
                            proba += Double.valueOf(((ArrayList)((ArrayList) transi).get(i)).get(j).toString());
                        }
                        if(!((proba + 0.0001 > 1 && proba <= 1) || (proba - 0.0001 < 1 && proba >= 1))){
                            erreurs += "la somme des probabilités de " +((ArrayList) transi).get(i) + " ne fait pas 1 !";
                            rep = true;
                        }
                    }
                }
                else{
                    erreurs += "Les probalités sont mal formulées !";
                    rep = true;
                }
            } else {
                erreurs += "Erreur sur la loi de markov ! ";
                rep = true;
            }
        }
        else{
            erreurs += "Un des 2 paramètres de markov n'est pas une liste !";
            rep = true;
        }
        return rep;
    }

    public boolean integerExpected(Object o){
        if (!(o instanceof Integer)){
            erreurs += "Le paramètre " + o.toString() + " est invalide! ";
            return true;
        }
        return false;
    }

    public void extractionSensorExpected(Sensor s){
        if(!(s instanceof ExtractionSensor)){
            erreurs+= "l' ExtractionSensor " + s.getName() + " n'existe pas !";
        }
    }

    public boolean checkRandomImplementation(Object interval, int size, Object f, Object unit){
        boolean rep = false;
        if(!arraylistExpected(interval, size)) {
            if(randomExpected((ArrayList<Integer>) interval)){
                rep = true;
            }
        }
        else{
            rep = true;
        }
        if(integerExpected(f)) {
            rep = true;
        }
        if(timeUnitExpected(unit)){
            rep = true;
        }
        return rep;
    }

    public boolean randomExpected(ArrayList<Integer> interval){
        if(interval.get(0) >= interval.get(1)){
            erreurs += "Le premier nombre doit être inférieur au second !";
            Exception e = new Exception();
            findAndAddLine(e);
            return true;
        }
        return false;
    }

    public void bigDecimalExpected(Object o){
        if (!(o instanceof BigDecimal)){
            erreurs += "Le paramètre " + o.toString() + " est invalide! ";
        }
    }

    public void throwIncorrectWord(Exception e){
        erreurs += e.getMessage()+ " ";
        findAndAddLine(e);
    }

    public boolean integerExpected(ArrayList<Object> olist){
        for(Object o : olist) {
            if (!(o instanceof Integer)) {
                erreurs += "Le paramètre " + o.toString() + " est invalide! ";
                return true;
            }
        }
        return false;
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

    public void dateExpected(Object obj){
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss a");
        try {
            Date startDate = formatter.parse((String) obj);
        }
        catch(Exception e){
            erreurs += "La string donnée n'est pas une date !";
        }
    }

    public boolean timeUnitExpected(Object obj){
        switch ((String)obj){
            case "ms":
                break;
            case "d":
                break;
            case "min":
                break;
            case "s":
                break;
            case "h":
                break;
            default:
                erreurs += "L'unité de temps " + obj +" n'existe pas !";
                Exception e = new Exception();
                findAndAddLine(e);
                return true;
        }
        return false;
    }

    public void addErreur(String err){
        erreurs += err;
    }

    public void compositeLawImplementation(Sensor s){
        DataLaw law = s.getSensorDataLaw();
        if(law instanceof MarkovLaw){
            MarkovLaw markov = (MarkovLaw) law;
            for(Object value : markov.getStates().getValue()){
                if(!(value instanceof Number)){
                    erreurs += "Les sensors composites sont incompatibles avec les lois qui ne retournent pas des nombres !";
                    Exception e = new Exception();
                    findAndAddLine(e);
                    return;
                }
            }
        }
    }

    public void goodCompositeSensor(Object name, Object list, Object unit, ArrayList<Sensor> sensors, Object sensor) throws Exception {
        integerExpected((ArrayList) list);
        timeUnitExpected(unit);
        Sensor s = sensorExist(sensors, (String) sensor);
        if(s != null){
            compositeLawImplementation(s);
        }

        if(sensorExistBool(sensors, (String) name)){
            erreurs += "Le sensor " + name + " existe déja !";
        }
    }

    public boolean arraylistExpected(Object olist, int length){
        boolean rep = false;
        if (!(olist instanceof ArrayList)) {
            erreurs += "Le paramètre " + olist.toString() + " n'est pas une liste ! ";
            rep = true;
        }
        if(((ArrayList) olist).size() < length){
            erreurs += "Le paramètre " + olist.toString() + " n'a pas assez d'element : requiert " + length + " éléments ! ";
            rep = true;
        }
        if(((ArrayList) olist).size() > length){
            erreurs += "Le paramètre " + olist.toString() + " a  trop d'element : requiert " + length + " éléments ! ";
            rep = true;
        }
        return rep;
    }

    public boolean modeExpected(Object m){
        switch ((String) m){
            case "csv":
                break;
            case "json":
                break;
            default:
                erreurs += "mode de lecture inconnue ! (les modes acceptés sont json et csv)";
                Exception e = new Exception();
                findAndAddLine(e);
                return true;
        }
        return false;
    }

    public boolean sensorExistInFile(Object sensor, Object path, Object mode) throws FileNotFoundException {
        switch((String) mode){
            case "csv":
                csvExtractor = new CSVExtractor((String) path);
                String res = (String) csvExtractor.extractNextValue((String)sensor);
                if(res.equalsIgnoreCase("")){
                    erreurs += "le fichier donnée ne contient aucun sensor " + sensor +" !";
                    Exception e = new Exception();
                    findAndAddLine(e);
                    return true;
                }
                break;
            case "json":
                jsonExtractor = new JSONExtractor((String)path);
                String res2 = (String) jsonExtractor.extractNextValue((String)sensor);
                if(res2.equalsIgnoreCase("")){
                    erreurs += "le fichier donnée ne contient aucun sensor " + sensor +" !";
                    Exception e = new Exception();
                    findAndAddLine(e);
                    return true;
                }
        }
        return false;
    }

    public boolean goodExtractionSensor(Object name, ArrayList<Sensor> sensors, Object list, Object m, Object p, Object t, Object s) throws FileNotFoundException {
        boolean rep = false;
        integerExpected((ArrayList) list);
        if(modeExpected(m)) {
            rep = true;
        }
        filePathExpected(p);
        if(timeUnitExpected(t)){
            rep = true;
        }
        if(sensorExistInFile(s,p,m)){
            rep =  true;
        }
        if(sensorExistBool(sensors, (String) name)){
            erreurs += "Le sensor " + name + " existe déjà !";
        }
        return rep;
    }

    public boolean sensorExistBool(ArrayList<Sensor> list, String name){
        for(Sensor s : list){
            if(s.getName().equalsIgnoreCase(name)){
                return true;
            }
        }
        return false;
    }

    public Sensor sensorExist(ArrayList<Sensor> list, String name){
        for(Sensor s : list){
            if(s.getName().equalsIgnoreCase(name)){
                return s;
            }
        }
        erreurs += "Le sensor " + name +" n'existe pas ! ";
        Exception e = new Exception();
        findAndAddLine(e);
        return null;
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
            erreurs += "Le paramètre " + o.toString() + " n'est pas une liste ! ";
        }
    }
}
