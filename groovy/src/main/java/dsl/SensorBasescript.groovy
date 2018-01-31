package dsl

import laws.DataLaw
import structural.Building

abstract class SensorBasescript extends Script {
	// sensor "name" pin n

	def mode(String s){
		[path: { path ->
            ErrorDetection.filePathExpected(path)
            ((SensorBinding)this.getBinding()).setVariable("mode", s)
            ((SensorBinding) this.getBinding()).getSensorModel().generateSensors(s, path)
		}]
	}
	def sensor(String name) {
		[law: { datalaw ->
			[create: { nombre ->
				[building: { b ->
					[frequency: { f ->
						[echantillonage: { e ->
                            ErrorDetection.integerExpected([b,nombre])
                            if (!((SensorBinding) this.getBinding()).getSensorModel().containsBuilding(b)) {
                                ((SensorBinding) this.getBinding()).getSensorModel().createBuilding(b)
                            }
                            Building building = ((SensorBinding) this.getBinding()).getSensorModel().getBuilding(b)
                            DataLaw law = ((SensorBinding) this.getBinding()).getSensorModel().getLaw(datalaw)
                            for (int i = 0; i < nombre; i++) {
                                ((SensorBinding) this.getBinding()).getSensorModel().createSensor(name, building, law, f, e)
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
            if( type.equalsIgnoreCase("markov")){
                [states: { states ->
                    [transi: { map ->
                        ErrorDetection.integerExpected(type)
                        ErrorDetection.arraylistExpected([states,map])
                        ErrorDetection.checkMarkovImplementation(states, map)
                        ((SensorBinding) this.getBinding()).getSensorModel().createLaw(name, type, states, map)
                    }]
                }]
            }
            else{
                ((SensorBinding) this.getBinding()).getSensorModel().createLaw(name, type)
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
