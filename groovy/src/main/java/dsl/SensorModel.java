package dsl;

import dataextraction.Extractor;
import groovy.lang.Binding;
import groovy.lang.Closure;
import launcher.App;
import laws.*;
import structural.Area;
import structural.ExtractionSensor;
import structural.Sensor;

import java.io.FileNotFoundException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;

public class SensorModel {
	private ArrayList<Area> areas;
	private ArrayList<Sensor> sensors;
	private ArrayList<DataLaw> laws;
	private ArrayList<Sensor> sensorsComposite;
	private Extractor extractor;
	private Binding binding;
	
	public SensorModel(Binding binding) {
		sensorsComposite = new ArrayList<>();
		this.areas = new ArrayList<Area>();
		this.sensors = new ArrayList<Sensor>();
		this.laws = new ArrayList<DataLaw>();
	}

	public ArrayList<Sensor> getSensors() {
		return sensors;
	}

	public boolean containsBuilding(Integer id){
		for(Area b : areas){
			if(b.getId() == id){
				return true;
			}
		}
		return false;
	}

	public Sensor getSensor(String name){
		for(Sensor s : sensors){
			if(s.getName().equalsIgnoreCase(name)){
				return s;
			}
		}
		return null;
	}

	public ArrayList<Sensor> getCompositesSensors(){
		return sensorsComposite;
	}

	public Sensor getCompositeSensor(String name){
		for(Sensor s : sensorsComposite){
			if(s.getName().equalsIgnoreCase(name)){
				return  s;
			}
		}
		return null;
	}

	public DataLaw getLaw(String name){
		for(DataLaw dataLaw : laws){
			if(dataLaw.getName() == name){
				return dataLaw;
			}
		}
		return null;
	}

	public Area getBuilding(int b){
		for(Area area : areas){
			if(area.getId() == b){
				return area;
			}
		}
		return null;
	}


	public void createExtractionLaw(String name, String mode, String path, String sensor, String timeunit) throws FileNotFoundException {
		ExtractionLaw law = new ExtractionLaw(name, mode, path, sensor, timeunit);
		laws.add(law);
	}

	public void createSensor(String name, Area b, DataLaw law, Integer e, String unit) {
		Sensor sensor = new Sensor(name, e, unit);
		sensor.setId(this.sensors.size());
		sensor.setSensorDataLaw(law);
		sensor.setArea(b);
		if(sensor.getSensorDataLaw() instanceof CompositeLaw){
			this.sensorsComposite.add(sensor);
		}
		else {
			if (sensor.getSensorDataLaw() instanceof FunctionLaw){
				((FunctionLaw) law).setNbSensors(((FunctionLaw) law).getNbSensors()+1);
			}
			this.sensors.add(sensor);
			for(Area area : areas){
				if (b.equals(area)){
					b.addSensor(sensor);
					break;
				}
			}
		}

	}

	public void createExtractionSensor(String name, Area b, DataLaw law) {
		Sensor s = new ExtractionSensor(name);
		s.setId(this.sensors.size());
		s.setSensorDataLaw(law);
		s.setArea(b);
		this.sensors.add(s);
		for(Area area : areas){
			if (b.equals(area)){
				b.addSensor(s);
				break;
			}
		}
	}

	public void createBuilding(Integer id){
		Area b = new Area(id);
		areas.add(b);
	}

	public void createFunction(String name, Closure cl){
		FunctionLaw function = new FunctionLaw(name,cl);
		laws.add(function);
	}

	public void createMarkovLaw(String name, ArrayList<Object> states, ArrayList<ArrayList<BigDecimal>> map, int freq, String unit){
		Matrix matrix = new Matrix(map);
		States state = new States(states);
		MarkovLaw law = new MarkovLaw(name, state, matrix, freq, unit);
		laws.add(law);
	}

	public void createRandomLaw(String name, ArrayList<Integer> integers, int frequency, String unit){
		RandomLaw law = new RandomLaw(name, frequency, unit);
		law.setMin(integers.get(0));
		law.setMax(integers.get(1));
		laws.add(law);
	}

	public void runApp(Date start, Date stop){
		sensors.addAll(sensorsComposite);
		for(Sensor s : sensorsComposite){
			for(Area b : areas){
				if(s.getArea().equals(b)){
					b.addSensor(s);
				}
			}
		}
		App app = new App(start, stop);
		app.setAreas(areas);
		app.setup();
		app.run();
	}
}
