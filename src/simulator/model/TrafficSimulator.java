package simulator.model;

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
		//TODO ...
	}
	
	public void advance() {
		//TODO ...
	}
	
	public void reset() {
		//TODO ...
	}
	
	public JSONObject report() {
		JSONObject jo = new JSONObject();
		//TODO ...
		return jo;
	}
	
	
}
