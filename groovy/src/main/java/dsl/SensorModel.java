package dsl;

import dataextraction.CSVExtractor;
import dataextraction.Extractor;
import dataextraction.JSONExtractor;
import groovy.lang.Binding;
import groovy.lang.Closure;
import launcher.App;
import laws.*;
import structural.Building;
import structural.ExtractionSensor;
import structural.Sensor;

import java.io.FileNotFoundException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;

public class SensorModel {
	private ArrayList<Building> buildings;
	private ArrayList<Sensor> sensors;
	private ArrayList<DataLaw> laws;
	private ArrayList<Sensor> sensorsComposite;
	private Extractor extractor;
	private Binding binding;
	
	public SensorModel(Binding binding) {
		sensorsComposite = new ArrayList<>();
		this.buildings = new ArrayList<Building>();
		this.sensors = new ArrayList<Sensor>();
		this.laws = new ArrayList<DataLaw>();
	}

	public ArrayList<Sensor> getSensors() {
		return sensors;
	}

	public boolean containsBuilding(Integer id){
		for(Building b : buildings){
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

	public Building getBuilding(int b){
		for(Building building : buildings){
			if(building.getId() == b){
				return building;
			}
		}
		return null;
	}


	public void createExtractionLaw(String name, String mode, String path, String sensor, String timeunit) throws FileNotFoundException {
		ExtractionLaw law = new ExtractionLaw(name, mode, path, sensor, timeunit);
		laws.add(law);
	}

	public void createSensor(String name, Building b, DataLaw law, Integer e, String unit) {
		Sensor sensor = new Sensor(name, e, unit);
		sensor.setId(this.sensors.size());
		sensor.setSensorDataLaw(law);
		sensor.setBuilding(b);
		if(sensor.getSensorDataLaw() instanceof CompositeLaw){
			this.sensorsComposite.add(sensor);
		}
		else {
			if (sensor.getSensorDataLaw() instanceof FunctionLaw){
				((FunctionLaw) law).setNbSensors(((FunctionLaw) law).getNbSensors()+1);
			}
			this.sensors.add(sensor);
			for(Building building : buildings){
				if (b.equals(building)){
					b.addSensor(sensor);
					break;
				}
			}
		}

	}

	public void createExtractionSensor(String name, Building b, DataLaw law) {
		Sensor s = new ExtractionSensor(name);
		s.setId(this.sensors.size());
		s.setSensorDataLaw(law);
		s.setBuilding(b);
		this.sensors.add(s);
		for(Building building : buildings){
			if (b.equals(building)){
				b.addSensor(s);
				break;
			}
		}
	}

	public void createBuilding(Integer id){
		Building b = new Building(id);
		buildings.add(b);
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
			for(Building b : buildings){
				if(s.getBuilding().equals(b)){
					b.addSensor(s);
				}
			}
		}
		App app = new App(start, stop);
		app.setBuildings(buildings);
		app.setup();
		app.run();
	}
}
