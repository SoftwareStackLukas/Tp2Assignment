package simulator.model;

import java.util.List;

import simulator.misc.Pair;

public class SetWeatherEvent extends Event {

	private List<Pair<String, Weather>> weathers; // (Road id, weather)

	public SetWeatherEvent(int time, List<Pair<String,Weather>> weathers) {
		super(time);
		this.weathers = weathers;
	}

	@Override
	void execute(RoadMap map) {
		// Sets weather to second element of the pair in the road with id of the first element
		for (Pair<String, Weather> roadWeather: weathers) {
			Road road = map.getRoad(roadWeather.getFirst());
			road.setWeather(roadWeather.getSecond());
		}
	}

}
