package simulator.model;

public class CityRoad extends Road {

	@Override
	void reduceTotalContamination() {
		int x = 0;
		switch (weather) {
		case WINDY:
			x = 10;
			break;
		case STORM:
			x = 2;
			break;
		}
		totalCO2 = totalCO2 - x;
		if (totalCO2 < 0) {
			totalCO2 = 0;
		}
	}

	@Override
	void updateSpeedLimit() {
		// f is the contamination class of the vehicle ??
		speedLimit = ((11-f)*maxSpeed)/11;
	}

}
