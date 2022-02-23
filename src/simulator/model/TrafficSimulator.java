package simulator.model;

import java.util.List;

import org.json.JSONArray;
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
		this.events.add(e);
	}
	
	public void advance() {
		try {
		this.simulationTime++;
		for(Event e : events) {
			if (e.getTime() == this.simulationTime) {
				e.execute(roadMap);
				this.events.remove(e);
			} else {
				//Do nothing
			}
		}
		for (Junction j : this.roadMap.getJunctions()) {
			j.advance(simulationTime);
		}
		
		for (Road r : this.roadMap.getRoads()) {
			r.advance(simulationTime);
		}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	public void reset() {
		this.init();
	}
	
	//Has to be completed
	public JSONObject report() {
		JSONObject jo = new JSONObject();
		jo.append("time", this.simulationTime);
		jo.append("state", (new JSONObject())
							.append("junctions", "")													)
							.append("road", "")
							.append("vehicles", "");
		return jo;
	}
	
	
}
