package simulator.model;

import java.util.List;

class MostCrowdedStrategy implements LightSwitchingStrategy {
	private int timeSlot;
	
	
	public MostCrowdedStrategy(int timeSlot) {
		this.timeSlot = timeSlot;
	}
	
	@Override
	public int chooseNextGreen(List<Road> roads, List<List<Vehicle>> qs, int currGreen, int lastSwitchingTime,
			int currTime) {
		// TODO Auto-generated method stub
		return 0;
	}

}
