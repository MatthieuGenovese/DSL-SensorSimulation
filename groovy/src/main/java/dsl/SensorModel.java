package dsl;

import dataextraction.CSVExtractor;
import dataextraction.JSONExtractor;
import groovy.lang.Binding;
import launcher.App;
import laws.DataLaw;
import laws.MarkovLaw;
import laws.RandomLaw;
import structural.Building;
import structural.Sensor;

import java.io.FileNotFoundException;
import java.util.ArrayList;

public class SensorModel {
	private ArrayList<Building> buildings;
	private ArrayList<Sensor> sensors;
	private ArrayList<DataLaw> laws;
	
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

	public void generateSensors(String mode, String path) throws FileNotFoundException {
		ArrayList<Sensor> list = new ArrayList<>();
		switch(mode){
			case "csv":
				CSVExtractor extractorCsv = new CSVExtractor(path);
				list = extractorCsv.extractSensors();
				break;
			case "json":
				JSONExtractor extractorJson = new JSONExtractor();
				list = extractorJson.extractSensors(path);
		}
		this.sensors.addAll(list);
		this.buildings.add(sensors.get(0).getBuilding());
	}

	public void createSensor(String name, Building b, DataLaw law) {
		Sensor sensor = new Sensor();
//		switch (type){
//			case "temps":
//				dataLaw = new MarkovLaw();
//				sensor = new TempsSensor();
//				break;
//			case "nombre":
//				dataLaw = new RandomLaw();
//				sensor = new NombreSensor();
//				break;
//			default:
//				sensor = new NombreSensor();
//				dataLaw = new RandomLaw();
//		}
		sensor.setId(this.sensors.size());
		sensor.setSensorDataLaw(law);
		sensor.setBuilding(b);
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

	public void createLaw(String name, String type){
		if(type == "markov"){
			MarkovLaw law = new MarkovLaw(name);
			laws.add(law);
		}
		else if(type == "random"){
			RandomLaw law = new RandomLaw(name);
			laws.add(law);
		}
	}

	public void runApp(Integer step){
		App app = new App();
		app.setBuildings(buildings);
		app.setStep(step);
		app.run();
	}
}
