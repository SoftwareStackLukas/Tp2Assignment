package simulator.model;

import java.util.List;

public class RoundRobinStrategy implements LightSwitchStrategy {
	private int timeSlot;
	
	public RoundRobinStrategy(int timeSlot) {
		this.timeSlot = timeSlot;
	}
	
	@Override
	public int chooseNextGreen(List<Road> roads, List<List<Vehicle>> qs, int currGreen, int lastSwitchingTime, int currTime) {
		int result;
		//If n list == null
		try {
			if (!roads.isEmpty()) {
				if (currGreen == -1) {
					result = 0;
				} else if (currTime - lastSwitchingTime < timeSlot) {
					result = currGreen;
				} else {
					result = (currGreen + 1) % roads.size();
				}
			} else result = -1;
		} catch (Exception e) {
			result = -1; 
		}
		return 0;
	}
}
