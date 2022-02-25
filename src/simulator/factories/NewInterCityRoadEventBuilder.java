package simulator.factories;

import org.json.JSONObject;

import simulator.model.Event;
import simulator.model.NewInterCityRoadEvent;

public class NewInterCityRoadEventBuilder extends Builder<Event> {

	private static final String TYPE = "new_inter_city_road";
	
	NewInterCityRoadEventBuilder() {
		super(TYPE);
	}

	@Override
	protected NewInterCityRoadEvent createTheInstance(JSONObject data) {
		// TODO Auto-generated method stub
		return null;
	}

}
