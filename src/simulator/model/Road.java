package simulator.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

public abstract class Road extends SimulatedObject {
	Junction srcJunc, destJunc;
	int maxSpeed, length, contLimit, totalCO2, speedLimit;
	Weather weather;
	List<Vehicle> vehicleList; 
	/* Should always be sorted by vehicle location
	   Note that multiple vehicles can be at the same location, however, their order of
	   arrival at this location must be preserved */
	
	Road(String id, Junction srcJunc, Junction destJunc, int maxSpeed,
			int contLimit, int length, Weather weather) {
			super(id);
			if (id == null || srcJunc == null || destJunc == null || weather == null) {
				throw new NullPointerException();
			} else if (id.isEmpty()) {
				throw new IllegalArgumentException("id must be a non-empty string");
			} else if (maxSpeed <= 0) {
				throw new IllegalArgumentException("maxSpeed should be a positive number");
			} else if (contLimit < 0) {
				throw new IllegalArgumentException("contLimit should be non negative");
			} else if (length <= 0) {
				throw new IllegalArgumentException("length should be a positive number");
			}

			totalCO2 = 0;
			vehicleList = new ArrayList<Vehicle>();
			this.srcJunc = srcJunc;
			this.destJunc = destJunc;
			this.maxSpeed = maxSpeed;
			this.speedLimit = maxSpeed;
			this.contLimit = contLimit;
			this.length = length;
			this.weather = weather;
			
			srcJunc.addOutGoingRoad(this);
			destJunc.addIncommingRoad(this);
			// The constructor should add the road as an incoming road to its destination junction, and
			// as an outgoing road of its source junction
	}
	
	abstract void reduceTotalContamination();
	abstract void updateSpeedLimit();
	abstract int calculateVehicleSpeed(Vehicle v);
	
	void enter(Vehicle v) {
		if (v.getLocation() != 0) {
			throw new IllegalArgumentException("Location is != 0");
		} else if (v.getSpeed() != 0) {
			throw new IllegalArgumentException("Speed is != 0");
		}
		vehicleList.add(v);
	}
	
	void exit(Vehicle v) {
		vehicleList.remove(v);
	}
	
	void setWeather(Weather w) {
		if (w == null) {
			throw new NullPointerException("Wheather is null");
		}
		this.weather = w;
	}

	void addContamination(int c) {
		if (c < 0) {
			throw new IllegalArgumentException("contamination should be non negative");
		}
		this.totalCO2 += c;
	}
	
	@Override
	void advance(int time) {
		this.reduceTotalContamination();
		this.updateSpeedLimit();
		for (Vehicle v: vehicleList) {
			v.setSpeed(calculateVehicleSpeed(v));
			v.advance(time);
		}
//		vehicleList.sort(comparator);
		vehicleList.sort((v1, v2) -> v2.getLocation() - v1.getLocation());
	}
	
	@Override
	public JSONObject report() {
		JSONObject jo = new JSONObject();
		jo.put("id", this._id);
		jo.put("speedlimit", this.speedLimit);
		jo.put("weather", this.weather.toString());
		jo.put("co2", this.totalCO2);
		
		JSONArray jarr = new JSONArray();
		for (Vehicle v: vehicleList) {
			jarr.put(v._id);			
		}
		jo.put("vehicles", jarr);
				
		return jo;
	}
	
	// ------------------------------------------- Getters and Setters ----------------------------------------
	
	public Junction getSrc() {
		return srcJunc;
	}

	public Junction getDest() {
		return destJunc;
	}

	public int getMaxSpeed() {
		return maxSpeed;
	}
	
	public int getSpeedLimit() {
		return speedLimit;
	}

	public int getLength() {
		return length;
	}

	public int getContLimit() {
		return contLimit;
	}

	public int getTotalCO2() {
		return totalCO2;
	}

	public Weather getWeather() {
		return weather;
	}

	public List<Vehicle> getVehicles() {
		// should return the list of vehicles as a read-only list
		// (i.e., it returns Collections.unmodifiableList(vehicles) where vehicles is the list of vehilces).
		return Collections.unmodifiableList(vehicleList);
	}
}



