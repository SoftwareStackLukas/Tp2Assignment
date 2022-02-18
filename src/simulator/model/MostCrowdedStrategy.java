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
			} else if (currGreen == -1) {				
				result = calcStartQueueIndex(qs);
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
		//Note that it might return currGreen
		int index = 0;
		int countOfCars = 0;
		//calcNextQueueIndex
		for (int x = ((currGreen + 1) % roads.size()); x < qs.size() - 1; x++) {
			if (countOfCars < qs.get(x).size()) {
				index = x;
				countOfCars = qs.get(x).size();
			}
			if (x > qs.size() - 1) {
				x = -1;
			}
		}
		
		return index;
	}

	private int calcStartQueueIndex(List<List<Vehicle>> qs) {
		int index = 0;
		int countOfCars = 0;
		for (List<Vehicle> vLs : qs ) {
			if (vLs.size() > countOfCars) {
				index = qs.indexOf(vLs);
				countOfCars = vLs.size();
			}
		}
		return index;
	}

}
