package dsl;

import groovy.lang.Binding;
import groovy.lang.Script;
import laws.CompositeLaw;

import java.util.Map;

public class SensorBinding extends Binding {
	// can be useful to return the script in case of syntax trick
	private Script script;
	private String fileName;
	private ErrorDetection erreurHandler;

	public ErrorDetection getErreurHandler() {
		return erreurHandler;
	}

	public void setErreurHandler(ErrorDetection erreurHandler) {
		this.erreurHandler = erreurHandler;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	private SensorModel model;

	public boolean isErreurs() {
		return erreurs;
	}

	public void setErreurs(boolean erreurs) {
		this.erreurs = erreurs;
	}

	private boolean erreurs;
	
	public SensorBinding() {
		super();
		erreurs = false;
	}

	public SensorBinding(Map variables) {
		super(variables);
	}
	
	public SensorBinding(Script script) {
		super();
		this.script = script;
	}
	
	public void setScript(Script script) {
		this.script = script;
	}
	
	public void setSensorModel(SensorModel model) {
		this.model = model;
	}
	
	public Object getVariable(String name) {
		if("s".equals(name)){
			return "s";
		}
		if("min".equals(name)){
			return "min";
		}
		if("h".equals(name)){
			return "h";
		}
		if("d".equals(name)){
			return "d";
		}
		if("ms".equals(name)){
			return "ms";
		}
		if(model.getSensor(name) != null && (!(model.getSensor(name).getSensorDataLaw() instanceof CompositeLaw))){
			Manager manager = new Manager(name, model.getCompositesSensors(),model.getSensors(), erreurHandler);
			return manager;
		}
		if(model.getCompositeSensor(name) != null){
			Manager manager = new Manager(name, model.getCompositesSensors(),model.getSensors(), erreurHandler);
			return manager;
		}
		//else{
		//	return (String) name;
		//}

		return super.getVariable(name);
	}
	
	public void setVariable(String name, Object value) {
		super.setVariable(name, value);
	}
	
	public SensorModel getSensorModel() {
		return this.model;
	}
}
