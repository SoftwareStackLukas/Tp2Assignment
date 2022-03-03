package simulator.factories;

import org.json.JSONObject;

import simulator.model.Event;
import simulator.model.NewRodEvent;
import simulator.model.Weather;

abstract class NewRoadEventBuilder extends Builder<Event> {
	int time, length, co2limit, maxspeed;
	String id, src, dest, weatherName;
	Weather weather;

	NewRoadEventBuilder(String type) {
		super(type);
	}
	
	protected void parseData(JSONObject data) {
		time = data.getInt("time");
		id = data.getString("id");
		src = data.getString("src");
		dest = data.getString("dest");
		length = data.getInt("length");
		co2limit = data.getInt("co2limit");
		maxspeed = data.getInt("maxspeed");
		weatherName = data.getString("weather");
		weather = Weather.valueOf(weatherName);
	}
}
