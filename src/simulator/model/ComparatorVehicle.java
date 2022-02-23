package simulator.model;

import java.util.Comparator;

public class ComparatorVehicle implements Comparator<Vehicle>{

	@Override
	public int compare(Vehicle o1, Vehicle o2) {
		return o2.getLocation() - o1.getLocation();
	}

}
