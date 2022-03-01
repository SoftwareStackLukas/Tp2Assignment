package simulator.factories;

import simulator.model.Event;

public abstract class RoadEventBuilder extends Builder<Event> {

	RoadEventBuilder(String type) {
		super(type);
	}
}
