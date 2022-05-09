package simulator.factories;

import org.json.JSONObject;

import simulator.model.LightSwitchingStrategy;
import simulator.model.VIPswitching;

public class VIPswitchingBuilder extends Builder<LightSwitchingStrategy> {
	private static final String TYPE = "vip_lss";
	
	public VIPswitchingBuilder() {
		super(TYPE);
	}

	@Override
	protected VIPswitching createTheInstance(JSONObject data) {
		int timeSlot = 1;
		String vipTag = "vip"; //vip is taken as default
		if (data.has("timeslot")) timeSlot = data.getInt("timeslot");
		if (data.has("viptag")) vipTag = data.getString("viptag");
		return new VIPswitching(timeSlot, vipTag);
	}
}
