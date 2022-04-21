package simulator.control;

import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

import simulator.factories.Factory;
import simulator.model.Event;
import simulator.model.TrafficSimObserver;
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
			simulator.addEvent(eventsFactory.createInstance(ja.getJSONObject(x)));
		}
	}
	
	public void run(int n, OutputStream out) {
		PrintStream p = new PrintStream(out);
		p.println("{\"states\": [");
		for (int x = 0; x < n; x++) {
			this.simulator.advance();
			p.print(this.simulator.report().toString());
			if (x < n - 1)
				p.println(",");
		}
		p.println("]}");
	}
	
	public void run(int n) {
		// TODO: check if something is missing
		for (int x = 0; x < n; x++) {
			this.simulator.advance();
		}
	}
	
	public void reset() {
		this.simulator.reset();
	}
	
	//Add the Observer Methods (that the view is not accessing the TrafficSim directly)
	public void addObserver(TrafficSimObserver o) {
		this.simulator.addObserver(o);
	}
	
	public void removeObserver(TrafficSimObserver o) {
		this.simulator.removeObserver(o);
	}
	
	public void addEvent(Event e) {
		this.simulator.addEvent(e);
	}
	//End of the Observer methods	
	
	Factory<Event> getEventsFactory() {
		return this.eventsFactory;
	}
}
