package simulator.control;

import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

import simulator.factories.Factory;
import simulator.model.Event;
import simulator.model.TrafficSimulator;

public class Controller {
	private static final String _event = "events";
	
	private TrafficSimulator simulator;
	private Factory<Event> eventsFactory;
	
	
	public Controller(TrafficSimulator sim, Factory<Event> eventsFactory) {
		if (sim == null || eventsFactory == null) {
			throw new IllegalArgumentException("The arguemtns can not be null");
		} 
		this.simulator = sim;
		this.eventsFactory = eventsFactory;
	}
	
	public void loadEvents(InputStream in) {
		JSONObject jo = new JSONObject(new JSONTokener(in));
		JSONArray ja = jo.getJSONArray(Controller._event);
		if (ja.length() == 0) throw new IllegalArgumentException("No data");
		for (int x = 0; x < ja.length(); x++) {
			eventsFactory.createInstance(ja.getJSONObject(x));
		}		
	}
	
	public void run(int n, OutputStream out) {
		PrintStream p = new PrintStream(out);
		p.print("{\"states\": [");
		for (int x = 0; x < n -1; x++) {
			p.print(this.simulator.report().toString());
			p.print(",");
			this.simulator.advance();
		}
		p.print(this.simulator.report().toString());
		p.print("]}");
	}
	
	public void reset() {
		this.simulator.reset();
	}
	
	TrafficSimulator getTrafficSim() {
		return this.simulator;
	}
	
	Factory<Event> getEventsFactory() {
		return this.eventsFactory;
	}
}
