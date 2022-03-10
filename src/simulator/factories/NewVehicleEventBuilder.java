package simulator.factories;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import simulator.model.Event;
import simulator.model.NewVehicleEvent;

public class NewVehicleEventBuilder extends Builder<Event> {
	private static final String TYPE = "new_vehicle";
	
	private static final String _time = "time";
	private static final String _id = "id";
	private static final String _maxspeed = "maxspeed";
	private static final String _class = "class";
	private static final String _itinerary = "itinerary";
	
	public NewVehicleEventBuilder() {
		super(NewVehicleEventBuilder.TYPE);
	}

	@Override
	protected NewVehicleEvent createTheInstance(JSONObject data) {
		List<String> itinerary = new ArrayList<>();	
		JSONArray ja = data.getJSONArray(NewVehicleEventBuilder._itinerary);

		for (int x = 0; x < ja.length(); x++) {
			String s = ja.getString(x);
			itinerary.add(s);
		}

	    return new NewVehicleEvent(data.getInt(NewVehicleEventBuilder._time), 
													data.getString(NewVehicleEventBuilder._id), 
													data.getInt(NewVehicleEventBuilder._maxspeed), 
													data.getInt(NewVehicleEventBuilder._class), 
													itinerary);
	}

}
