package structural;

/**
 * Created by Michael on 12/02/2018.
 */
public class CompositeSensor {
    private String name;
    private Sensor sensor;

    public CompositeSensor(String name, Sensor sensor) {
        this.name = name;
        this.sensor = sensor;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Sensor getSensor() {
        return sensor;
    }

    public void setSensor(Sensor sensor) {
        this.sensor = sensor;
    }
}
