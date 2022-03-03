package simulator.factories;

import org.json.JSONObject;

import simulator.model.Event;
import simulator.model.NewCityRoadEvent;
import simulator.model.NewInterCityRoadEvent;

public class NewInterCityRoadEventBuilder extends NewRoadEventBuilder {

	private static final String TYPE = "new_inter_city_road";
	
	public NewInterCityRoadEventBuilder() {
		super(NewInterCityRoadEventBuilder.TYPE);
	}

	@Override
	NewInterCityRoadEvent createCorrespondingEvent() {
		try {
			return new NewInterCityRoadEvent(time, id, srcJunc, destJunc, length, co2Limit, maxSpeed, weather);
		} catch (Exception ex) {
			throw new IllegalArgumentException("Something went wronge in" + NewInterCityRoadEventBuilder.TYPE);
		}
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
 