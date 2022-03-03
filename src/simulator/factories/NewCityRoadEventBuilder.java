package simulator.factories;

import simulator.model.NewCityRoadEvent;

public class NewCityRoadEventBuilder extends NewRoadEventBuilder {

	private static final String TYPE = "new_city_road";
	
	NewCityRoadEventBuilder() {
		super(TYPE);
	}

	@Override
	NewCityRoadEvent createCorrespondingEvent() {
		try {
			return new NewCityRoadEvent(time, id, srcJunc, destJunc, length, co2Limit, maxSpeed, weather);
		} catch (Exception ex) {
			throw new IllegalArgumentException("Something went wronge in" + NewCityRoadEventBuilder.TYPE);
		}
	}
}