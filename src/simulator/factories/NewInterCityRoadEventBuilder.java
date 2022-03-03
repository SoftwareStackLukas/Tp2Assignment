package simulator.factories;

import org.json.JSONObject;

import simulator.model.Event;
import simulator.model.NewCityRoadEvent;
import simulator.model.NewInterCityRoadEvent;

public class NewInterCityRoadEventBuilder extends NewRoadEventBuilder {

	private static final String TYPE = "new_inter_city_road";
	
	NewInterCityRoadEventBuilder() {
		super(TYPE);
	}

	@Override
	protected NewInterCityRoadEvent createTheInstance(JSONObject data) {
		parseData(data);
		return new NewInterCityRoadEvent(time, id, src, dest, length, co2limit, maxspeed, weather);
	}

}


/*
Structure:
{
	"type" : new_inter_city_road
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
 