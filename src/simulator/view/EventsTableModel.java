package simulator.view;

import java.util.ArrayList;
import java.util.List;

import javax.swing.table.AbstractTableModel;

import simulator.control.Controller;
import simulator.misc.SortedArrayList;
import simulator.model.Event;
import simulator.model.Junction;
import simulator.model.RoadMap;
import simulator.model.TrafficSimObserver;

@SuppressWarnings("serial")
public class EventsTableModel extends MyTable<Event> {
	private static final String[] columnNames = {"Time", "Description"};
	
	EventsTableModel(Controller ctlr) {
		super(ctlr, columnNames);
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		Object obj;
		Event e = rowIndex < rawData.size() ? rawData.get(rowIndex) : null;
		if (e == null) return "Not Available";
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
	public void onAdvanceStart(RoadMap map, List<Event> events, int time) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onAdvanceEnd(RoadMap map, List<Event> events, int time) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onEventAdded(RoadMap map, List<Event> events, Event e, int time) {
		System.out.println(e.toString());
		rawData.add(e);
		fireTableDataChanged();
	}

	@Override
	public void onReset(RoadMap map, List<Event> events, int time) {
		// TODO Auto-generated method stub
		rawData = new SortedArrayList<Event>();
	}

	@Override
	public void onRegister(RoadMap map, List<Event> events, int time) {
		// TODO Auto-generated method stub
	}
}
