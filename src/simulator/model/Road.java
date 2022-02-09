package simulator.model;

import java.util.ArrayList;
import java.util.List;

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
			if (srcJunc == null || destJunc == null || weather == null) {
				throw new NullPointerException();
			} else if (maxSpeed <= 0) {
				throw new IllegalArgumentException("maxSpeed should be a positive number");
			} else if (contLimit < 0) {
				throw new IllegalArgumentException("contLimit should be non negative");
			} else if (length <= 0) {
				throw new IllegalArgumentException("length should be a positive number");
			}
			totalCO2 = 0;
			speedLimit = maxSpeed;
			vehicleList = new ArrayList<Vehicle>();
			// The constructor should add the road as an incoming road to its destination junction, and
			// as an outgoing road of its source junction
	}
	
	abstract void reduceTotalContamination();
	abstract void updateSpeedLimit();
	abstract int calculateVehicleSpeed(Vehicle v);
	
	void enter(Vehicle v) {
		// it should check that following
		//		hold and throw a corresponding exception otherwise: the vehicle’s location is 0; the
		//		vehicle’s speed is 0.
		vehicleList.add(v);
	}
	
	void exit(Vehicle v) {
		
	}
	
	void setWeather(Weather w) {
		
	}

	void addContamination(int c) {
		
	}
	
	
	void advance(int time) {
		
	}
	
	public JSONObject report() {
		
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
	
	// ------------------------------------------- Getters and Setters ----------------------------------------
	
	public Junction getSrcJunc() {
		return srcJunc;
	}

	public Junction getDestJunc() {
		return destJunc;
	}

	public int getMaxSpeed() {
		return maxSpeed;
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

	public List<Vehicle> getVehicleList() {
		// should return the list of vehicles as a read-only list
		// (i.e., it returns Collections.unmodifiableList(vehicles) where vehicles is the list of vehilces).
		return unmodifiableList(vehicleList);
	}

//	public void setSrcJunc(Junction srcJunc) {
//		this.srcJunc = srcJunc;
//	}
//
//	public void setDestJunc(Junction destJunc) {
//		this.destJunc = destJunc;
//	}
//
//	public void setMaxSpeed(int maxSpeed) {
//		this.maxSpeed = maxSpeed;
//	}
//
//	public void setLength(int length) {
//		this.length = length;
//	}
//
//	public void setContLimit(int contLimit) {
//		this.contLimit = contLimit;
//	}
//
//	public void setContTotal(int contTotal) {
//		this.contTotal = contTotal;
//	}
//
//	public void setVehicleList(List<Vehicle> vehicleList) {
//		this.vehicleList = vehicleList;
//	}

}



