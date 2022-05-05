package simulator.factories;

import org.json.JSONObject;

import simulator.model.DequeuingStrategy;
import simulator.model.MoveAllStrategy;
import simulator.model.VIPDeque;

public class VIPDequeStrategyBuilder  extends Builder<DequeuingStrategy> {
	private static final String TYPE = "vip_dqs";
	
	public VIPDequeStrategyBuilder() {
		super(VIPDequeStrategyBuilder.TYPE);
	}
	
	@Override
	protected VIPDeque createTheInstance(JSONObject data) {
		try {
			
			String vip = "vip";
		    if (data.has("viptag")) {
		    	data.getString("viptag");		
		    }
		    
			Integer limit = 5;
			if (data.has("limit")) {
				limit = data.getInt("limit");
			}
			
			VIPDeque result = new VIPDeque(vip, limit); 
			return result;  
		} catch (Exception ex) {
			throw new IllegalArgumentException("Something went wronge in" + VIPDequeStrategyBuilder.TYPE);
		}
	}
}
