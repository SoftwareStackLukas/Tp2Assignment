package simulator.model;

import java.util.List;
import java.util.Map;

import org.json.JSONObject;

public class Junction extends SimulatedObject {
	private List<Road> incomingRoads = null;
	private Map<Junction,Road> outgoingRoads = null;
	private List<List<Vehicle>> queues = null;
	private int greenLightIndex;
	private int lastSwitchingTime;
	private LightSwitchStrategy lightSwitchingStrategy = null;
	private DequeingStrategy dequeuingStrategy = null;
	private int x;
	private int y;
	
	Junction(String id, LightSwitchStrategy IsStrategy, DequeingStrategy
			dqStrategy, int xCoor, int yCoor) {
		super(id);
		if (IsStrategy == null || dqStrategy == null) {
			throw new IllegalArgumentException("A Strategy can not be null."); 
		} else {
			this.lightSwitchingStrategy = IsStrategy;
			this.dequeuingStrategy = dqStrategy;
		}
		if (xCoor < 0 || yCoor < 0) {
			throw new IllegalArgumentException("Coordinat can not be negativ"); 
		} else {
			this.x = xCoor;
			this.y = yCoor;
		}
	}
	
	void addIncommingRoad(Road r) {
		
	}
	
	void addOutGoingRoad(Road r) {
		
	}
	
	void enter(Vehicle v) {
		
	}
	
	Road roadTo(Junction j) {
		return null;
	}
	
	@Override
	void advance(int time) {
		// TODO Auto-generated method stub

	}

	@Override
	public JSONObject report() {
		// TODO Auto-generated method stub
		return null;
	}

}
