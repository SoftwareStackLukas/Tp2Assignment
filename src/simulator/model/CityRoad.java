package simulator.model;

public class CityRoad extends Road {

	CityRoad(String id, Junction srcJunc, Junction destJunc, int maxSpeed, int contLimit, int length, Weather weather) {
		super(id, srcJunc, destJunc, maxSpeed, contLimit, length, weather);
	}

	@Override
	void reduceTotalContamination() {
		int x = 0;
		switch (weather) {
		case WINDY:
		case STORM:
			x = 10;
			break;
		default:
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
		speedLimit = maxSpeed;
	}

	@Override
	int calculateVehicleSpeed(Vehicle v) {
		int f = v.getContClass();
		int speed = ((11-f)*speedLimit)/11;
		return speed;
	}

}
