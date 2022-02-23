package simulator.model;

public class NewVehicleEvent extends Event {

	public NewVehicleEvent(int time, String id, int maxSpeed, int
			contClass, List<String> itinerary) {
			super(time);
			// ...
	}
	
	@Override
	void execute(RoadMap map) {
		// TODO Auto-generated method stub

	}


}
