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
	private Map<Road,List<Vehicle>> queueMapList;		
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
			this.queueMapList = new HashMap<>();
			
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
			this.queueMapList.put(r, waitingCars);			
		} else {
			throw new IllegalArgumentException();
		}
	}
	
	void addOutGoingRoad(Road r) {
		if (this.outgoingRoads.get(r.getDestJunc()) == null &&
				r.getSrcJunc().equals(this)) {
			this.outgoingRoads.put(r.getDestJunc(), r);
		} else {
			throw new IllegalArgumentException("Road is not added to the outgoing roads");
		}
	}
	
	//has to be done!
	void enter(Vehicle v) {
		Road currentRoadVeh = v.getRoad();
		
	}
	
	Road roadTo(Junction j) {
		return this.outgoingRoads.get(j);
	}
	
	
	//has to be done!
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
