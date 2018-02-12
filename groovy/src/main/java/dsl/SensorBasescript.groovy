package dsl

import laws.DataLaw
import laws.FunctionLaw
import structural.Building

abstract class SensorBasescript extends Script {

	public ErrorDetection erreurHandler
	// sensor "name" pin n

	def mode(String s){
		[path: { path ->
			[min: { min ->
				[max: { max ->
			//this.erreurHandler.filePathExpected(path)
			try {
				((SensorBinding) this.getBinding()).setVariable("mode", s)
				((SensorBinding) this.getBinding()).getSensorModel().createExtractor(min,max,s,path);
				((SensorBinding) this.getBinding()).getSensorModel().generateSensors();
			}
			catch(Exception e){
				this.erreurHandler.findAndAddLine(e)
				e.printStackTrace()
				((SensorBinding) this.getBinding()).setErreurs(true)
			}
				}]
			}]
		}]
	}
	def sensor(String name) {
	[law: { datalaw ->
		[create: { nombre ->
			[building: { b ->
				[echantillonage: { e ->
					this.erreurHandler.integerExpected([b,nombre,e])
					try {
						if (!((SensorBinding) this.getBinding()).getSensorModel().containsBuilding(b)) {
							((SensorBinding) this.getBinding()).getSensorModel().createBuilding(b)
						}
						Building building = ((SensorBinding) this.getBinding()).getSensorModel().getBuilding(b)
						DataLaw law = ((SensorBinding) this.getBinding()).getSensorModel().getLaw(datalaw)
						for (int i = 0; i < nombre; i++) {
							((SensorBinding) this.getBinding()).getSensorModel().createSensor(name, building, law, e)
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
	}

    def function(String name, Closure cl){
		try {
			((SensorBinding) this.getBinding()).getSensorModel().createFunction(name, cl)
		}
		catch (Exception e){
			this.erreurHandler.findAndAddLine(e)
			((SensorBinding) this.getBinding()).setErreurs(true)
		}
    }

	def law(String name) {
		[type: { type ->
			if (type.equalsIgnoreCase("markov")) {
				[states: { states ->
					[transi: { map ->
						[frequency: { f ->
							this.erreurHandler.checkMarkovImplementation(states, map)
							try {
								((SensorBinding) this.getBinding()).getSensorModel().createMarkovLaw(name, type, states, map, f)
							}
							catch (Exception e) {
								this.erreurHandler.findAndAddLine(e)
								((SensorBinding) this.getBinding()).setErreurs(true)
							}
						}]
					}]
				}]
			} else {
				((SensorBinding) this.getBinding()).getSensorModel().createMarkovLaw(name, type)
			}
		}]
	}




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
				e.printStackTrace();
			}
		} else {
			println "Run method is disabled"
		}
	}
}
