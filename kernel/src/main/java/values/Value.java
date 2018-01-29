package values;

/**
 * Created by Matthieu on 29/01/2018.
 */
public interface Value<T> {

    public T getValue();
    public void setValue(T value);
}
