package simulator.model;

import java.util.LinkedList;
import java.util.List;

public class VIPDeque implements DequeuingStrategy{
	private String suffix;
	private int limit = 1; //default = 1
	
	public VIPDeque(String suffix, int limit) {
		this.suffix = suffix;
		this.limit = Math.max(this.limit, limit);
	}
	
	@Override
	public List<Vehicle> dequeue(List<Vehicle> q) {
		List<Vehicle> l = new LinkedList<>();
		int i = 0;
		if (q.size() != 0) {
			//First get all VIP cars
			while(i < limit && i < q.size()) {
				if (q.get(i)._id.endsWith(this.suffix)) {
					l.add(q.get(i));
				}
				i++;
			}
			//Now get all not VIP cars
			while(l.size() < limit && i < q.size()) {
				if (!q.get(i)._id.endsWith(this.suffix)) {
					l.add(q.get(i));
				}
				i++;
			}
			
		}
		return l;
	}

}
