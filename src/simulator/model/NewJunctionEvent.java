package simulator.model;

public class NewJunctionEvent extends Event {
	private String id;
	private LightSwitchingStrategy light;
	private DequeuingStrategy deque;
	private int xCorr;
	private int yCorr;
	
	
	public NewJunctionEvent(int time, String id, LightSwitchingStrategy lsStrategy, 
							DequeuingStrategy dqStrategy, int xCoor, int yCoor) {
			super(time);
			this.id = id;
			this.light = lsStrategy;
			this.deque = dqStrategy;
			this.xCorr = xCoor;
			this.yCorr = yCoor;
	}
	
	@Override
	void execute(RoadMap map) {
		map.addJunction(new Junction(id, light, deque, xCorr, yCorr));
	}
}
