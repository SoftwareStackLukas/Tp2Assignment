	package simulator.model;

import java.util.LinkedList;
import java.util.List;

import org.json.JSONObject;

import simulator.misc.SortedArrayList;

public class TrafficSimulator implements Observable<TrafficSimObserver>{
	private RoadMap roadMap;
	private List<Event> events;
	private int simulationTime;
	private List<TrafficSimObserver> observers; 
	
	public TrafficSimulator() {
		this.init();
	}
	
	private void init() {
		this.roadMap = new RoadMap();
		this.simulationTime = 0;
		this.events = new SortedArrayList<>();
		this.observers = new SortedArrayList<>();
	}
	
	public void addEvent(Event e) {
		if (e.getTime() > this.simulationTime) {
			this.events.add(e);
		}
		for (TrafficSimObserver o : this.observers) {
			o.onEventAdded(this.roadMap, this.events, e, this.simulationTime);
		}
	}
	
	public void advance() {		
		this.simulationTime++;
		for (TrafficSimObserver o : observers) {
			o.onAdvanceStart(this.roadMap, this.events, this.simulationTime);
		}
		List<Event> es = new LinkedList<>();
		for(Event e : events) {
			if (e.getTime() == this.simulationTime) {
				e.execute(roadMap);
				es.add(e);
			} 
		}
		this.events.removeAll(es);
		es = null;
		try {
			for (Junction j : this.roadMap.getJunctions()) {
				j.advance(simulationTime);
			}
			
			for (Road r : this.roadMap.getRoads()) {
				r.advance(simulationTime);
			}
		} catch (Exception e) {
			for (TrafficSimObserver o : observers) {
				o.onError(e.getMessage());
			}
			throw e;
		}
		
		for (TrafficSimObserver o : observers) {
			o.onAdvanceEnd(this.roadMap, this.events, this.simulationTime);
		}
	}
	
	public void reset() {
		this.init();
		for (TrafficSimObserver o : this.observers) {
			o.onReset(this.roadMap, this.events, this.simulationTime);
		}
	}
	
	public JSONObject report() {
		JSONObject jo = new JSONObject();
		jo.put("time", this.simulationTime);
		jo.put("state", this.roadMap.report());
		return jo;
	}

	@Override
	public void addObserver(TrafficSimObserver o) {
		if (!this.observers.contains(o)) {
			this.observers.add(o);
			o.onRegister(this.roadMap, this.events, this.simulationTime);
		}
	}

	@Override
	public void removeObserver(TrafficSimObserver o) {
		if (this.observers.contains(o) ) {
			this.observers.remove(o);
		}		
	}
}
