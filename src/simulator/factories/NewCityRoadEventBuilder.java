package simulator.factories;

import simulator.model.NewCityRoadEvent;

public class NewCityRoadEventBuilder extends NewRoadEventBuilder {

	private static final String TYPE = "new_city_road";
	
	public NewCityRoadEventBuilder() {
		super(TYPE);
	}

	@Override
	NewCityRoadEvent createCorrespondingEvent() {
		return new NewCityRoadEvent(time, id, srcJunc, destJunc, length, co2Limit, maxSpeed, weather);
	}
}