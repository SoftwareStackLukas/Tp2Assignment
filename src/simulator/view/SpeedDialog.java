package simulator.view;

import java.awt.Dimension;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JSpinner;
import javax.swing.table.AbstractTableModel;

import simulator.control.Controller;
import simulator.misc.Pair;
import simulator.model.Event;
import simulator.model.RoadMap;
import simulator.model.Vehicle;

public class SpeedDialog extends MyDialog {
	private final static String HELP_TEXT = "Select a speed limit and press UPDATE to show the vehicels that exceed this speed in each tick.";
	private int sLimit;
	private JLabel speedLimit;
	private JSpinner speedSpinner;
	private JButton close;
	private JButton update;
	private innerTable table;
	
	//Vehicle logic
	private int tickCounter = 1;
	private Map<Integer, List<Pair<Integer, String>>> ticks;
	
	public SpeedDialog(JFrame parent, Controller ctrl) {
		super(parent, ctrl);
		initDialog();
		this.ticks = new HashMap<>();
		this.table = new innerTable();
	}
	
	private void initDialog() {
		setTitle("Vehicles Speed History");
		mainPanel.setLayout( new BoxLayout( mainPanel, BoxLayout.Y_AXIS) );
		setContentPane(mainPanel);
		
		initHelpPanel(SpeedDialog.HELP_TEXT);
		
		this.close = new JButton("Close");
		this.close.addActionListener( (e) -> {
			closeOption = CANCEL;
			dispose();
		});
		//Button has to be added 
		this.update = new JButton("Update");
		this.update.addActionListener((e) -> {
			table.fireTableStructureChanged();
		});
		//Button has to be added		
		
		setMinimumSize(new Dimension(100, 100));
		setPreferredSize(new Dimension(500, 250));
	}
	
	@Override
	public void onAdvanceEnd(RoadMap map, List<Event> events, int time) {
		//Adding cars to a list
		LinkedList<Pair<Integer, String>> ls = new LinkedList<>();
		for (Vehicle v : map.getVehicles()) {
			ls.add(new Pair<Integer, String>(v.getSpeed(), v.getId()));
		}
		this.ticks.put(++this.tickCounter, ls);
	}
	
	@Override
	public void onReset(RoadMap map, List<Event> events, int time) {
		this.ticks.clear();
		this.tickCounter = 1;		
	}
	
	@SuppressWarnings("serial")
	private class innerTable extends AbstractTableModel {
		private final String[] columnNames = {"Tick", "Vehicle"};
		
		@Override
		public int getRowCount() {
			return SpeedDialog.this.ticks.size();
		}

		@Override
		public int getColumnCount() {
			return columnNames.length;
		}

		@Override
		public Object getValueAt(int rowIndex, int columnIndex) {
			String result = "";
			if (columnIndex == 0) {
				result = Integer.toString(rowIndex + 1);
			} else if (columnIndex == 1) {
				StringBuilder sb = new StringBuilder();
				sb.append("[");
				List<Pair<Integer, String>> ls = SpeedDialog.this.ticks.get(rowIndex);
				for (Pair<Integer, String> p : ls) {
					if (SpeedDialog.this.sLimit > p.getFirst()) sb.append(p.getSecond() + ",");
				}
				int length = sb.length();
				sb.replace(length, length, "");
				sb.append("]");
				result = sb.toString();
			}
			return result;
		}
		
		@Override
		public String getColumnName(int col) {
			return columnNames[col].toString();
		}
		
		@Override
		public boolean isCellEditable(int row, int col) {
			return false;
		}
	}

}
