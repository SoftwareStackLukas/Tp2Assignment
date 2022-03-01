package simulator.factories;

import org.json.JSONObject;

import simulator.model.Event;

public class NewJunctionEvent extends Builder<Event> {

	public NewJunctionEvent(String type) {
		super(type);
		// TODO Auto-generated constructor stub
	}
	
	@Override
	protected Event createTheInstance(JSONObject data) {
		Event result = null;
		return result;
	}
}
