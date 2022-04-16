package simulator.view;

import java.util.List;


import simulator.control.Controller;
import simulator.misc.SortedArrayList;
import simulator.model.Event;
import simulator.model.RoadMap;

@SuppressWarnings("serial")
public class EventsTableModel extends MyTable<Event> {
	private static final String[] columnNames = {"Time", "Description"};
	
	EventsTableModel(Controller ctlr) {
		super(ctlr, columnNames);
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		Object obj;
		Event e = getRow(rowIndex);
		if (e == null) return "NONE";
		
		switch (columnIndex) {
			case 0:
				obj = e.getTime();
				break;
			case 1:
				obj = e.toString();
				break;
		default:
			 obj = "ERROR";
		}
		return obj;
	}

	@Override
	void setRawData(RoadMap map, List<Event> events, int time) {
		rawData = events;
		fireTableDataChanged();
	}
}
