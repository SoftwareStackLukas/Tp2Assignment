package simulator.model;

import java.util.List;

import simulator.misc.Pair;

public class NewSetContClassEvent extends Event {
	private List<Pair<String,Integer>> cs;
	
	public NewSetContClassEvent(int time, List<Pair<String,Integer>> cs) {
		super(time);
		if (cs == null) {
			throw new NullPointerException("cs can not be null");
		} else {
			this.cs = cs;
		}
	}
	
	@Override
	void execute(RoadMap map) {
		for (Pair<String, Integer> c : cs) {
			Vehicle tempV = map.getVehicle(c.getFirst());
			if (tempV == null) throw new NullPointerException("Vehicle is null");
			tempV.setContClass(c.getSecond());
		}
	}
}
