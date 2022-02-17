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
		int toSetSpeed = Math.min(this.maxSpeed, s);		
		if (0 > toSetSpeed) {
			throw new IllegalArgumentException("The speed is out of the legal range.");
		} 
		this.currSpeed = toSetSpeed;
	}

	void setContClass(int c) {
		if (Math.subtractExact(10, c) < 0) {
			throw new IllegalArgumentException("The contamination class has to be between [0,10]");
		}
		this.contClass = c;
	}
	
	
	//Has to be coded
	@Override
	void advance(int time) {
		// TODO: Fix this method, it always advances 0.
		if (this.status == VehicleStatus.TRAVELING) {
			int previewsLocation =  this.location;
			this.location = Math.addExact(this.location, this.currSpeed);
			//the length of the current road;
			int c = Math.multiplyExact(time, previewsLocation);
			this.contClass += c;
			road.addContamination(c);
		}
		
//	modify this or take it // see after running the tests.
//		if(this.status == VehicleStatus.TRAVELING) {
//			//Update location
//			int newLocation = Math.min(this.getLocation()+this.getCurrSpeed(), this.getRoad().getLength());
//			int distTravelledOnCycle = newLocation - getLocation();		// For contamination calculus
//			this.setLocation(newLocation);
//			this.totalDistance += distTravelledOnCycle;
//			
//			//Update contamination
//			int c = getContClass() * distTravelledOnCycle;
//			setTotalContamination(getTotalContamination() + c);
//			this.road.addContamination(c);			
//			
//			//Check end of road
//			if(this.getLocation() == this.getRoad().getLength()) {
//				this.setStatus(VehicleStatus.WAITING);
//				this.setSpeed(0);
//				itinerary.get(itineraryIdx).enter(this);
//			}
//		}
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
			// Continues the trip
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
				// Has arrived to end of itinerary
				this.status = VehicleStatus.ARRIVED;
			}
		}
		else {
			// Status = ARRIVED -> End the trip -> no new road -> throw exception
			// Status = TRAVELLING -> Still moving on previous road -> throw exception			
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
	int getSpeed() {
		return this.currSpeed;
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
	
	int getContClass() {
		return this.contClass;
	}
	
	List<Junction> getItinerary() {
		return this.itinerary;
	}
	
	Road getRoad() {
		return this.road;
	}

	int getTotalCO2() {
		return this.totalContamination;
	}
}
