package simulator.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.json.JSONObject;

import simulator.Exception.VehicleException;

public class Vehicle extends SimulatedObject {
	private List<Junction> itinerary;
	private int maxSpeed;
	private int currentSpeed;
	private VehicleStatus status;
	private Road road;
	private int location = 0;
	private int contaminationClass;
	private int totalTraveledDistance = 0;
	
	Vehicle(String id, int maxSpeed, int contClass, List<Junction> itinerary) throws VehicleException {
		super(id);
		if (!(!id.isEmpty() &&
				maxSpeed > 0 &&
				(contClass >= 0 && contClass <= 10) &&
				itinerary.size() >= 2)) {
			throw new VehicleException("Params are corropted");
		}
		this.itinerary = Collections.unmodifiableList(new ArrayList<>(itinerary));
		this.maxSpeed = maxSpeed;
		this.contaminationClass = contClass;
	}
	
	void setSpeed(int s) {
		this.currentSpeed = s;
	}
	
	void setContaminationClass(int c) {
		this.contaminationClass = c;
	}
	
	@Override
	void advance(int time) {
		// TODO Auto-generated method stub

	}

	void moveToNextRoad() {
		
	}
	
	@Override
	public JSONObject report() {
		// TODO Auto-generated method stub
		return null;
	}

}
