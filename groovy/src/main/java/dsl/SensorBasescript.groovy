package dsl

import laws.CompositeLaw
import laws.DataLaw
import structural.Building
import structural.Sensor

import java.text.SimpleDateFormat

abstract class SensorBasescript extends Script {

	public ErrorDetection erreurHandler

	def sensor(String name) {
		[law: { datalaw ->
			[create: { nombre ->
				[area: { b ->
					[echantillonage: { e ->
						[by: { unit ->
							try{
								this.erreurHandler.goodSensorExpected(name, ((SensorBinding) this.getBinding()).getSensorModel().getSensors(), [b,nombre,e],unit,((SensorBinding)this.getBinding()).getSensorModel().getLaw(datalaw))
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
							}
						}]
					}]
				}]
			}]
		}]
	}

	def extractionSensor(String name){
		[mode: { m ->
			[path: { p ->
				[sensor: { s ->
					[create: { nombre ->
						[area: { b ->
							[timeunit: { t ->
								try {
									this.erreurHandler.goodExtractionSensor(name, ((SensorBinding) this.getBinding()).getSensorModel().getSensors(), [b,nombre],m,p,t,s)
									((SensorBinding) this.getBinding()).getSensorModel().createExtractionLaw(name, m, p, s, t)
									if (!((SensorBinding) this.getBinding()).getSensorModel().containsBuilding(b)) {
										((SensorBinding) this.getBinding()).getSensorModel().createBuilding(b)
									}
									Building building = ((SensorBinding) this.getBinding()).getSensorModel().getBuilding(b)
									DataLaw law = ((SensorBinding) this.getBinding()).getSensorModel().getLaw(name)
									for (int i = 0; i < nombre; i++) {
										((SensorBinding) this.getBinding()).getSensorModel().createExtractionSensor(name, building, law)
									}
								}
								catch (Exception ex) {
									this.erreurHandler.findAndAddLine(ex)
								}
							}]
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
		}
    }

	def markovLaw(String name) {
			[states: { states ->
				[transi: { map ->
					[frequency: { f ->
						[by: { unit ->
							try {
								this.erreurHandler.checkMarkovImplementation(states, map, f, unit)
								((SensorBinding) this.getBinding()).getSensorModel().createMarkovLaw(name, states, map, f, unit)
							}
							catch (Exception e) {
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
					try {
						this.erreurHandler.checkRandomImplementation(interval, 2, f, unit)
						((SensorBinding) this.getBinding()).getSensorModel().createRandomLaw(name, interval, f, unit)
					}
					catch (Exception e) {
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
								try {
									this.erreurHandler.goodCompositeSensor(name, [b,nombre,e],unit,((SensorBinding) this.getBinding()).getSensorModel().getSensors(), sensor)
									if (!((SensorBinding) this.getBinding()).getSensorModel().containsBuilding(b)) {
										((SensorBinding) this.getBinding()).getSensorModel().createBuilding(b)
									}
									Building building = ((SensorBinding) this.getBinding()).getSensorModel().getBuilding(b)
									Sensor s = ((SensorBinding) this.getBinding()).getSensorModel().getSensor(sensor)
									CompositeLaw compositeLaw = new CompositeLaw(name, s, func)
									for (int i = 0; i < nombre; i++) {
										((SensorBinding) this.getBinding()).getSensorModel().createSensor(name, building, compositeLaw, e, unit)
									}
								}
								catch (Exception ex) {
									ex.printStackTrace()
									this.erreurHandler.findAndAddLine(ex)
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
			try {
				Date startDate = formatter.parse(start);
				Date stopDate = formatter.parse(stop);
				if (!this.erreurHandler.getErreurs().equalsIgnoreCase("-----ERREURS DE COMPILATION-----\n\n")) {
					System.out.println(this.erreurHandler.getErreurs())
				} else {
					((SensorBinding) this.getBinding()).getSensorModel().runApp(startDate, stopDate)
				}
			}
			catch (Exception e){
				this.erreurHandler.findAndAddLine(e)
				((SensorBinding) this.getBinding()).setErreurs(true)
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
			((SensorBinding) this.getBinding()).setErreurHandler(erreurHandler)
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
