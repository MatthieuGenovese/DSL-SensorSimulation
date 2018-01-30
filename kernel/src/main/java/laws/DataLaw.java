package laws;

import values.Value;

/**
 * Created by Matthieu on 29/01/2018.
 */
public interface DataLaw {
    public String getName();
    public DataLaw getLaw();
    public Value generateNextValue();
}
