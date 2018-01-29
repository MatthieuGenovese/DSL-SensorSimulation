package dsl;

import groovy.lang.Binding;
import groovy.lang.Script;

import java.util.Map;

public class SensorBinding extends Binding {
	// can be useful to return the script in case of syntax trick
	private Script script;
	
	private SensorModel model;
	
	public SensorBinding() {
		super();
	}
	
	@SuppressWarnings("rawtypes")
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
	
	public void setGroovuinoMLModel(SensorModel model) {
		this.model = model;
	}
	
	public Object getVariable(String name) {
		// Easter egg (to show you this trick: seb is now a keyword!)
		if ("seb".equals(name)) {
			// could do something else like: ((App) this.getVariable("app")).action();
			System.out.println("Seb, c'est bien");
			return script;
		}
		return super.getVariable(name);
	}
	
	public void setVariable(String name, Object value) {
		super.setVariable(name, value);
	}
	
	public SensorModel getGroovuinoMLModel() {
		return this.model;
	}
}
