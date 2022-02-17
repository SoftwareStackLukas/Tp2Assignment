package simulator.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;

public class Junction extends SimulatedObject {
	private List<Road> incomingRoads = null;
	private Map<Junction,Road> outgoingRoads = null;
	private List<List<Vehicle>> queues = null;
	private Map<Road,List<Vehicle>> queueMap;		
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
			this.queueMap = new HashMap<>();
			
			this.greenLightIndex = -1;
			this.xCoor = xCoor;
			this.yCoor = yCoor;
		}
	}
	
	void addIncommingRoad(Road r) {
		if (r.getDest().equals(this)) {
			this.incomingRoads.add(r);
			LinkedList<Vehicle> waitingCars = new LinkedList<>();
			this.queues.add(waitingCars);
			this.queueMap.put(r, waitingCars);			
		} else {
			throw new IllegalArgumentException();
		}
	}
	
	void addOutGoingRoad(Road r) {
		if (this.outgoingRoads.get(r.getDest()) == null &&
				r.getSrc().equals(this)) {
			this.outgoingRoads.put(r.getDest(), r);
		} else {
			throw new IllegalArgumentException("Road is not added to the outgoing roads");
		}
	}
	
	//has to be done!
	void enter(Vehicle v) {
		queueMap.get(v.getRoad()).add(v);
	}
	
	Road roadTo(Junction j) {
		return this.outgoingRoads.get(j);
	}
	
	
	//has to be done!
	@Override
	void advance(int time) {
		if (this.greenLightIndex != 1 && this.greenLightIndex < this.queues.size() -1) {
			List<Vehicle> toAdvanceCar = this.queues.get(this.greenLightIndex);
			if (toAdvanceCar.size() != 0) {
				for (Vehicle v : toAdvanceCar) {
					v.advance(time);
					toAdvanceCar.remove(v);
				}
			}
		}
		
		int greenLightIndex = this.lightSwitchingStrategy.chooseNextGreen(this.incomingRoads, this.queues, this.greenLightIndex, this.lastSwitchingTime, time);
		if (this.greenLightIndex != greenLightIndex) {
			this.greenLightIndex = greenLightIndex;
		}
	}

	@Override
	public JSONObject report() {
		JSONObject jo = new JSONObject();
		jo.put("id", this._id);
		if (this.greenLightIndex != -1) {
			jo.put("green", this.greenLightIndex);
		} else {
			jo.put("green", "none");
		}
		JSONArray jsonArrayQu = new JSONArray();
		JSONObject qu;
		JSONArray jsonVehicles;
		int roadID = 1;
		int quID = 1; 
		for (List<Vehicle> ls : this.queues) {
			qu = new JSONObject();
		    jsonVehicles = new JSONArray();
			for (Vehicle v : ls) {
				jsonVehicles.put(v.getId());
			}
			qu.put("road", roadID);
			qu.put("vehicles", jsonVehicles);
			jsonArrayQu.put(quID, jsonVehicles);
			quID++;
			roadID++;
		}
		jo.put("queues", jsonArrayQu);
		
		return jo;
	}

}
