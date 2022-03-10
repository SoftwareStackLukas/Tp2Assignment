package simulator.factories;

import org.json.JSONObject;

import simulator.model.LightSwitchingStrategy;
import simulator.model.MostCrowdedStrategy;

public class MostCrowdedStrategyBuilder extends Builder<LightSwitchingStrategy> {

	private static final String TYPE = "most_crowded_lss";
	
	public MostCrowdedStrategyBuilder() {
		super(TYPE);
	}

	@Override
	protected MostCrowdedStrategy createTheInstance(JSONObject data) {
		int timeSlot = 1;
		if (data.has("timeslot")) {
			timeSlot = data.getInt("timeslot");
		}
		return new MostCrowdedStrategy(timeSlot);
	}
}
