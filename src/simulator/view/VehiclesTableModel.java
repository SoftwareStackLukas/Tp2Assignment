package simulator.view;

import java.util.List;

import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableModel;

import simulator.control.Controller;
import simulator.model.Event;
import simulator.model.RoadMap;
import simulator.model.Vehicle;

@SuppressWarnings("serial")
class VehiclesTableModel extends MyTable<Vehicle> {

	private static final String[] columnNames = {"Id", "Location", "Itinerary", "CO2 Class", "Max Speed", "Speed", "Total CO2", "Distance"};
	
	VehiclesTableModel(Controller ctlr) {
		super(ctlr, columnNames);
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		Object obj;
		Vehicle vehicle = getRow(rowIndex);
		if (vehicle == null) return "Not Available";
		
		switch (columnIndex) {
		case 0:
			obj = vehicle.getId();
			break;
		case 1:
			obj = vehicle.getLocation();
			break;
		case 2:
			obj = vehicle.getItinerary();
			break;
		case 3:
			obj = vehicle.getMaxSpeed();
			break;
		case 4:
			obj = vehicle.getContClass();
			break;
		case 5:
			obj = vehicle.getMaxSpeed();
			break;
		case 6:
			obj = vehicle.getSpeed();
			break;
		case 7:
			obj = vehicle.getDistance();
			break;
		default:
			 obj = "ERROR";
		}
		return obj;
	}

	@Override
	void setRawData(RoadMap map, List<Event> events, int time) {
		rawData = map.getVehicles();
	}
}
