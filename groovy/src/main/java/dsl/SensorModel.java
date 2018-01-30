package dsl;

import dataextraction.CSVExtractor;
import dataextraction.JSONExtractor;
import groovy.lang.Binding;
import launcher.App;
import laws.DataLaw;
import laws.MarkovLaw;
import laws.RandomLaw;
import structural.Building;
import structural.NombreSensor;
import structural.Sensor;
import structural.TempsSensor;

import java.util.ArrayList;
import java.util.List;

public class SensorModel {
	private ArrayList<Building> buildings;
	private ArrayList<Sensor> sensors;
	
	private Binding binding;
	
	public SensorModel(Binding binding) {
		this.buildings = new ArrayList<Building>();
		this.sensors = new ArrayList<Sensor>();
	}

	public boolean containsBuilding(Integer id){
		for(Building b : buildings){
			if(b.getId() == id){
				return true;
			}
		}
		return false;
	}

	public Building getBuilding(int b){
		for(Building building : buildings){
			if(building.getId() == b){
				return building;
			}
		}
		return null;
	}

	public void generateSensors(String mode, String path){
		ArrayList<Sensor> list = new ArrayList<>();
		switch(mode){
			case "csv":
				CSVExtractor extractorCsv = new CSVExtractor();
				list = extractorCsv.extractSensors(path);
				break;
			case "json":
				JSONExtractor extractorJson = new JSONExtractor();
				list = extractorJson.extractSensors(path);
		}
		this.sensors.addAll(list);
	}
	
	public void createSensor(String type, Building b) {
		DataLaw dataLaw;
		Sensor sensor;
		switch (type){
			case "temps":
				dataLaw = new MarkovLaw();
				sensor = new TempsSensor();
				break;
			case "nombre":
				dataLaw = new RandomLaw();
				sensor = new NombreSensor();
				break;
			default:
				sensor = new NombreSensor();
				dataLaw = new RandomLaw();
		}
		sensor.setId(this.sensors.size());
		sensor.setSensorDataLaw(dataLaw);
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

	public void runApp(Integer step){
		App app = new App();
		app.setBuildings(buildings);
		app.setStep(step);
		app.run();
	}
}
