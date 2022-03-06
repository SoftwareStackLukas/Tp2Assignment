package simulator.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.json.JSONObject;

public class Vehicle extends SimulatedObject {
	private List<Junction> itinerary;
	private int maxSpeed;
	private int currSpeed;
	private VehicleStatus status;
	private Road road;
	private int location;
	private int contClass;
	private int totalContamination;
	private int totalTraveledDistance;
	
	//To identify the junction
	private int itineraryIdx;
	
	Vehicle(String id, int maxSpeed, int contClass, List<Junction> itinerary) {
		super(id);
		if (!(!id.isEmpty() &&
				maxSpeed > 0 &&
				(contClass >= 0 && contClass <= 10) &&
				itinerary.size() >= 2)) {
			throw new IllegalArgumentException("IllegalArguments");
		}
		//Create an unmuteable List
		this.itinerary = Collections.unmodifiableList(new ArrayList<>(itinerary));
		
		this.maxSpeed = maxSpeed;
		this.contClass = contClass;
		this.currSpeed = 0; 
		this.totalContamination = 0;
		this.status = VehicleStatus.PENDING;
		this.location = 0;
		this.totalTraveledDistance = 0;
		this.itineraryIdx = 0;	
	}
	
	void setSpeed(int s) {
		if (this.status == VehicleStatus.TRAVELING) {
			int toSetSpeed = Math.min(this.maxSpeed, s);		
			if (0 > toSetSpeed) {
				throw new IllegalArgumentException("The speed is out of the legal range.");
			} 
			this.currSpeed = toSetSpeed;
		} else {
			this.currSpeed = 0;
		}
	}

	void setContClass(int c) {
		if (!(c <= 10 && c >= 0)) {
			throw new IllegalArgumentException("The contamination class has to be between [0,10]");
		}
		this.contClass = c;
	}
	
	void setLocation(int loc) {
		this.location = loc;
	}
	
	@Override
	void advance(int time) {
		if (this.status == VehicleStatus.TRAVELING) {
			int location = Math.min(this.getLocation() + this.getSpeed(), this.getRoad().getLength());
			int traveledDistance = Math.subtractExact(location, this.getLocation());
			int c = Math.multiplyExact(traveledDistance, this.contClass);
			
			//Setting global states + adding contamination to road
			this.totalContamination += c;
			this.totalTraveledDistance += traveledDistance;
			this.location = location;
			this.road.addContamination(c);
			
			if (this.getLocation() == this.road.getLength()) {
				this.status = VehicleStatus.WAITING;
				this.currSpeed = 0;
				this.itinerary.get(this.itineraryIdx).enter(this);;
			}
		}
	}

	void moveToNextRoad() {
		if (this.status == VehicleStatus.PENDING) {
			// Starts the trip -> no previous road
			this.road = itinerary.get(0).roadTo(itinerary.get(1));
			this.road.enter(this);
			itineraryIdx++;
			this.status = VehicleStatus.TRAVELING;
		}
		else if (this.status == VehicleStatus.WAITING){
			this.road.exit(this);			
			if (itineraryIdx < itinerary.size() - 1) {
				this.road = itinerary.get(itineraryIdx).roadTo(itinerary.get(itineraryIdx + 1));
				this.location = 0;
				this.currSpeed = 0;
				this.status = VehicleStatus.TRAVELING;
				this.road.enter(this);
				itineraryIdx++;
			}
			else {
				this.status = VehicleStatus.ARRIVED;
			}
		}
		else {
			//Using https://docs.oracle.com/javase/7/docs/api/java/lang/UnsupportedOperationException.html
			throw new UnsupportedOperationException();
		}
	}
	
	@Override
	public JSONObject report() {
		JSONObject jo = new JSONObject();
		
		jo.put("id", this._id);
		jo.put("speed", this.currSpeed);
		jo.put("distance", this.totalTraveledDistance);
		jo.put("co2", this.totalContamination);
		jo.put("class", this.contClass);
		jo.put("status", this.status);
		if (!(this.status == VehicleStatus.ARRIVED 
				|| this.status == VehicleStatus.PENDING)) {
			jo.put("road", this.road.getId());
			jo.put("location", this.location);
		}
		
		return jo;
	}
	
	//Class getters
	int getLocation() {
		return this.location;
	}
	
	int getSpeed() {
		return this.currSpeed;
	}
	
	int getMaxSpeed() {
		return this.maxSpeed;
	}
	
	int getContClass() {
		return this.contClass;
	}
	
	VehicleStatus getStatus() {
		return this.status;
	}
	
	int getTotalCO2() {
		return this.totalContamination;
	}
	
	List<Junction> getItinerary() {
		return this.itinerary;
	}
	
	Road getRoad() {
		return this.road;
	}


}
