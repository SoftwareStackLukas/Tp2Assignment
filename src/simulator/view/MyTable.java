package simulator.view;

import java.util.List;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;

import java.awt.*;

import simulator.control.Controller;
import simulator.misc.SortedArrayList;
import simulator.model.Event;
import simulator.model.Junction;
import simulator.model.RoadMap;
import simulator.model.TrafficSimObserver;

@SuppressWarnings("serial")
public abstract class MyTable<T> extends AbstractTableModel implements TrafficSimObserver{
	String[] columnNames;
	List<T> rawData; //from where do we get this data?
	

	MyTable(Controller ctlr, String[] columnNames) {
		this.columnNames = columnNames;
		ctlr.addObserver(this);
		rawData = new SortedArrayList<T>();
		
//		setBorder(BorderFactory.createEmptyBorder());
	}
	
	abstract void setRawData(RoadMap map, List<Event> events, int time);

	protected T getRow(int rowIndex) {
		return rowIndex < rawData.size() ? rawData.get(rowIndex) : null;
	}
	
	//Method to change the values which are presented
	@Override
	public String getColumnName(int col) {
		return columnNames[col].toString();
	}
	
	@Override
	public int getRowCount() {
		return this.rawData != null ? this.rawData.size() : 0;
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
	public void onError(String err) {
		//DO NOTHING
		//IF WE THROW ALWAYS AN ERROR MSG 
		//THE GUI WOULD BE FULL OF IT 
	}
	
	@Override
	public void onAdvanceStart(RoadMap map, List<Event> events, int time) {
//		setRawData(map, events, time);
//		fireTableDataChanged();
	}

	@Override
	public void onAdvanceEnd(RoadMap map, List<Event> events, int time) {
		setRawData(map, events, time);
		fireTableDataChanged();
	}

	@Override
	public void onEventAdded(RoadMap map, List<Event> events, Event e, int time) {
		setRawData(map, events, time);
		fireTableDataChanged();
	}

	@Override
	public void onReset(RoadMap map, List<Event> events, int time) {
		rawData = new SortedArrayList<T>();
	}

	@Override
	public void onRegister(RoadMap map, List<Event> events, int time) {
		setRawData(map, events, time);
		fireTableDataChanged();
	}
}
