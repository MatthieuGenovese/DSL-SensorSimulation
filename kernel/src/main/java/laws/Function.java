package laws;


import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.function.Predicate;

/**
 * Created by Matthieu on 31/01/2018.
 *
 */
public class Function {

    public ArrayList<String> operator;
    public ArrayList<BigDecimal> predicate;
    public String nom;
    public ArrayList<BigDecimal> values;

    public Function(){
        values = new ArrayList<>();
        predicate = new ArrayList<>();
        operator = new ArrayList<>();
    }

    public void name(String nom){
        this.nom = nom;
    }

    public void interval(ArrayList<Object> list){
        values.add((BigDecimal)list.get(0));
        operator.add((String)list.get(1));
        predicate.add((BigDecimal)list.get(2));
    }

    public String toString(){
        String pred = "";
        String act = "";
        String op = "";
        for(int i = 0; i < predicate.size(); i++){
            pred+= predicate.get(i) + ", ";
            act+= values.get(i) + ", ";
            op += operator.get(i);
        }
        return "fonction : " + nom + " predicats : [ " + op + " " + pred + " ]" + " values : [ " + act + " ]\n";
    }
}
