package simulator.factories;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONString;

import simulator.misc.Pair;
import simulator.model.Event;
import simulator.model.Weather;
import simulator.model.SetWeatherEvent;


public class SetWeatherEventBuilder extends Builder<Event> {
	private static final String TYPE = "set_cont_class";
	private static final String ROAD = "road";
	private static final String WEATHER = "weather";
	
	private static final String _time = "time";
	private static final String _info = "info";
	
	public SetWeatherEventBuilder() {
		super(SetWeatherEventBuilder.TYPE);
	}

	@Override
	protected Event createTheInstance(JSONObject data) {
		List<Pair<String, Weather>> ws = new ArrayList<>();
		JSONArray ja = data.getJSONArray(SetWeatherEventBuilder._info);
		JSONObject jo; 
		for (int x = ja.length(); x >= 0; x--) {
			jo = ja.getJSONObject(x);
			ws.add(new Pair<String, Weather>(jo.getString(SetWeatherEventBuilder.ROAD), 
					Weather.valueOf(jo.getString(SetWeatherEventBuilder.WEATHER))));
		}
		Collections.reverse(ws);
		Event result = new SetWeatherEvent(data.getInt(SetWeatherEventBuilder._time), ws);
		return result;
	}
}
