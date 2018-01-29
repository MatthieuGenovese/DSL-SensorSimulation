package dsl;

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

	/*public void setLedError(){
		Actuator errorLed = new Actuator();
		errorLed.setName("errorLed");
		errorLed.setPin(12);
		this.bricks.add(errorLed);
		this.binding.setVariable("errorLed", errorLed);
	}

	public void createError(Integer code, List<Action> actions, List<Sensor> sensor, List<SIGNAL> signal){
		Error err = new Error();
		err.setCode(code);
		err.setActions(actions);
		err.setSensors(sensor);
		err.setValues(signal);
		this.errors.add(err);

	}
	
	public void createActuator(String name, Integer pinNumber) {
		Actuator actuator = new Actuator();
		actuator.setName(name);
		actuator.setPin(pinNumber);
		this.bricks.add(actuator);
		this.binding.setVariable(name, actuator);
	}
	
	public void createState(String name, List<Action> actions) {
		State state = new State();
		state.setName(name);
		state.setActions(actions);
		this.states.add(state);
		this.binding.setVariable(name, state);
	}
	
	public void createTransition(State from, State to, Sensor sensor, SIGNAL value) {
		Transition transition = new Transition();
		transition.setNext(to);
		transition.setSensor(sensor);
		transition.setValue(value);
		from.setTransition(transition);
	}
	
	public void setInitialState(State state) {
		this.initialState = state;
	}

	public Error getError(Integer code){
		for(Error e : this.errors){
			if(e.getCode() == code){
				return e;
			}
		}
		return null;
	}
	public void addError(Error e){
		this.errors.add(e);
	}

	public void removeError(Error e){
		this.errors.remove(e);
	}
	@SuppressWarnings("rawtypes")
	public Object generateCode(String appName) {
		launcher.App app = new launcher.App();
		app.setName(appName);
		app.setBricks(this.bricks);
		app.setStates(this.states);
		app.setInitial(this.initialState);
		app.setErrors(this.errors);
		Visitor codeGenerator = new ToWiring();
		app.accept(codeGenerator);
		
		return codeGenerator.getResult();
	}*/
}
