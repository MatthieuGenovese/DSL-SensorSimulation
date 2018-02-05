package dsl

import laws.DataLaw
import laws.Function
import laws.MarkovLaw
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
                            ErrorDetection.integerExpected([b,nombre,f,e])
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
    def function(Closure cl){
        def function = new Func()
		function.name(nom)
		def code = cl.rehydrate(function, this,this)
		code.resolveStrategy = Closure.DELEGATE_ONLY
        code()
		System.out.print(function)
    }

	class Func {
		def nom = ""
		def predicate = new Predicates()
		void name(String n) { nom = n}
		void body(Closure body) {
			def code = body.rehydrate(predicate, this, this)
			code.resolveStrategy = Closure.DELEGATE_ONLY
			code()
		}

		String toString(){
			return "nom " + nom + predicate.toString()
		}
	}

	def then(String s){
		def acts = s
	}

	class Predicates{
		def predicates = new ArrayList<String>()
		def acts = new ArrayList<String>()
		void when(String pred){
				then();
				predicates.add(pred)
				//acts.add(act)
		}
		void then(String act){
			acts.add(act)
		}
		String toString(){
			String pred = "";
			String act = "";
			for(int i = 0; i < predicates.size(); i++){
				pred+= predicates.get(i) + ", ";
				act+= acts.get(i) + ", ";
			}
			return " predicats : [ " + pred + " ]" + " action : [ " + act + " ]\n"
		}
	}

	def law(String name) {
		[type: { type ->
            if( type.equalsIgnoreCase("markov")){
                [states: { states ->
                    [transi: { map ->
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
        ((SensorBinding) this.getBinding()).getSensorModel().runApp(steps)
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
