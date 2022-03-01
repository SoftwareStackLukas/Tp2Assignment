package simulator.factories;

import org.json.JSONObject;

import simulator.model.Event;
import simulator.model.NewCityRoadEvent;
import simulator.model.Weather;

public class NewCityRoadEventBuilder extends RoadEventBuilder {

	private static final String TYPE = "new_city_road";
	
	NewCityRoadEventBuilder() {
		super(TYPE);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected NewCityRoadEvent createTheInstance(JSONObject data) {
		int time = data.getInt("time");
		String id = data.getString("id");
		String src = data.getString("src");
		String dest = data.getString("dest");
		int length = data.getInt("length");
		int co2limit = data.getInt("co2limit");
		int maxspeed = data.getInt("maxspeed");
		String weatherName = data.getString("weather");
		Weather weather = Weather.valueOf(weatherName);
		
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