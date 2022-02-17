package simulator.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
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
	private int xCoor;
	private int yCoor;
	
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
			this.lightSwitchingStrategy = IsStrategy;
			this.dequeuingStrategy = dqStrategy;
			this.incomingRoads = new ArrayList<>();
			this.outgoingRoads = new HashMap<>();
			this.queues = new ArrayList<>();
			
			this.greenLightIndex = -1;
			this.xCoor = xCoor;
			this.yCoor = yCoor;
		}
	}
	
	void addIncommingRoad(Road r) {
		if (r.getDestJunc().equals(this)) {
			this.incomingRoads.add(r);
			LinkedList<Vehicle> waitingCars = new LinkedList<>();
			this.queues.add(waitingCars);
			this.			
		} else {
			
		}
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
