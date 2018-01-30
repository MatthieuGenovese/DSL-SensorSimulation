package database;

import org.influxdb.InfluxDB;
import org.influxdb.InfluxDBFactory;
import org.influxdb.dto.Point;
import structural.Sensor;

import java.util.concurrent.TimeUnit;

/**
 * Created by Matthieu on 29/01/2018.
 */
public class InfluxDBManager {

    private InfluxDB db;

    public InfluxDBManager(){
        db = InfluxDBFactory.connect("http://localhost:8086", "root", "root");
        if(!db.databaseExists("DSL-SimulationSensor")){
            db.createDatabase("DSL-SimulationSensor");
        }
        db.setLogLevel(InfluxDB.LogLevel.BASIC);
        db.setDatabase("DSL-SimulationSensor");
    }

    public void writeSensor(Sensor s){
        Point point = Point.measurement("sensor" + s.getId())
                .time(System.currentTimeMillis(), TimeUnit.MILLISECONDS)
                .addField("type", s.getClass().getName())
                .addField("value", s.getValue().toString())
                .build();
        db.write(point);
    }
}