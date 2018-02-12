package dsl;

import dataextraction.CSVExtractor;
import dataextraction.Extractor;
import dataextraction.JSONExtractor;
import groovy.lang.Binding;
import groovy.lang.Closure;
import launcher.App;
import laws.*;
import structural.Building;
import structural.Sensor;

import java.io.FileNotFoundException;
import java.math.BigDecimal;
import java.util.ArrayList;

public class SensorModel {
	private ArrayList<Building> buildings;
	private ArrayList<Sensor> sensors;
	private ArrayList<DataLaw> laws;
	private Extractor extractor;
	
	private Binding binding;
	
	public SensorModel(Binding binding) {
		this.buildings = new ArrayList<Building>();
		this.sensors = new ArrayList<Sensor>();
		this.laws = new ArrayList<DataLaw>();
	}

	public boolean containsBuilding(Integer id){
		for(Building b : buildings){
			if(b.getId() == id){
				return true;
			}
		}
		return false;
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

	public void generateSensors() throws FileNotFoundException {
		ArrayList<Sensor> list = new ArrayList<>();
		list = extractor.extractSensors();
		this.sensors.addAll(list);
		this.buildings.add(sensors.get(0).getBuilding());
	}

	public void createExtractor(int min, int max,String mode,String path) throws FileNotFoundException{
		switch(mode){
			case "csv":
				extractor = new CSVExtractor(path,min,max);
				break;
			case "json":
				extractor = new JSONExtractor(path,min,max);
				break;
		}
	}

	public void createSensor(String name, Building b, DataLaw law, Integer e) {
		Sensor sensor = new Sensor();
		sensor.setId(this.sensors.size());
		sensor.setSensorDataLaw(law);
		sensor.setBuilding(b);
		sensor.setEchantillonage(e);
		this.sensors.add(sensor);
		for(Building building : buildings){
			if (b.equals(building)){
				b.addSensor(sensor);
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

	public void createMarkovLaw(String name, ArrayList<String> states, ArrayList<ArrayList<BigDecimal>> map, int freq){
		Matrix matrix = new Matrix(map);
		States state = new States(states);
		System.out.println(map);
		MarkovLaw law = new MarkovLaw(name, state, matrix);
		law.setFrequency(freq);
		laws.add(law);
	}

	public void createRandomLaw(String name, ArrayList<Integer> integers, int frequency){
		RandomLaw law = new RandomLaw(name);
		law.setMin(integers.get(0));
		law.setMax(integers.get(1));
		law.setFrequency(frequency);
		laws.add(law);
	}

	public void runApp(Integer step){
		App app = new App();
		app.setBuildings(buildings);
		app.setStep(step);
		app.setup();
		app.run();
	}
}
