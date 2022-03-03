package simulator.factories;

import org.json.JSONObject;

import simulator.model.Event;
import simulator.model.NewCityRoadEvent;
import simulator.model.Weather;

public class NewCityRoadEventBuilder extends NewRoadEventBuilder {

	private static final String TYPE = "new_city_road";
	
	NewCityRoadEventBuilder() {
		super(TYPE);
	}

	@Override
	protected NewCityRoadEvent createTheInstance(JSONObject data) {
		parseData(data);
		return new NewCityRoadEvent(time, id, src, dest, length, co2limit, maxspeed, weather);
	}

}


/*
{
	"type" : "new_city_road",
	"data" : {
		"time" : 1,
		"id" : "r1",
		"src" : "j1",
		"dest" : "j2",
		"length" : 10000,
		"co2limit" : 500,
		"maxspeed" : 120,
		"weather" : "SUNNY"
	}
}
*/