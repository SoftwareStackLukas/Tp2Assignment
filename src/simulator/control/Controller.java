package simulator.control;

import simulator.factories.Factory;
import simulator.model.Event;
import simulator.model.TrafficSimulator;

public class Controller {
	TrafficSimulator simulator;
	Factory<Event> eventsFactory;
}
