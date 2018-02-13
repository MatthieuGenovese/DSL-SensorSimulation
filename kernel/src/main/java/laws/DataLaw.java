package laws;


/**
 * Created by Matthieu on 29/01/2018.
 */
public interface DataLaw {
    public String getName();
    public DataLaw getLaw();
    public int getFrequency();
    public void setFrequency(int frequency);
    public Object generateNextValue(long time);
}
