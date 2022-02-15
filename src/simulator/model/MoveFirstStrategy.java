package simulator.model;

import java.util.ArrayList;
import java.util.List;

public class MoveFirstStrategy implements DequeingStrategy {

	@Override
	public List<Vehicle> dequeue(List<Vehicle> q) {
		// Does not work for empty queue
		List<Vehicle> out = new ArrayList<Vehicle>();
		out.add(q.get(0));
		return out;
	}

}
