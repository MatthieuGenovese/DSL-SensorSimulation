package dsl

import laws.DataLaw
import laws.Function
import laws.MarkovLaw
import structural.Building

abstract class SensorBasescript extends Script {

	public ErrorDetection erreurHandler
	// sensor "name" pin n

	def mode(String s){
		[path: { path ->
			this.erreurHandler.filePathExpected(path)
			try {
				((SensorBinding) this.getBinding()).setVariable("mode", s)
				((SensorBinding) this.getBinding()).getSensorModel().generateSensors(s, path)
			}
			catch(Exception e){
				this.erreurHandler.findAndAddLine(e)
				((SensorBinding) this.getBinding()).setErreurs(true)
			}
		}]
	}
	def sensor(String name) {
		[law: { datalaw ->
			[create: { nombre ->
				[building: { b ->
					[frequency: { f ->
						[echantillonage: { e ->
                            this.erreurHandler.integerExpected([b,nombre,f,e])
							try {
								if (!((SensorBinding) this.getBinding()).getSensorModel().containsBuilding(b)) {
									((SensorBinding) this.getBinding()).getSensorModel().createBuilding(b)
								}
								Building building = ((SensorBinding) this.getBinding()).getSensorModel().getBuilding(b)
								DataLaw law = ((SensorBinding) this.getBinding()).getSensorModel().getLaw(datalaw)
								for (int i = 0; i < nombre; i++) {
									((SensorBinding) this.getBinding()).getSensorModel().createSensor(name, building, law, f, e)
								}
							}
							catch(Exception ex){
								this.erreurHandler.findAndAddLine(ex)
								((SensorBinding) this.getBinding()).setErreurs(true)
							}
						}]
					}]
				}]
			}]
		}]
	}
    def function(Closure cl){
        def function = new Function()
		def code = cl.rehydrate(function, this,this)
		code.resolveStrategy = Closure.DELEGATE_ONLY
        code()
		System.out.print(function)
    }

	def law(String name) {
		[type: { type ->
			if (type.equalsIgnoreCase("markov")) {
				[states: { states ->
					[transi: { map ->
						this.erreurHandler.checkMarkovImplementation(states, map)
						try {
							((SensorBinding) this.getBinding()).getSensorModel().createLaw(name, type, states, map)
						}
						catch (Exception e){
							this.erreurHandler.findAndAddLine(e)
							((SensorBinding) this.getBinding()).setErreurs(true)
						}
					}]
				}]
			} else {
				((SensorBinding) this.getBinding()).getSensorModel().createLaw(name, type)
			}
		}]
	}


	//todo : MARCHE PAS ENCORE BLBLBLBLBL
	/*def law(String name) {
		//def markov = {
		[markov: { states ->
				[transi: { map ->
					ErrorDetection.arraylistExpected([states, map])
					ErrorDetection.checkMarkovImplementation(states, map)
					((SensorBinding) this.getBinding()).getSensorModel().createLaw(name, "markov", states, map)
				}]
		}]
		[random: { interval ->
				ErrorDetection.arraylistExpected(interval, 2)
				((SensorBinding) this.getBinding()).getSensorModel().createLaw(name, interval)
		}]
	}*/

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
		this.erreurHandler.integerExpected(steps)
		if(((SensorBinding) this.getBinding()).isErreurs()){
			System.out.println(this.erreurHandler.getErreurs())
		}
		else {
			((SensorBinding) this.getBinding()).getSensorModel().runApp(steps)
		}
	}

	// disable run method while running
	int count = 0
	abstract void scriptBody()
	def run() {
		if(count == 0) {
			count++
			this.erreurHandler = new ErrorDetection(((SensorBinding) this.getBinding()).getFileName())
			try {
				scriptBody()
			}
			catch (Exception e){
				this.erreurHandler.throwIncorrectWord(e)
				System.out.println(this.erreurHandler.getErreurs())
			}
		} else {
			println "Run method is disabled"
		}
	}
}
