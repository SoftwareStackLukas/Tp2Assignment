package simulator.factories;

import org.json.JSONObject;

import simulator.model.Event;
import simulator.model.Weather;

public abstract class NewRoadEventBuilder extends Builder<Event> {

	private static final String _time  = "time";
	private static final String _id = "id";
	private static final String _src = "src";
	private static final String _dest = "dest";
	private static final String _length = "length";
	private static final String _co2limit = "co2limit";
	private static final String _maxspeed = "maxspeed";
	private static final String _weather = "weather";
	
	int time, length, co2Limit, maxSpeed;
	String id, srcJunc, destJunc;
	Weather weather;

	NewRoadEventBuilder(String type) {
		super(type);
	}
	abstract Event createCorrespondingEvent();
	
	@Override
	protected Event createTheInstance(JSONObject data) {
		this.time = data.getInt(NewRoadEventBuilder._time);
		this.length = data.getInt(NewRoadEventBuilder._length);
		this.co2Limit = data.getInt(NewRoadEventBuilder._co2limit);
		this.maxSpeed = data.getInt(NewRoadEventBuilder._maxspeed);
		this.id = data.getString(NewRoadEventBuilder._id);
		this.srcJunc = data.getString(NewRoadEventBuilder._src);
		this.destJunc = data.getString(NewRoadEventBuilder._dest);
		this.weather = Weather.valueOf(data.getString(NewRoadEventBuilder._weather));
		
		Event e = createCorrespondingEvent();
		return e;
	}
}
