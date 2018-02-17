package dsl

import laws.CompositeLaw
import laws.DataLaw
import structural.Building
import structural.Sensor

import java.text.SimpleDateFormat

abstract class SensorBasescript extends Script {

	public ErrorDetection erreurHandler

	def mode(String s){
		[path: { path ->
			[min: { min ->
				[max: { max ->
			this.erreurHandler.filePathExpected(path)
			try {
				((SensorBinding) this.getBinding()).setVariable("mode", s)
				((SensorBinding) this.getBinding()).getSensorModel().createExtractor(min,max,s,path);
				((SensorBinding) this.getBinding()).getSensorModel().generateSensors();
			}
			catch(Exception e){
				this.erreurHandler.findAndAddLine(e)
				((SensorBinding) this.getBinding()).setErreurs(true)
			}
				}]
			}]
		}]
	}
	def sensor(String name) {
		[law: { datalaw ->
			[create: { nombre ->
				[area: { b ->
					[echantillonage: { e ->
						[by: { unit ->
							this.erreurHandler.integerExpected([b, nombre, e])
							try {
								if (!((SensorBinding) this.getBinding()).getSensorModel().containsBuilding(b)) {
									((SensorBinding) this.getBinding()).getSensorModel().createBuilding(b)
								}
								Building building = ((SensorBinding) this.getBinding()).getSensorModel().getBuilding(b)
								DataLaw law = ((SensorBinding) this.getBinding()).getSensorModel().getLaw(datalaw)
								for (int i = 0; i < nombre; i++) {
									((SensorBinding) this.getBinding()).getSensorModel().createSensor(name, building, law, e, unit)
								}
							}
							catch (Exception ex) {
								this.erreurHandler.findAndAddLine(ex)
								((SensorBinding) this.getBinding()).setErreurs(true)
							}
						}]
					}]
				}]
			}]
		}]
	}

    def functionLaw(String name, Closure cl){
		try {
			((SensorBinding) this.getBinding()).getSensorModel().createFunction(name, cl)
		}
		catch (Exception e){
			this.erreurHandler.findAndAddLine(e)
			((SensorBinding) this.getBinding()).setErreurs(true)
		}
    }

	def markovLaw(String name) {
			[states: { states ->
				[transi: { map ->
					[frequency: { f ->
						[by: { unit ->
							this.erreurHandler.checkMarkovImplementation(states, map)
							this.erreurHandler.integerExpected(f)
							try {
								((SensorBinding) this.getBinding()).getSensorModel().createMarkovLaw(name, states, map, f, unit)
							}
							catch (Exception e) {
								this.erreurHandler.findAndAddLine(e)
								((SensorBinding) this.getBinding()).setErreurs(true)
							}
						}]
					}]
				}]
			}]
	}

	def randomLaw(String name){
		[interval: { interval ->
			[frequency: { f ->
				[by: { unit ->
					this.erreurHandler.arraylistExpected(interval, 2)
					this.erreurHandler.integerExpected(f)
					try {
						((SensorBinding) this.getBinding()).getSensorModel().createRandomLaw(name, interval, f, unit)
					}
					catch (Exception e) {
						this.erreurHandler.findAndAddLine(e)
						((SensorBinding) this.getBinding()).setErreurs(true)
					}
				}]
			}]
		}]
	}

	def compositeSensor(String name){
		[sensor : { sensor ->
			[function: { func ->
				[create: { nombre ->
					[area: { b ->
						[echantillonage: { e ->
							[by: { unit ->
								this.erreurHandler.integerExpected([b, nombre, e])
								try {
									if (!((SensorBinding) this.getBinding()).getSensorModel().containsBuilding(b)) {
										((SensorBinding) this.getBinding()).getSensorModel().createBuilding(b)
									}
									Building building = ((SensorBinding) this.getBinding()).getSensorModel().getBuilding(b)
									Sensor s = ((SensorBinding) this.getBinding()).getSensorModel().getSensor(sensor)
									CompositeLaw compositeLaw = new CompositeLaw(name, s, func);
//									DataLaw law = ((SensorBinding) this.getBinding()).getSensorModel().getLaw(datalaw)
									for (int i = 0; i < nombre; i++) {
										((SensorBinding) this.getBinding()).getSensorModel().createSensor(name, building, compositeLaw, e, unit)
									}
								}
								catch (Exception ex) {
									ex.printStackTrace()
									this.erreurHandler.findAndAddLine(ex)
									((SensorBinding) this.getBinding()).setErreurs(true)
								}
							}]
						}]
					}]
				}]
			}]
		}]
	}


	def runApp(String start){
		[to: { stop ->
			SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss a");
			Date startDate = formatter.parse(start);
			Date stopDate = formatter.parse(stop);

			if (((SensorBinding) this.getBinding()).isErreurs()) {
				System.out.println(this.erreurHandler.getErreurs())
			} else {
				((SensorBinding) this.getBinding()).getSensorModel().runApp(startDate, stopDate)
			}
		}]
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
