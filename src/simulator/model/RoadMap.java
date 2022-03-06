package simulator.model;

import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONObject;

public class RoadMap {
	private List<Junction> junctionList;
	private List<Road> roadList;
	private List<Vehicle> vehicleList;
	private Map<String, Junction> junctionMap;
	private Map<String, Road> roadMap;
	private Map<String, Vehicle> vehicleMap;
	
	
	public RoadMap() {
		junctionList = new ArrayList<Junction>();
		roadList = new ArrayList<Road>();
		vehicleList = new ArrayList<Vehicle>();
		junctionMap = new HashMap<String, Junction>();
		roadMap = new HashMap<String, Road>();
		vehicleMap = new HashMap<String, Vehicle>();
	}
	
	boolean areConnected(Junction j1, Junction j2) {
		boolean connected = false;
		// Searches linearly for a road that connects the given junctions.
		for (Road road: roadList) {
			if (road.getSrc() == j1 && road.getDest() == j2) {
				connected = true;
				break;				
			}
		}
		return connected;
	}
	
	boolean isValidItinerary(List<Junction> itinerary) {
		boolean valid = true;
		// Checks if there is road that connects every pair of consecutive junctions.
		// Big computational cost
		for (int i = 0; i < itinerary.size() - 1; i++) {
			if (! areConnected(itinerary.get(i), itinerary.get(i + 1))) {
				valid = false;
				break;
			}
		}
		return valid;
	}
	
	void addJunction(Junction j) {
		junctionList.add(j);
		if (! junctionMap.containsKey(j.getId())) {
			junctionMap.put(j.getId(), j);			
		} else {
			throw new InvalidParameterException("cannot add object with same id");
		}
	}
	
	void addRoad(Road r) {
		roadList.add(r);
		if (! roadMap.containsKey(r.getId())) {
			roadMap.put(r.getId(), r);			
		} else {
			throw new InvalidParameterException("cannot add object with same id");
		}
	}
	
	void addVehicle(Vehicle v) {
		vehicleList.add(v);
		if (vehicleMap.containsKey(v.getId())) {
			throw new InvalidParameterException("Cannot add object with same id");
		} else if (! isValidItinerary(v.getItinerary())){
			throw new InvalidParameterException("Itinerary is not valid");
		} else {
			vehicleMap.put(v.getId(), v);	
		}
	}
	
	public Junction getJunction(String id) {
		return junctionMap.get(id);
	}
	
	public Road getRoad(String id) {
		return roadMap.get(id);
	}

	public Vehicle getVehicle(String id) {
		return vehicleMap.get(id);
	}

	public List<Junction> getJunctions(){
		return Collections.unmodifiableList(junctionList);
	}

	public List<Road> getRoads() {
		return Collections.unmodifiableList(roadList);	
	}

	public List<Vehicle> getVehilces() {
		return Collections.unmodifiableList(vehicleList);	
	}
	
	public JSONObject report() {
		JSONObject jo = new JSONObject();
		jo.put("junctions", SimulatedObject.list2jArray(junctionList));
		jo.put("roads", SimulatedObject.list2jArray(roadList));
		jo.put("vehicles", SimulatedObject.list2jArray(vehicleList));
		return jo;
	}
}









