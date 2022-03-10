	package simulator.model;

import java.util.LinkedList;
import java.util.List;

import org.json.JSONObject;

import simulator.misc.SortedArrayList;

public class TrafficSimulator {
	private RoadMap roadMap;
	private List<Event> events;
	private int simulationTime;
	
	public TrafficSimulator() {
		this.init();
	}
	
	private void init() {
		this.roadMap = new RoadMap();
		this.simulationTime = 0;
		this.events = new SortedArrayList<>();
	}
	
	public void addEvent(Event e) {
		if (e.getTime() > this.simulationTime) {
			this.events.add(e);
		}
	}
	
	public void advance() {		
		this.simulationTime++;
		List<Event> es = new LinkedList<>();
		for(Event e : events) {
			if (e.getTime() == this.simulationTime) {
				e.execute(roadMap);
				es.add(e);
			} 
		}
		this.events.removeAll(es);
		for (Junction j : this.roadMap.getJunctions()) {
			j.advance(simulationTime);
		}
		
		for (Road r : this.roadMap.getRoads()) {
			r.advance(simulationTime);
		}
	}
	
	public void reset() {
		this.init();
	}
	
	public JSONObject report() {
		JSONObject jo = new JSONObject();
		jo.put("time", this.simulationTime);
		jo.put("state", this.roadMap.report());
		return jo;
	}
}
