package simulator.model;

import java.util.ArrayList;
import java.util.List;

public class MoveAllStrategy implements DequeingStrategy {

	@Override
	public List<Vehicle> dequeue(List<Vehicle> q) {
		List<Vehicle> out = new ArrayList<Vehicle>();
		for (Vehicle v: q) {
			out.add(v);
		}
		return out;
	}

}
