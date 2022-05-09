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
		int gVipCount = 0;
		int vipCount = 0;
		Road r = null;
		if (!roads.isEmpty()) {
			if (currGreen == -1) {
				for(int x = 0; x < roads.size(); x++) {
					
					for(Vehicle v : roads.get(x).getVehicles()) {
						if (v.getId().endsWith(this.vipTag)) vipCount++;
					}
					if (vipCount >  gVipCount) {
						r = roads.get(x);
						gVipCount = vipCount;
					}
				}
			} else {
				for(int x = 0; x < roads.size(); x++) {
					
					for(Vehicle v : roads.get((currGreen + 1 + x) % roads.size()).getVehicles()) {
						if (v.getId().endsWith(this.vipTag)) vipCount++;
					}
					if (vipCount >  gVipCount) {
						r = roads.get((currGreen + 1 + x) % roads.size());
						gVipCount = vipCount;
					}
				}
			}	
		}
		//Case if no road has a vip car
		if (r == null) {
			RoundRobinStrategy round = new RoundRobinStrategy(this.timeSlot);
			result = round.chooseNextGreen(roads, qs, currGreen, lastSwitchingTime, currTime);
		}
		return result;
	}
}

