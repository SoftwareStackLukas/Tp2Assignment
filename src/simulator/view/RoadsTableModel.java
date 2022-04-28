package simulator.view;

import java.util.List;

import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableModel;

import simulator.control.Controller;
import simulator.model.Event;
import simulator.model.Road;
import simulator.model.RoadMap;
import simulator.model.TrafficSimObserver;

@SuppressWarnings("serial")
class RoadsTableModel extends MyTable<Road> {

	private static final String[] columnNames = {"Id", "Length", "Weather", "Max Speed", "Speed Limit", "Total CO2", "CO2 Limit"};
	
	RoadsTableModel(Controller ctlr) {
		super(ctlr, columnNames);
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		Object obj;
		Road road = getRow(rowIndex);
		if (road == null) return "Not Available";
		
		switch (columnIndex) {
			case 0:
				obj = road.getId();
				break;
			case 1:
				obj = road.getLength();
				break;
			case 2:
				obj = road.getWeather();
				break;
			case 3:
				obj = road.getMaxSpeed();
				break;
			case 4:
				obj = road.getSpeedLimit();
				break;
			case 5:
				obj = road.getTotalCO2();
				break;
			case 6:
				obj = road.getContLimit();
				break;
		default:
			 obj = "ERROR";
		}
		return obj;
	}

	@Override
	void setRawData(RoadMap map, List<Event> events, int time) {
		rawData = map.getRoads();
	}
	
	
}
