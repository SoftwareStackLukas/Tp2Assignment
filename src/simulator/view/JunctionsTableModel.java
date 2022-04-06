package simulator.view;

import java.util.List;

import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableModel;

import simulator.control.Controller;
import simulator.model.Event;
import simulator.model.Junction;
import simulator.model.RoadMap;
import simulator.model.TrafficSimObserver;

public class JunctionsTableModel extends AbstractTableModel implements TrafficSimObserver {
	private static final String[] columnNames = {"Id", "Green", "Queues"};
	private List<Junction> rowData; //from where do we get this data?
	
	
	JunctionsTableModel(Controller ctlr) {
		ctlr.addObserver(this);
		initGUI();
	}

	private void initGUI() {
		//Do we need this?
	}
	
	void settingTheRowData(List<Junction> rowData) {
		this.rowData = rowData;
		this.fireTableDataChanged();
	}
	
	//Method to change the values which are presented
	@Override
	public String getColumnName(int col) {
		return columnNames[col].toString();
	}
	
	@Override
	public int getRowCount() {
		return this.rowData != null ? this.rowData.size() : 0;
	}
	
	@Override
	public boolean isCellEditable(int row, int col) {
		return false;
	}
	
	@Override
	public void setValueAt(Object value, int row, int col) {
		//Values can not be set
		//Overrideing the parent that parent logic can not be accessed
	}

	@Override
	public int getColumnCount() {
		return this.columnNames.length;
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		Object o = "No data in row/col space";
		switch (columnIndex) {
		case 0:
			//Return ID
			o = this.rowData.get(rowIndex).getId();
			break;
		case 1:
			//Return GreenIdx
			int index = this.rowData.get(rowIndex).getGreenLightIndex();
			if(index >= 0 && index < this.rowData.get(rowIndex).getQueueList().size()) {
				o = this.rowData.get(rowIndex).getInRoads().get(index).toString();
			}
			break;
		case 2:
			//Return a junction queue
			o = this.rowData.get(rowIndex).getQueueList();
			if(o == null || o == "{}") {
				o =  "";
			}
			break;
		}
		return o;
	}

	@Override
	public void onAdvanceStart(RoadMap map, List<Event> events, int time) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onAdvanceEnd(RoadMap map, List<Event> events, int time) {
		this.settingTheRowData(map.getJunctions());
	}

	@Override
	public void onEventAdded(RoadMap map, List<Event> events, Event e, int time) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onReset(RoadMap map, List<Event> events, int time) {
		this.settingTheRowData(map.getJunctions());
	}

	@Override
	public void onRegister(RoadMap map, List<Event> events, int time) {
		this.settingTheRowData(map.getJunctions());
	}

	@Override
	public void onError(String err) {
		//DO NOTHING
		//IF WE THROW ALWAYS AN ERROR MSG 
		//THE GUI WOULD BE FULL OF IT 
	}
	
}
