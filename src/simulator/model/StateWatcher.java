package simulator.model;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import simulator.control.Controller;
import simulator.misc.Pair;

public class StateWatcher implements TrafficSimObserver {
	private Controller cntr;
	private Map<Integer, List<Pair<String, Integer>>> ls;
	private int ticksCounter = 1;
	
	public StateWatcher(Controller cntr) {
		this.cntr = cntr;		
		this.ls = new HashMap<>();
		this.cntr.addObserver(this);
	}
	
	@Override 
	public String toString() {
		StringBuilder sb = new StringBuilder();
		for (int x = 1; x <= this.ticksCounter; x++) {
			List<Pair<String, Integer>> l = this.ls.get((Integer)x);
			if (l != null) {
				sb.append(x + ": [");
				for (Pair<String, Integer> p : l) {
					sb.append("(" + p.getFirst() + "," + p.getSecond() + ")");	
				}	
				sb.append(String.format("]" + "%n"));
			}
		}
		return sb.toString();		
	}
	
	@Override
	public void onAdvanceStart(RoadMap map, List<Event> events, int time) {
		List<Pair<String, Integer>> l = new LinkedList<>();
		List<Road> listRoads;
		List<List<Vehicle>> listQueues;
		int vs;
		for (Junction j : map.getJunctions()) {
			vs = 0;
			listRoads = j.getInRoads();
			listQueues = j.getQueueList();
			for (int x = 0; x < listRoads.size(); x++) {
				Road r = listRoads.get(x);
				vs = listQueues.get(x).size();
				if (vs != 0) {
					l.add(new Pair<String, Integer>(r.getId(), vs));
				}
			}
		}
		if (l.size() > 0) ls.put(ticksCounter, l);
		ticksCounter++;
	}

	@Override
	public void onAdvanceEnd(RoadMap map, List<Event> events, int time) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onEventAdded(RoadMap map, List<Event> events, Event e, int time) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onReset(RoadMap map, List<Event> events, int time) {
		this.ls.clear();
		this.ticksCounter = 1;
	}

	@Override
	public void onRegister(RoadMap map, List<Event> events, int time) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onError(String err) {
		// TODO Auto-generated method stub
		
	}
}
