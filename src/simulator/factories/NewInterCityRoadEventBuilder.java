package simulator.factories;

import simulator.model.NewInterCityRoadEvent;

public class NewInterCityRoadEventBuilder extends NewRoadEventBuilder {

	private static final String TYPE = "new_inter_city_road";
	
	public NewInterCityRoadEventBuilder() {
		super(NewInterCityRoadEventBuilder.TYPE);
	}

	@Override
	NewInterCityRoadEvent createCorrespondingEvent() {
			return new NewInterCityRoadEvent(time, id, srcJunc, destJunc, length, co2Limit, maxSpeed, weather);
	}
}
 