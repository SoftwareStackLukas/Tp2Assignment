package simulator.model;

import java.util.LinkedList;
import java.util.List;

public class NewVehicleEvent extends Event {
	private String id;
	private int maxSpeed;
	private int contClass;
	private List <String> itinerary;
	
	
	public NewVehicleEvent(int time, String id, int maxSpeed, int
			contClass, List<String> itinerary) {
			super(time);
			this.id = id;
			this.maxSpeed = maxSpeed;
			this.contClass = contClass;
			this.itinerary = itinerary;
	}
	
	@Override
	void execute(RoadMap map) {
		List<Junction> tempItinerary = new LinkedList<Junction>();
		for (String id : itinerary) {
			Junction temp = map.getJunction(id);
			if (temp != null) {
				tempItinerary.add(temp);
			}
		}
		Vehicle v = new Vehicle(id, maxSpeed, contClass, tempItinerary);
		map.addVehicle(v);
		v.moveToNextRoad();
	}
}
