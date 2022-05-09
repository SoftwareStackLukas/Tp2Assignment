package simulator.model;

import java.util.List;

public class VIPswitching implements LightSwitchingStrategy {
	private int timeSlot;
	private String vipTag;
	
	public VIPswitching(int timeSlot, String vipTag) {
		this.timeSlot = timeSlot;
		this.vipTag = vipTag;
	}
	
	@Override
	public int chooseNextGreen(List<Road> roads, List<List<Vehicle>> qs, int currGreen, int lastSwitchingTime, int currTime) {
		int result = -1;
		if (!roads.isEmpty()) {
			int vipCount = 0;
			if (currGreen == -1) {
				for(int x = 0; x < roads.size(); x++) {
					
					for(Vehicle v : roads.get(x).getVehicles()) {
						if (v.getId().endsWith(this.vipTag)) vipCount++;
					}
					if (vipCount != 0) break;
				}
			} else {
				for(int x = 0; x < roads.size(); x++) {
					
					for(Vehicle v : roads.get((currGreen + x) % roads.size()).getVehicles()) {
						if (v.getId().endsWith(this.vipTag)) vipCount++;
					}
					if (vipCount != 0) break;
				}
			}	
			//Case if no road has a vip car
			if (vipCount == 0) {
				RoundRobinStrategy round = new RoundRobinStrategy(this.timeSlot);
				result = round.chooseNextGreen(roads, qs, currGreen, lastSwitchingTime, currTime);
			}
		}
		return result;
	}
}

