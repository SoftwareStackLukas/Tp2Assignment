package simulator.factories;

import org.json.JSONObject;

import simulator.model.MoveAllStrategy;

public class MoveAllStrategyBuilder extends Builder<MoveAllStrategy> {
	
	private static final String TYPE = "move_all_dqs";
	
	public MoveAllStrategyBuilder() {
		super(MoveAllStrategyBuilder.TYPE);
	}
	
	@Override
	protected MoveAllStrategy createTheInstance(JSONObject data) {
		try {
			MoveAllStrategy result = new MoveAllStrategy(); 
			return result;  
		} catch (Exception ex) {
			throw new IllegalArgumentException("Something went wronge in" + MoveAllStrategyBuilder.TYPE);
		}
	}
}
