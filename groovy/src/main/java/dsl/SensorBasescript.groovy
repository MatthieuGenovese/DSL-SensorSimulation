package dsl

import structural.Building

abstract class SensorBasescript extends Script {
	// sensor "name" pin n
	def sensor(Integer b) {
		[law: { l ->
			if(b instanceof  Integer){
				if(!((SensorBinding)this.getBinding()).getSensorModel().containsBuilding(b)){
					((SensorBinding)this.getBinding()).getSensorModel().createBuilding(b)
				}
				Building building = ((SensorBinding)this.getBinding()).getSensorModel().getBuilding(b)
				((SensorBinding)this.getBinding()).getSensorModel().createSensor(l, building)
			}
		}]
	}
	
	// actuator "name" pin n
	/*def actuator(String name) {
		[pin: { n -> ((SensorBinding)this.getBinding()).getSensorModel().createActuator(name, n) }]
	}

	// state "name" means actuator becomes signal [and actuator becomes signal]*n
	def state(String name) {
		List<Action> actions = new ArrayList<Action>()
		((SensorBinding) this.getBinding()).getSensorModel().createState(name, actions)
		// recursive closure to allow multiple and statements
		def closure
		closure = { actuator -> 
			[becomes: { signal ->
				Action action = new Action()
				action.setActuator(actuator instanceof String ? (Actuator)((SensorBinding)this.getBinding()).getVariable(actuator) : (Actuator)actuator)
				action.setValue(signal instanceof String ? (SIGNAL)((SensorBinding)this.getBinding()).getVariable(signal) : (SIGNAL)signal)
				actions.add(action)
				[and: closure]
			}]
		}

		[means: closure]
	}
	
	// initial state
	def initial(state) {
		((SensorBinding) this.getBinding()).getSensorModel().setInitialState(state instanceof String ? (State)((SensorBinding)this.getBinding()).getVariable(state) : (State)state)
	}

	def error(Integer code){
		((SensorBinding) this.getBinding()).getSensorModel().setLedError()
		List<Action> actions = new ArrayList<Action>()
		List<Sensor> sensors = new ArrayList<Sensor>()
		List<SIGNAL> signals = new ArrayList<SIGNAL>()
		Action action = new Action();
		action.setActuator( (Actuator)((SensorBinding)this.getBinding()).getVariable("errorLed"))
		action.setValue(SIGNAL.HIGH)
		actions.add(action);

		((SensorBinding) this.getBinding()).getSensorModel().createError(code, actions, sensors, signals)
		//((SensorBinding) this.getBinding()).getSensorModel().createError(code, actions)
		def closure
		closure = { sensor ->
			[becomes: { signal ->
						Error err = (Error) ((SensorBinding) this.getBinding()).getSensorModel().getError(code);
						((SensorBinding) this.getBinding()).getSensorModel().removeError(err)
						if(sensor instanceof  String){
							err.addSensor(((SensorBinding) this.getBinding()).getVariable(sensor))
						}
						else{
							err.addSensor((Sensor) sensor)
						}
						if(signal instanceof  String){
							err.addValue(((SensorBinding) this.getBinding()).getVariable(signal))
						}
						else{
							err.addValue((SIGNAL) signal)
						}
						((SensorBinding) this.getBinding()).getSensorModel().addError(err)
						//sensor instanceof String ? sensors.add((Sensor) ((SensorBinding) this.getBinding()).getVariable(sensor)) : sensors.add((Sensor) sensor)
						//signal instanceof String ? signals.add((SIGNAL) ((SensorBinding) this.getBinding()).getVariable(signal)) : signals.add((SIGNAL) signal)
				[and: closure]
			}]
		}
		[when: closure]
		//System.out.println(sensors.size() + " " + signals.size());
		//((SensorBinding) this.getBinding()).getSensorModel().createError(code, actions, sensors, signals)

	}
	
	// from state1 to state2 when sensor becomes signal
	def from(state1) {
		[to: { state2 -> 
			[when: { sensor ->
				[becomes: { signal -> 
					((SensorBinding) this.getBinding()).getSensorModel().createTransition(
						state1 instanceof String ? (State)((SensorBinding)this.getBinding()).getVariable(state1) : (State)state1,
						state2 instanceof String ? (State)((SensorBinding)this.getBinding()).getVariable(state2) : (State)state2,
						sensor instanceof String ? (Sensor)((SensorBinding)this.getBinding()).getVariable(sensor) : (Sensor)sensor,
						signal instanceof String ? (SIGNAL)((SensorBinding)this.getBinding()).getVariable(signal) : (SIGNAL)signal)
				}]
			}]
		}]
	}*/
	
	// export name

	def runApp(String name){
		((SensorBinding) this.getBinding()).getSensorModel().runApp()
	}

	def export(String name) {
		println(((SensorBinding) this.getBinding()).getSensorModel().generateCode(name).toString())
	}
	
	// disable run method while running
	int count = 0
	abstract void scriptBody()
	def run() {
		if(count == 0) {
			count++
			scriptBody()
		} else {
			println "Run method is disabled"
		}
	}
}
