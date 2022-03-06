package simulator.model;

import java.util.List;

public class MostCrowdedStrategy implements LightSwitchingStrategy {
	private int timeSlot;
	
	
	public MostCrowdedStrategy(int timeSlot) {
		this.timeSlot = timeSlot;
	}
	
	@Override
	public int chooseNextGreen(List<Road> roads, List<List<Vehicle>> qs, int currGreen, int lastSwitchingTime, int currTime) {
		int result;
		
		try {
			if (roads.isEmpty()) {
				result = -1;
			} else if (currTime-lastSwitchingTime < timeSlot) {
				result = currGreen;
			} else {
				result = calcNextQueueIndex(roads, qs, currGreen);
			}
		} catch (Exception e) {
			result = -1;
		}
		
		return result;
	}

	private int calcNextQueueIndex(List<Road> roads, List<List<Vehicle>> qs, int currGreen) {
		int startIndex = (currGreen + 1) % roads.size();
		int max = qs.get(startIndex).size();
		int maxIndex = startIndex;
		int i = (startIndex + 1) % qs.size();
		
		while (i != startIndex) {
			if (max < qs.get(i).size()) {
				maxIndex = i;
				max = qs.get(i).size();
			}
			i = (i + 1) % qs.size();
		}
		
		return maxIndex;
	}
}
