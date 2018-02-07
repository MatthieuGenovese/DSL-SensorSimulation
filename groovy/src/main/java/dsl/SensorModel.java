package dsl;

import dataextraction.CSVExtractor;
import dataextraction.Extractor;
import dataextraction.JSONExtractor;
import groovy.lang.Binding;
import launcher.App;
import laws.DataLaw;
import laws.MarkovLaw;
import laws.RandomLaw;
import structural.Building;
import laws.Matrix;
import structural.Sensor;
import laws.States;

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

	public void createSensor(String name, Building b, DataLaw law, Integer f, Integer e) {
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
		sensor.setEchantillonage(e);
		sensor.getSensorDataLaw().setFrequency(f);
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

	public void createLaw(String name, String type, ArrayList<String> states, ArrayList<ArrayList<BigDecimal>> map){
//			if(states.isEmpty()){
//				states = "beau, nuageux, orageux";
//			}
		Matrix matrix = new Matrix(map);
		States state = new States(states);
		System.out.println(map);
		MarkovLaw law = new MarkovLaw(name, state, matrix);
		laws.add(law);
	}

	public void createLaw(String name, ArrayList<Integer> integers){
		RandomLaw law = new RandomLaw(name);
		law.setMin(integers.get(0));
		law.setMax(integers.get(1));
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
