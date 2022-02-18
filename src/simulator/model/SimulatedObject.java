package simulator.model;

import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

public abstract class SimulatedObject {

	protected String _id;

	SimulatedObject(String id) {
		_id = id;
	}

	public String getId() {
		return _id;
	}

	@Override
	public String toString() {
		return _id;
	}

	abstract void advance(int time);

	abstract public JSONObject report();
	
	static public <T extends SimulatedObject> JSONArray list2jArray(List<T> list) {
		JSONArray jArray = new JSONArray();
		for (SimulatedObject obj: list) {
			jArray.put(obj.report());
		}
		return jArray;
	}
}
