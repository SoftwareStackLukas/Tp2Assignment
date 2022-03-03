package simulator.control;

import java.io.InputStream;
import java.io.OutputStream;

import simulator.factories.Factory;
import simulator.model.Event;
import simulator.model.TrafficSimulator;

public class Controller {
	private TrafficSimulator simulator;
	private Factory<Event> eventsFactory;
	
	
	public Controller(TrafficSimulator sim, Factory<Event> eventsFactory) {
		//ToDO
	}
	
	public void loadEvents(InputStream in) {
		//ToDO
	}
	
	public void run(int n, OutputStream out) {
		//ToDO
	}
	
	public void reset() {
		//ToDO
	}
	
	TrafficSimulator getTrafficSim() {
		return this.simulator;
	}
	
	Factory<Event> getEventsFactory() {
		return this.eventsFactory;
	}
}
