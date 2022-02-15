package simulator.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.json.JSONObject;

public class Vehicle extends SimulatedObject {
	private List<Junction> itinerary;
	private int maxSpeed;
	private int currentSpeed;
	private VehicleStatus status;
	private Road road;
	private int location = 0;
	private int contaminationClass;
	private int totalContamination = 0;
	private int totalTraveledDistance = 0;
	
	Vehicle(String id, int maxSpeed, int contClass, List<Junction> itinerary) {
		super(id);
		if (!(!id.isEmpty() &&
				maxSpeed > 0 &&
				(contClass >= 0 && contClass <= 10) &&
				itinerary.size() >= 2)) {
			throw new IllegalArgumentException("IllegalArguments");
		}
		this.itinerary = Collections.unmodifiableList(new ArrayList<>(itinerary));
		this.maxSpeed = maxSpeed;
		this.contaminationClass = contClass;
	}
	
	void setSpeed(int s) {
		int toSetSpeed = Math.min(this.maxSpeed, s);		
		if (0 > toSetSpeed) {
			throw new IllegalArgumentException("The speed is out of the legal range.");
		} 
		this.currentSpeed = toSetSpeed;
	}
	
	void setContaminationClass(int c) {
		//[0,10]
		if (Math.subtractExact(10, c) < 0) {
			throw new IllegalArgumentException("The contamination class has to be between [0,10]");
		}
		this.contaminationClass = c;
	}
	
	
	//Has to be coded
	@Override
	void advance(int time) {
		if (this.status == VehicleStatus.TRAVELING) {
			int previewsLocation =  this.location;
			this.location = Math.addExact(this.location, this.currentSpeed);
			//the length of the current road;
			int c = Math.multiplyExact(time, previewsLocation);
			this.contaminationClass += c;
			road.addContamination(c);
		}
	}

	void moveToNextRoad() {
		road.exit(this);
		
		//How to get the new pointer for the road?
		road.enter(this);
	}
	
	@Override
	public JSONObject report() {
		JSONObject jo = new JSONObject();
		
		jo.put("id", this._id);
		jo.put("speed", this.currentSpeed);
		jo.put("distance", this.totalTraveledDistance);
		jo.put("co2", this.totalContamination);
		jo.put("class", this.contaminationClass);
		jo.put("status", this.status);
		if (!(this.status == VehicleStatus.ARRIVED 
				|| this.status == VehicleStatus.PENDING)) {
			jo.put("road", this.road.getId());
			jo.put("location", this.location);
		}
		
		return jo;
	}
	
	
	//Class getters
	int getSpeed() {
		return this.currentSpeed;
	}
	
	int getLocation() {
		return this.location;
	}
	
	int getMaxSpeed() {
		return this.maxSpeed;
	}
	
	VehicleStatus getStatus() {
		return this.status;
	}	
	
	int getContaminationClass() {
		return this.totalContamination;
	}
	
	List<Junction> getItinerary() {
		return this.itinerary;
	}
	
	Road getRoad() {
		return this.road;
	}
}
