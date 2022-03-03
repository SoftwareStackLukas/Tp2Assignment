package simulator.factories;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import simulator.misc.Pair;
import simulator.model.Event;
import simulator.model.SetContClassEvent;

public class SetContClassEventBuilder extends Builder<Event> {
	private static final String TYPE = "set_cont_class";
	
	private static final String _time = "time";
	private static final String _info = "info";
	private static final String _vehicle = "vehicle";
	private static final String _class = "class";
	
	public SetContClassEventBuilder() {
		super(SetContClassEventBuilder.TYPE);
	}

	@Override
	protected Event createTheInstance(JSONObject data) {		
		try {
			List<Pair<String, Integer>> cs = new ArrayList<>();
			JSONArray ja = data.getJSONArray(SetContClassEventBuilder._info);
			JSONObject jo; 
			for (int x = ja.length() -1 ; x >= 0; x--) {
				jo = ja.getJSONObject(x);
				cs.add(new Pair<String, Integer>(jo.getString(SetContClassEventBuilder._vehicle), 
												 jo.getInt(SetContClassEventBuilder._class)));
			}
			Collections.reverse(cs);
			
			return new SetContClassEvent(data.getInt(SetContClassEventBuilder._time), cs);
		} catch (Exception ex) {
			throw new IllegalArgumentException("Something went wronge in" + SetContClassEventBuilder.TYPE);
		}
	}
}

/*
{
	
	"type" : "set_cont_class",
	"data" : {
		"time" : 10,
		"info" : [ { "vehicle" : v1, "class": 3 },
			{ "vehicle" : v4, "class": 2 },
			...
			]
	}
}
*/
