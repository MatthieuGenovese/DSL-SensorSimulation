package dsl

import laws.DataLaw
import structural.Building

abstract class SensorBasescript extends Script {
	// sensor "name" pin n

	def mode(String s){
		[path: { path ->
			if(path instanceof String){
				((SensorBinding)this.getBinding()).setVariable("mode", s)
				((SensorBinding) this.getBinding()).getSensorModel().generateSensors(s, path)
			}
		}]
	}
	def sensor(String name) {
		[law: { datalaw ->
			[create: { nombre ->
				[building: { b ->
					[frequency: { f ->
						[echantillonage: { e ->
							if (b instanceof Integer) {
								if (!((SensorBinding) this.getBinding()).getSensorModel().containsBuilding(b)) {
									((SensorBinding) this.getBinding()).getSensorModel().createBuilding(b)
								}
								Building building = ((SensorBinding) this.getBinding()).getSensorModel().getBuilding(b)
								DataLaw law = ((SensorBinding) this.getBinding()).getSensorModel().getLaw(datalaw)
								if (nombre instanceof Integer) {
									for (int i = 0; i < nombre; i++) {
										((SensorBinding) this.getBinding()).getSensorModel().createSensor(name, building, law, f, e)
									}
								}
							}
						}]
					}]
				}]
			}]
		}]
	}
	// export name

	def law(String name) {
		[type: { type ->
			if(type instanceof String){
				if( type.equalsIgnoreCase("markov")){
					[states: { states ->
						if (states instanceof ArrayList) {
							[transi: { map ->
								if (map instanceof ArrayList) {
									ErrorDetection.checkMarkovImplementation(states, map)
									((SensorBinding) this.getBinding()).getSensorModel().createLaw(name, type, states, map)
								}
							}]
						}
					}]
				}
				else{
					((SensorBinding) this.getBinding()).getSensorModel().createLaw(name, type)
				}
			}
		}]
	}

	/*def state(String name) {
		List<Action> actions = new ArrayList<Action>()
		((GroovuinoMLBinding) this.getBinding()).getGroovuinoMLModel().createState(name, actions)
		// recursive closure to allow multiple and statements
		def closure
		closure = { actuator ->
			[becomes: { signal ->
				Action action = new Action()
				action.setActuator(actuator instanceof String ? (Actuator)((GroovuinoMLBinding)this.getBinding()).getVariable(actuator) : (Actuator)actuator)
				action.setValue(signal instanceof String ? (SIGNAL)((GroovuinoMLBinding)this.getBinding()).getVariable(signal) : (SIGNAL)signal)
				actions.add(action)
				[and: closure]
			}]
		}

		[means: closure]
	}*/

	def runApp(Integer steps){
		if(steps instanceof Integer) {
			((SensorBinding) this.getBinding()).getSensorModel().runApp(steps)
		}
	}

	/*def export(String name) {
		println(((SensorBinding) this.getBinding()).getSensorModel().generateCode(name).toString())
	}*/
	
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
