package simulator.factories;

import java.util.List;

import org.json.JSONObject;

import simulator.misc.Pair;
import simulator.model.Event;
import simulator.model.SetContClassEvent;

public class SetContClassEventBuilder extends Builder<Event> {
	private static final String TYPE = "set_cont_class";
	
	private static final String _type = "type";
	private static final String _time = "time";
	private static final String _info = "info";
	
	public SetContClassEventBuilder() {
		super(SetContClassEventBuilder.TYPE);
	}

	@Override
	protected Event createTheInstance(JSONObject data) {		
		try {
			List<Pair<String, Integer>> cs = null;
			//get the info out of the JSON???		
			Event result = new SetContClassEvent(data.getInt(SetContClassEventBuilder._time), 
												cs);
			return result;
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