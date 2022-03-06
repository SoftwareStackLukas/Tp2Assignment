package simulator.model;

import java.util.ArrayList;
import java.util.Collections;
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
	private LightSwitchingStrategy lightSwitchingStrategy = null;
	private DequeuingStrategy dequeuingStrategy = null;
	private int xCoor;
	private int yCoor;
	
	Junction(String id, LightSwitchingStrategy IsStrategy, DequeuingStrategy
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
	
	void enter(Vehicle v) {
		queueMap.get(v.getRoad()).add(v);
	}
	
	Road roadTo(Junction j) {
		return this.outgoingRoads.get(j);
	}
	
	//has to be done!
	@Override
	void advance(int time) {
		if (this.greenLightIndex != -1 && this.greenLightIndex < incomingRoads.size()) {
			List<Vehicle> currQueue = this.queueMap.get(incomingRoads.get(this.greenLightIndex));
			if (!currQueue.isEmpty()) {
				List<Vehicle> toDequeue = this.dequeuingStrategy.dequeue(currQueue);
				for (Vehicle v : toDequeue) {
					v.moveToNextRoad();
					currQueue.remove(v);
				}
			}
		}
		
		int newGreen = this.lightSwitchingStrategy.chooseNextGreen(incomingRoads, this.queues, this.greenLightIndex, this.lastSwitchingTime, time);
		if (newGreen != this.greenLightIndex) {
			this.greenLightIndex = newGreen;
			this.lastSwitchingTime = time;
		}
		
		
//		if (this.greenLightIndex != 1 && this.greenLightIndex < this.queues.size() -1) {
//			List<Vehicle> toAdvanceCar = this.queues.get(this.greenLightIndex);
//			if (toAdvanceCar.size() != 0) {
//				toAdvanceCar = this.dequeuingStrategy.dequeue(toAdvanceCar);
//				for (Vehicle v : toAdvanceCar) {
//					v.advance(time);
//					toAdvanceCar.remove(v);
//				}
//			}
//		}
//		
//		int greenLightIndex = this.lightSwitchingStrategy.chooseNextGreen(this.incomingRoads, this.queues, this.greenLightIndex, this.lastSwitchingTime, time);
//		if (this.greenLightIndex != greenLightIndex) {
//			this.greenLightIndex = greenLightIndex;
//		}
	}

	@Override
	public JSONObject report() {
		JSONObject jo = new JSONObject();
		jo.put("id", this._id);
		if (this.greenLightIndex != -1) {
			jo.put("green", incomingRoads.get(greenLightIndex).getId());
		} else {
			jo.put("green", "none");
		}
		JSONArray qu = new JSONArray();
		JSONArray jsonVehicles;
		JSONObject jObj;
		for (Map.Entry<Road, List<Vehicle>> entry : queueMap.entrySet()) {
			qu = new JSONArray();
			jObj = new JSONObject();
		    jsonVehicles = new JSONArray();
		    Road road = entry.getKey();
		    List<Vehicle> vehicles = entry.getValue();
			for (Vehicle v : vehicles) {
				jsonVehicles.put(v.getId());
			}
			jObj.put("road", road.getId());
			jObj.put("vehicles", jsonVehicles);
			qu.put(jObj);
//			jsonArrayQu.put(quID, jsonVehicles);
		}
		jo.put("queues", qu);
		
		return jo;
	}

	//Getters of the class
	public int getX() {
		return xCoor;
	}
	
	public int getY() {
		return yCoor;
	}
	
	public int getGreenLightIndex() {
		return this.greenLightIndex;
	}
	
	public List<Road> getInRoads(){
		return Collections.unmodifiableList(this.incomingRoads);
	}
	
	public Map<Junction, Road> getOutRoads(){
		return Collections.unmodifiableMap(this.outgoingRoads);
	}

	public List<List<Vehicle>> getQueueList() {
		return Collections.unmodifiableList(this.queues);
	}
}
