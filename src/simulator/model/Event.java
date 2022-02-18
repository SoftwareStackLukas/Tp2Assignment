package simulator.model;

public abstract class Event implements Comparable<Event> {

	protected int _time;

	Event(int time) {
		if (time < 1)
			throw new IllegalArgumentException("Time must be positive (" + time + ")");
		else
			_time = time;
	}

	int getTime() {
		return _time;
	}

	@Override
	public int compareTo(Event o) {
		/*
	    * returns < 0 then the String calling the method is lexicographically first
	    * returns == 0 then the two strings are lexicographically equivalent
	    * returns > 0 then the parameter passed to the Java compareTo() method is lexicographically first.
		*/
		int result;
		if (this._time > o._time) {
			result = 1;
		} else if (this._time < o._time) {
			result = -1;
		} else {
			result = 0;
		}
		return result;
	}

	abstract void execute(RoadMap map);
}
