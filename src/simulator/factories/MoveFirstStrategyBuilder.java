package simulator.factories;

import org.json.JSONObject;

import simulator.model.DequeuingStrategy;
import simulator.model.MoveFirstStrategy;

public class MoveFirstStrategyBuilder extends Builder<DequeuingStrategy> {
	
	private static final String TYPE = "move_first_dqs";
	
	public MoveFirstStrategyBuilder () {
		super(MoveFirstStrategyBuilder.TYPE);
	}
	
	@Override
	protected MoveFirstStrategy createTheInstance(JSONObject data) {
			MoveFirstStrategy result = new MoveFirstStrategy(); 
			return result; 
	}
}
