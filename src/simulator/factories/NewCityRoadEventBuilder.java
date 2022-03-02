package simulator.factories;


import simulator.model.Event;
import simulator.model.NewCityRoadEvent;

public class NewCityRoadEventBuilder extends NewRoadEventBuilder {

	private static final String TYPE = "new_city_road";
	
	NewCityRoadEventBuilder() {
		super(TYPE);
		// TODO Auto-generated constructor stub
	}

	@Override
	Event createCorrespondingEvent() {
		try {
			return new NewCityRoadEvent(time, id, srcJunc, destJunc, length, co2Limit, maxSpeed, weather);
		} catch (Exception ex) {
			throw new IllegalArgumentException("Something went wronge in" + NewCityRoadEventBuilder.TYPE);
		}
	}
}