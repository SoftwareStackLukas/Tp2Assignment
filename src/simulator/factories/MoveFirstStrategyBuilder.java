package simulator.factories;

import org.json.JSONObject;

import simulator.model.MoveFirstStrategy;

public class MoveFirstStrategyBuilder extends Builder<MoveFirstStrategy> {
		
	public MoveFirstStrategyBuilder (String type) {
		super(type);
	}
	
	@Override
	protected MoveFirstStrategy createTheInstance(JSONObject data) {
		MoveFirstStrategy result = new MoveFirstStrategy(); 
		return result; 
	}
}
