package simulator.factories;

import org.json.JSONObject;

import simulator.model.MoveAllStrategy;

public class MoveAllStrategyBuilder extends Builder<MoveAllStrategy> {
	
	public MoveAllStrategyBuilder(String type) {
		super(type);
	}
	
	@Override
	protected MoveAllStrategy createTheInstance(JSONObject data) {
		MoveAllStrategy result = new MoveAllStrategy(); 
		return result;  
	}
}
