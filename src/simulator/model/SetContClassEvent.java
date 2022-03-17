package simulator.model;

import java.util.List;

import simulator.misc.Pair;

public class SetContClassEvent extends Event {
	private List<Pair<String,Integer>> cs;
	
	public SetContClassEvent(int time, List<Pair<String,Integer>> cs) {
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
	
	@Override
	public String toString() {	
		StringBuilder sb = new StringBuilder();		
		sb.append("Change contamination: [");
		for (Pair<String,Integer> pair : this.cs) {	
			sb.append("(" + pair.getFirst() + "," + pair.getSecond() + "), ");			
		}
		sb.delete(sb.length() - 2, sb.length());
		sb.append("]");
		
		return sb.toString();
	}
}
