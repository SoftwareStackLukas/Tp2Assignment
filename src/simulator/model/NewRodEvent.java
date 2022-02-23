package simulator.model;

public class NewRodEvent extends Event {

	int length, co2Limit, maxSpeed;
	String id, srcJunc, destJunc;
	Weather weather;

	public NewRodEvent(int time, String id, String srcJunc, String destJunc, 
			int length, int co2Limit, int maxSpeed, Weather weather) {
		super(time);
		this.length = length;
		this.co2Limit = co2Limit;
		this.maxSpeed = maxSpeed;
		this.id = id;
		this.srcJunc = srcJunc;
		this.destJunc = destJunc;
		this.weather = weather;
	}

	@Override
	void execute(RoadMap map) {
		// TODO Auto-generated method stub

	}

}
