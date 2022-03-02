package simulator.factories;

import org.json.JSONArray;
import org.json.JSONObject;

import simulator.model.DequeuingStrategy;
import simulator.model.Event;
import simulator.model.LightSwitchingStrategy;
import simulator.model.NewJunctionEvent;


public class NewJunctionEventBuilder extends Builder<Event> {
	private static final String TYPE = "new_junction";
	
	private Factory<LightSwitchingStrategy> lssFactory;
	private Factory<DequeuingStrategy> dqsFactory;

	public NewJunctionEventBuilder(Factory<LightSwitchingStrategy> lssFactory, 
			Factory<DequeuingStrategy> dqsFactory) {
		super(TYPE);
		this.lssFactory = lssFactory;
		this.dqsFactory = dqsFactory;
	}

	@Override
	protected NewJunctionEvent createTheInstance(JSONObject data) {
		try {
			int time = data.getInt("time");
		
			String id = data.getString("id");
		
			JSONArray coor = data.getJSONArray("coor");
			int xCoor = coor.getInt(0);
			int yCoor = coor.getInt(1);
			
			LightSwitchingStrategy lss = lssFactory.createInstance(data.getJSONObject("ls_strategy"));
			DequeuingStrategy dqs = dqsFactory.createInstance(data.getJSONObject("dq_strategy"));			
			
			return new NewJunctionEvent(time, id, lss, dqs, xCoor, yCoor);
		} catch (Exception ex) {
			throw new IllegalArgumentException("Something went wronge in" + NewJunctionEventBuilder.TYPE);
		}
	}
}


/*
JSON Structure:
{
	"type" : "new_junction",
	"data" : {
		"time" : 1,
		"id" : "j1",
		"coor" : [100,200],
		"ls_strategy" : { "type" : "round_robin_lss", "data" : {"timeslot" : 5} },
		"dq_strategy" : { "type" : "move_first_dqs", "data" : {} }
	}
}
 */
