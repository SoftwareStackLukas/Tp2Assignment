package simulator.factories;

import org.json.JSONObject;

import simulator.model.DequeuingStrategy;
import simulator.model.MoveAllStrategy;

public class MoveAllStrategyBuilder extends Builder<DequeuingStrategy> {
	
	private static final String TYPE = "move_all_dqs";
	
	public MoveAllStrategyBuilder() {
		super(MoveAllStrategyBuilder.TYPE);
	}
	
	@Override
	protected DequeuingStrategy createTheInstance(JSONObject data) { 
		return new MoveAllStrategy();  
	}
}
