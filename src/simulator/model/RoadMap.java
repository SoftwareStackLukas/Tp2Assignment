package simulator.model;

import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;

public class RoadMap {
	private List<Junction> _junctionList;
	private List<Road> _roadList;
	private List<Vehicle> _vehicleList;
	private Map<String, Junction> _junctionMap;
	private Map<String, Road> _roadMap;
	private Map<String, Vehicle> _vehicleMap;
	
	
	public RoadMap() {
		_junctionList = new ArrayList<Junction>();
		_roadList = new ArrayList<Road>();
		_vehicleList = new ArrayList<Vehicle>();
		_junctionMap = new HashMap<String, Junction>();
		_roadMap = new HashMap<String, Road>();
		_vehicleMap = new HashMap<String, Vehicle>();
	}
	
	
	void addJunction(Junction j) {
		_junctionList.add(j);
		if (! _junctionMap.containsKey(j.getId())) {
			_junctionMap.put(j.getId(), j);			
		} else {
			throw new InvalidParameterException("cannot add object with same id");
		}
	}
	
	void addRoad(Road r) {
		_roadList.add(r);
		if (! _roadMap.containsKey(r.getId())) {
			_roadMap.put(r.getId(), r);			
		} else {
			throw new InvalidParameterException("cannot add object with same id");
		}
	}
	
	void addVehicle(Vehicle v) {
		_vehicleList.add(v);
		if (! _vehicleMap.containsKey(v.getId())) {
			_vehicleMap.put(v.getId(), v);			
		} else {
			throw new InvalidParameterException("cannot add object with same id");
		}
	}
	
	public Junction getJunction(String id) {
		return _junctionMap.get(id);
	}
	
	public Road getRoad(String id) {
		return _roadMap.get(id);
	}

	public Vehicle getVehicle(String id) {
		return _vehicleMap.get(id);
	}

	public List<Junction> getJunctions(){
		return Collections.unmodifiableList(_junctionList);
	}

	public List<Road> getRoads() {
		return Collections.unmodifiableList(_roadList);	
	}

	public List<Vehicle> getVehilces() {
		return Collections.unmodifiableList(_vehicleList);	
	}
	
	public JSONObject report() {
		JSONObject jo = new JSONObject();
		jo.put("junctions", SimulatedObject.list2jArray(_junctionList));
		jo.put("roads", SimulatedObject.list2jArray(_roadList));
		jo.put("vehicles", SimulatedObject.list2jArray(_vehicleList));
		return jo;
	}
}









