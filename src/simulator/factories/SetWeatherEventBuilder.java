package simulator.factories;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import simulator.misc.Pair;
import simulator.model.Event;
import simulator.model.Weather;
import simulator.model.SetWeatherEvent;


public class SetWeatherEventBuilder extends Builder<Event> {
	private static final String TYPE = "set_weather";
	
	private static final String _time = "time";
	private static final String _info = "info";
	private static final String _road = "road";
	private static final String _weather = "weather";
	
	public SetWeatherEventBuilder() {
		super(SetWeatherEventBuilder.TYPE);
	}

	@Override
	protected Event createTheInstance(JSONObject data) {
		List<Pair<String, Weather>> ws = new ArrayList<>();
		JSONArray ja = data.getJSONArray(SetWeatherEventBuilder._info);
		JSONObject jo; 
		for (int x = ja.length() - 1; x >= 0; x--) {
			jo = ja.getJSONObject(x);
			ws.add(new Pair<String, Weather>(jo.getString(SetWeatherEventBuilder._road), 
					Weather.valueOf(jo.getString(SetWeatherEventBuilder._weather))));
		} 
		Collections.reverse(ws);
		return new SetWeatherEvent(data.getInt(SetWeatherEventBuilder._time), ws);
	}
}
