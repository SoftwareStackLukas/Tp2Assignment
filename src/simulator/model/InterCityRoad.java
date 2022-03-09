package simulator.model;

public class InterCityRoad extends Road {

	InterCityRoad(String id, Junction srcJunc, Junction destJunc, int maxSpeed, int contLimit, int length,
			Weather weather) {
		super(id, srcJunc, destJunc, maxSpeed, contLimit, length, weather);
	}

	@Override
	void reduceTotalContamination() {
		int x = 0;
		switch (weather) {
		case SUNNY:
			x = 2;
			break;
		case CLOUDY:
			x = 3;
			break;
		case RAINY:
			x = 10;
			break;
		case WINDY:
			x = 15;
			break;
		case STORM:
			x = 20;
			break;
		}
		totalCO2 = ((100 - x) * totalCO2) / 100;
	}

	@Override
	void updateSpeedLimit() {
		if (totalCO2 > contLimit) {
			speedLimit = maxSpeed / 2; 
		} else {
			speedLimit = maxSpeed;
		}
	}
	
	@Override
	int calculateVehicleSpeed(Vehicle v) {
		int speed = speedLimit;
		if (weather == Weather.STORM) {
			speed *= 0.8;
		}
		return speed;
	}

}
