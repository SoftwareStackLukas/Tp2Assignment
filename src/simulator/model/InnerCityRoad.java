package simulator.model;

public class InnerCityRoad extends Road {

	@Override
	void reduceTotalContamination() {
		int x;
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
		totalCO2 = ((100 - x) * totalCO2) / 100);
	}

	@Override
	void updateSpeedLimit() {
		if (totalCO2 > contLimit) {
			speedLimit = maxSpeed / 2; 
		} else {
			speedLimit = maxSpeed;
		}
	}
	
	double calculateVehicleSpeed() {
		double speed = maxSpeed;
		if (weather == Weather.STORM) {
			speed *= .20;
		}
		return speed;
	}

}
