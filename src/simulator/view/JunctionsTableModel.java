package simulator.view;

import java.util.List;

import javax.swing.table.AbstractTableModel;

import simulator.control.Controller;
import simulator.model.Event;
import simulator.model.Junction;
import simulator.model.RoadMap;
import simulator.model.TrafficSimObserver;

@SuppressWarnings("serial")
public class JunctionsTableModel extends MyTable<Junction> {
	private static final String[] COLUMN_NAMES = {"Id", "Green", "Queues"};
	
	
	JunctionsTableModel(Controller ctlr) {
		super(ctlr, COLUMN_NAMES);
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		Object o = "NONE";
		Junction junction = getRow(rowIndex);
		if (junction == null) return "Not Available";
		
		switch (columnIndex) {
		case 0:
			//Return ID
			o = junction.getId();
			break;
		case 1:
			//Return GreenIdx
			int index = junction.getGreenLightIndex();
			if(index >= 0 && index < junction.getQueueList().size()) {
				o = junction.getInRoads().get(index).toString();
			}
			break;
		case 2:
			//Return a junction queue
			o = junction.getQueuesRepr();
			break;
		default:
			 o = "ERROR";
		}
		return o;
	}

	@Override
	void setRawData(RoadMap map, List<Event> events, int time) {
		rawData = map.getJunctions();
	}
}
