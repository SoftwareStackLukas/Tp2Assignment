package simulator.factories;

import org.json.JSONObject;

import simulator.model.Event;

public class SetWeatherEventBuilder extends Builder<Event> {

	SetWeatherEventBuilder(String type) {
		super(type);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected Event createTheInstance(JSONObject data) {
		Event result = null;
		return result;
	}
	
}
