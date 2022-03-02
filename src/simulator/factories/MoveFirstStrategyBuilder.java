package simulator.factories;

import org.json.JSONObject;

import simulator.model.MoveFirstStrategy;

public class MoveFirstStrategyBuilder extends Builder<MoveFirstStrategy> {
	
	private static final String TYPE = "move_first_dqs";
	
	public MoveFirstStrategyBuilder () {
		super(MoveFirstStrategyBuilder.TYPE);
	}
	
	@Override
	protected MoveFirstStrategy createTheInstance(JSONObject data) {
		try {
			MoveFirstStrategy result = new MoveFirstStrategy(); 
			return result; 
		} catch (Exception ex) {
			throw new IllegalArgumentException("Something went wronge in" + MoveFirstStrategyBuilder.TYPE);
		}
	}
}
